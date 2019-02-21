package org.vaccineimpact.api.app.controllers.BurdenEstimates

import org.vaccineimpact.api.app.Cache
import org.vaccineimpact.api.app.ResultRedirector
import org.vaccineimpact.api.app.ResumableInfoCache
import org.vaccineimpact.api.app.asResult
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.context.RequestDataSource
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.errors.InvalidOperationError
import org.vaccineimpact.api.app.logic.BurdenEstimateLogic
import org.vaccineimpact.api.app.logic.RepositoriesBurdenEstimateLogic
import org.vaccineimpact.api.app.models.ResumableInfo
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.requests.PostDataHelper
import org.vaccineimpact.api.app.requests.csvData
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.security.KeyHelper
import org.vaccineimpact.api.security.TokenType
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.serialization.DataTableDeserializer
import org.vaccineimpact.api.serialization.MontaguSerializer
import org.vaccineimpact.api.serialization.Serializer
import java.io.File
import java.io.RandomAccessFile

class BurdenEstimateUploadController(context: ActionContext,
                                     private val repositories: Repositories,
                                     private val estimatesLogic: BurdenEstimateLogic,
                                     private val estimateRepository: BurdenEstimateRepository,
                                     private val postDataHelper: PostDataHelper = PostDataHelper(),
                                     private val tokenHelper: WebTokenHelper = WebTokenHelper(KeyHelper.keyPair),
                                     private val resumableInfoCache: Cache<ResumableInfo> = ResumableInfoCache.instance,
                                     private val serializer: Serializer = MontaguSerializer.instance)
    : BaseBurdenEstimateController(context, estimatesLogic)
{
    constructor(context: ActionContext, repos: Repositories)
            : this(context,
            repos,
            RepositoriesBurdenEstimateLogic(repos.modellingGroup, repos.burdenEstimates, repos.expectations),
            repos.burdenEstimates)

    fun getUploadToken(): String
    {
        val path = getValidResponsibilityPath(context, estimateRepository)
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

    fun populateBurdenEstimateSet() = populateBurdenEstimateSet(RequestDataSource.fromContentType(context))
    fun populateBurdenEstimateSet(source: RequestDataSource): Result
    {
        return ResultRedirector(tokenHelper, repositories).redirectIfRequested(context, "".asResult()) { repos ->
            val estimateRepository = repos.burdenEstimates

            // First check if we're allowed to see this touchstoneVersion
            val path = getValidResponsibilityPath(context, estimateRepository)

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
                    path.groupId, path.touchstoneVersionId, path.scenarioId,
                    data
            )

            // Then, maybe close the burden estimate set
            val keepOpen = context.queryParams("keepOpen")?.toBoolean() ?: false
            if (!keepOpen)
            {
                closeEstimateSetAndReturnMissingRowError(setId, path.groupId, path.touchstoneVersionId, path.scenarioId)
            }
            else
            {
                okayResponse().asResult()
            }
        }
    }

    fun uploadBurdenEstimateFile(): String
    {
        val resumableChunkNumber = context.queryParams("resumableChunkNumber")?.toInt()
                ?: throw BadRequest("Missing required query parameter: resumableChunkNumber")

        val info = getResumableInfo()
        val raf = RandomAccessFile(info.filePath, "rw")

        // Seek to position
        raf.seek((resumableChunkNumber - 1) * info.chunkSize)

        // Get file from context (supports multi-part or octet stream)
        val source = RequestDataSource.fromContentType(context)
        val stream = source.getContent()

        // Write to file
        var readBytes: Long = 0
        val length = context.request.raw().contentLength
        val bytes = ByteArray(1024 * 100)
        while (readBytes < length)
        {
            val r = stream.read(bytes)
            if (r < 0)
            {
                break
            }
            raf.write(bytes, 0, r)
            readBytes += r.toLong()
        }
        raf.close()

        //Mark as uploaded
        info.uploadedChunks[resumableChunkNumber] = true
        return okayResponse()
    }

    fun populateBurdenEstimateSetFromLocalFile(): String
    {
        val uploadToken = context.params(":token")
        val path = UploadPath(tokenHelper.verify(uploadToken, TokenType.UPLOAD))

        val info = resumableInfoCache[path.uniqueIdentifier]
                ?: throw BadRequest("Unrecognised file identifier - has this token already been used?")

        return if (info.uploadFinished())
        {
            // Stream estimates from file
            val data = DataTableDeserializer.deserialize(File(info.filePath).reader(),
                    BurdenEstimate::class, serializer).map {
                BurdenEstimateWithRunId(it, runId = null)
            }

            estimatesLogic.populateBurdenEstimateSet(
                    path.setId,
                    path.groupId,
                    path.touchstoneVersionId,
                    path.scenarioId,
                    data
            )

            resumableInfoCache.remove(info.uniqueIdentifier)
            estimatesLogic.closeBurdenEstimateSet(path.setId, path.groupId, path.touchstoneVersionId, path.scenarioId)

            okayResponse()
        }
        else
        {
            throw BadRequest("This file has not been fully uploaded")
        }
    }

    private fun getResumableInfo(): ResumableInfo
    {
        val totalChunks = context.queryParams("resumableTotalChunks")?.toInt()
        val chunkSize = context.queryParams("resumableChunkSize")?.toLong()
        val uploadToken = context.queryParams("resumableIdentifier")
        val filename = context.queryParams("resumableFilename")

        if (totalChunks == null || chunkSize == null || uploadToken.isNullOrEmpty() || filename.isNullOrEmpty())
        {
            throw BadRequest("You must include all resumablejs query parameters")
        }

        val claims = tokenHelper.verify(uploadToken!!, TokenType.UPLOAD)
        if (claims["file-name"] != filename)
        {
            throw BadRequest("The given token has not been issued for the file $filename")
        }
        if (claims["sub"] != context.username!!)
        {
            throw BadRequest("The given token has not been issued for this user")
        }

        val uniqueIdentifier = claims["uid"].toString()

        val info = resumableInfoCache[uniqueIdentifier]
        return if (info != null)
        {
            info
        }
        else
        {
            File(UPLOAD_DIR).mkdir()
            val file = File(UPLOAD_DIR, uniqueIdentifier)
            resumableInfoCache.put(ResumableInfo(totalChunks, chunkSize, uniqueIdentifier, file))
            resumableInfoCache[uniqueIdentifier]!!
        }
    }

    private fun getBurdenEstimateDataFromCSV(
            metadata: BurdenEstimateSet,
            source: RequestDataSource
    ): Sequence<BurdenEstimateWithRunId>
    {
        return if (metadata.type.type == BurdenEstimateSetTypeCode.STOCHASTIC)
        {
            postDataHelper.csvData<StochasticBurdenEstimate>(from = source).map {
                BurdenEstimateWithRunId(it)
            }
        }
        else
        {
            postDataHelper.csvData<BurdenEstimate>(from = source).map {
                BurdenEstimateWithRunId(it, runId = null)
            }
        }
    }

    companion object
    {
        const val UPLOAD_DIR = "upload_dir"
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