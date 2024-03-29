package org.vaccineimpact.api.app.controllers.BurdenEstimates

import org.slf4j.LoggerFactory
import org.vaccineimpact.api.app.Cache
import org.vaccineimpact.api.app.ChunkedFileCache
import org.vaccineimpact.api.app.ChunkedFileManager
import org.vaccineimpact.api.app.ChunkedFileManager.Companion.UPLOAD_DIR
import org.vaccineimpact.api.app.asResult
import org.vaccineimpact.api.app.clients.CeleryClient
import org.vaccineimpact.api.app.clients.TaskQueueClient
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.context.RequestDataSource
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.errors.InvalidOperationError
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.logic.BurdenEstimateLogic
import org.vaccineimpact.api.app.logic.RepositoriesBurdenEstimateLogic
import org.vaccineimpact.api.app.logic.RepositoriesResponsibilitiesLogic
import org.vaccineimpact.api.app.logic.ResponsibilitiesLogic
import org.vaccineimpact.api.app.models.ChunkedFile
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.UserRepository
import org.vaccineimpact.api.app.requests.PostDataHelper
import org.vaccineimpact.api.app.requests.csvData
import org.vaccineimpact.api.models.BurdenEstimate
import org.vaccineimpact.api.models.BurdenEstimateSet
import org.vaccineimpact.api.models.BurdenEstimateWithRunId
import org.vaccineimpact.api.models.Result
import org.vaccineimpact.api.security.KeyHelper
import org.vaccineimpact.api.security.TokenType
import org.vaccineimpact.api.security.TokenValidationException
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.serialization.DataTableDeserializer
import org.vaccineimpact.api.serialization.MontaguSerializer
import org.vaccineimpact.api.serialization.Serializer
import java.io.File

class BurdenEstimateUploadController(context: ActionContext,
                                     private val estimatesLogic: BurdenEstimateLogic,
                                     private val responsibilitiesLogic: ResponsibilitiesLogic,
                                     private val estimateRepository: BurdenEstimateRepository,
                                     private val userRepository: UserRepository,
                                     private val postDataHelper: PostDataHelper = PostDataHelper(),
                                     private val tokenHelper: WebTokenHelper = WebTokenHelper(KeyHelper.keyPair),
                                     private val chunkedFileCache: Cache<ChunkedFile> = ChunkedFileCache.instance,
                                     private val chunkedFileManager: ChunkedFileManager = ChunkedFileManager(),
                                     private val serializer: Serializer = MontaguSerializer.instance,
                                     private val taskQueueClient: TaskQueueClient = CeleryClient())
    : BaseBurdenEstimateController(context, estimatesLogic, responsibilitiesLogic, userRepository, taskQueueClient)
{
    constructor(context: ActionContext, repos: Repositories)
            : this(context,
            RepositoriesBurdenEstimateLogic(repos),
            RepositoriesResponsibilitiesLogic(repos),
            repos.burdenEstimates,
            repos.user)

    private val logger = LoggerFactory.getLogger(BurdenEstimateUploadController::class.java)
    fun getUploadToken(): String
    {
        val path = getValidResponsibilityPath()
        val setId = context.params(":set-id").toInt()

        // Check that this is a central estimate set
        val metadata = estimateRepository.getBurdenEstimateSet(path.groupId,
                path.touchstoneVersionId,
                path.scenarioId,
                setId)

        if (metadata.isStochastic())
        {
            throw InvalidOperationError("Stochastic estimate upload not supported")
        }

        return tokenHelper.generateUploadEstimatesToken(
                context.username!!,
                path.groupId,
                path.touchstoneVersionId,
                path.scenarioId,
                setId)
    }

    fun uploadBurdenEstimateFile(): String
    {
        val chunkNumber = context.queryParams("chunkNumber")?.toInt()
                ?: throw BadRequest("Missing required query parameter: chunkNumber")

        val metadata = getFileMetadata()

        val alreadyUploadedKeys = metadata.uploadedChunks.keys().toList().joinToString(",")
        logger.info("Uploading chunk: $chunkNumber")
        logger.info("These chunks have already been uploaded: $alreadyUploadedKeys")

        // Get file from context (supports multi-part or octet stream)
        val source = RequestDataSource.fromContentType(context)
        val stream = source.getContent()

        chunkedFileManager.writeChunk(stream, context.contentLength(), metadata, chunkNumber)

        //Mark as uploaded
        metadata.uploadedChunks[chunkNumber] = true
        return okayResponse()
    }

    fun populateBurdenEstimateSetFromLocalFile(): Result
    {
        val uploadToken = context.params(":token")
        val token = try
        {
            tokenHelper.verify(uploadToken, TokenType.UPLOAD)
        }
        catch (e: TokenValidationException)
        {
            throw UnknownObjectError(uploadToken, "upload-token")
        }

        val path = UploadPath(token)

        val file = chunkedFileCache[path.uniqueIdentifier]
                ?: throw BadRequest("Unrecognised file identifier - has this token already been used?")

        return if (file.uploadFinished())
        {
            chunkedFileManager.markFileAsComplete(file)

            // Stream estimates from file
            val data = DataTableDeserializer.deserialize(File("$UPLOAD_DIR/${file.uniqueIdentifier}").reader(),
                    BurdenEstimate::class, serializer).map {
                BurdenEstimateWithRunId(it, runId = null)
            }

            estimatesLogic.populateBurdenEstimateSet(
                    path.setId,
                    path.groupId,
                    path.touchstoneVersionId,
                    path.scenarioId,
                    data,
                    file.originalFileName)

            chunkedFileCache.remove(file.uniqueIdentifier)

            val info = estimateRepository.getResponsibilityInfo(path.groupId, path.touchstoneVersionId, path.scenarioId)

            closeEstimateSetAndReturnMissingRowError(path.setId,
                    path.groupId, info.disease, path.touchstoneVersionId, path.scenarioId)
        }
        else
        {
            val alreadyUploadedKeys = file.uploadedChunks.keys().toList().joinToString(",")
            logger.info("These chunks have been uploaded: $alreadyUploadedKeys")
            throw InvalidOperationError("This file has not been fully uploaded")
        }
    }

    fun populateBurdenEstimateSet() = populateBurdenEstimateSet(RequestDataSource.fromContentType(context))

    private fun populateBurdenEstimateSet(source: RequestDataSource): Result
    {
        // First check if we're allowed to see this touchstoneVersion
        val path = getValidResponsibilityPath()

        // Next, get the metadata that will enable us to interpret the CSV
        val setId = context.params(":set-id").toInt()
        val metadata = estimateRepository.getBurdenEstimateSet(path.groupId,
                path.touchstoneVersionId,
                path.scenarioId,
                setId)

        // Then add the burden estimates
        val data = getBurdenEstimateDataFromCSV(metadata, source)
        estimatesLogic.populateBurdenEstimateSet(
                setId,
                path.groupId,
                path.touchstoneVersionId,
                path.scenarioId,
                data,
                filename = null)

        val info = estimateRepository.getResponsibilityInfo(path.groupId, path.touchstoneVersionId, path.scenarioId)

        // Then, maybe close the burden estimate set
        val keepOpen = context.queryParams("keepOpen")?.toBoolean() ?: false

        return if (!keepOpen)
        {
            closeEstimateSetAndReturnMissingRowError(setId, path.groupId, info.disease, path.touchstoneVersionId, path.scenarioId)
        }
        else
        {
            okayResponse().asResult()
        }
    }

    private fun getFileMetadata(): ChunkedFile
    {
        val totalChunks = context.queryParams("totalChunks")?.toInt()
                ?: throw BadRequest("Missing required query parameter: totalChunks.")
        val totalSize = context.queryParams("totalSize")?.toLong()
                ?: throw BadRequest("Missing required query parameter: totalSize.")
        val chunkSize = context.queryParams("chunkSize")?.toLong()
                ?: throw BadRequest("Missing required query parameter: chunkSize.")
        val filename = context.queryParams("fileName")
                ?: throw BadRequest("Missing required query parameter: fileName.")

        val uploadToken = context.params(":token")

        // Note expired tokens will throw an error during verification
        val claims = tokenHelper.verify(uploadToken, TokenType.UPLOAD)

        if (claims["sub"] != context.username!!)
        {
            throw BadRequest("The given token has not been issued for this user")
        }

        val uniqueIdentifier = claims["uid"].toString()
        synchronized(BurdenEstimateUploadController::class.java) {
            val cachedMetadata = chunkedFileCache[uniqueIdentifier]
            val providedMetadata = ChunkedFile(totalChunks = totalChunks, chunkSize = chunkSize,
                    totalSize = totalSize, uniqueIdentifier = uniqueIdentifier, originalFileName = filename)

            return if (cachedMetadata != null)
            {
                if (cachedMetadata != providedMetadata)
                {
                    throw BadRequest("The given token has already been used to upload a different file." +
                            " Please request a fresh upload token.")
                }
                cachedMetadata
            }
            else
            {
                chunkedFileCache.put(providedMetadata)
                chunkedFileCache[uniqueIdentifier]!!
            }
        }
    }

    private fun getBurdenEstimateDataFromCSV(
            metadata: BurdenEstimateSet,
            source: RequestDataSource
    ): Sequence<BurdenEstimateWithRunId>
    {
        return postDataHelper.csvData<BurdenEstimate>(from = source).map {
            BurdenEstimateWithRunId(it, runId = null)
        }
    }

    data class UploadPath(val uniqueIdentifier: String,
                          val groupId: String,
                          val touchstoneVersionId: String,
                          val scenarioId: String,
                          val setId: Int)
    {
        constructor(claims: Map<String, Any>) : this(
                claims["uid"].toString(),
                claims["group-id"].toString(),
                claims["touchstone-id"].toString(),
                claims["scenario-id"].toString(),
                claims["set-id"].toString().toInt())
    }

}
