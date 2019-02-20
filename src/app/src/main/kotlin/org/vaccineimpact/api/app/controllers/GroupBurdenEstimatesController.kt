package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.app.Cache
import org.vaccineimpact.api.app.ResultRedirector
import org.vaccineimpact.api.app.ResumableInfoCache
import org.vaccineimpact.api.app.app_start.Controller
import org.vaccineimpact.api.app.asResult
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.context.RequestDataSource
import org.vaccineimpact.api.app.context.postData
import org.vaccineimpact.api.app.controllers.helpers.ResponsibilityPath
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.errors.MissingRowsError
import org.vaccineimpact.api.app.logic.BurdenEstimateLogic
import org.vaccineimpact.api.app.logic.RepositoriesBurdenEstimateLogic
import org.vaccineimpact.api.app.models.ResumableInfo
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.requests.PostDataHelper
import org.vaccineimpact.api.app.requests.csvData
import org.vaccineimpact.api.app.security.checkEstimatePermissionsForTouchstoneVersion
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.security.KeyHelper
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.serialization.DataTableDeserializer
import org.vaccineimpact.api.serialization.MontaguSerializer
import org.vaccineimpact.api.serialization.Serializer
import java.io.File
import java.io.RandomAccessFile
import java.time.Instant

open class GroupBurdenEstimatesController(
        context: ActionContext,
        private val repositories: Repositories,
        private val estimatesLogic: BurdenEstimateLogic,
        private val estimateRepository: BurdenEstimateRepository,
        private val postDataHelper: PostDataHelper = PostDataHelper(),
        private val tokenHelper: WebTokenHelper = WebTokenHelper(KeyHelper.keyPair),
        private val resumableInfoCache: Cache<ResumableInfo> = ResumableInfoCache.instance,
        private val serializer: Serializer = MontaguSerializer.instance
) : Controller(context)
{
    constructor(context: ActionContext, repos: Repositories)
            : this(context,
            repos,
            RepositoriesBurdenEstimateLogic(repos.modellingGroup, repos.burdenEstimates, repos.expectations),
            repos.burdenEstimates)

    fun getBurdenEstimates(): List<BurdenEstimateSet>
    {
        val path = getValidResponsibilityPath(context, estimateRepository)
        return estimateRepository.getBurdenEstimateSets(path.groupId, path.touchstoneVersionId, path.scenarioId)
    }

    fun getBurdenEstimateSet(): BurdenEstimateSet
    {
        val path = getValidResponsibilityPath(context, estimateRepository)
        val burdenEstimateSetId = context.params(":set-id").toInt()
        return estimateRepository.getBurdenEstimateSet(path.groupId, path.touchstoneVersionId, path.scenarioId, burdenEstimateSetId)
    }

    fun createBurdenEstimateSet(): String
    {
        // First check if we're allowed to see this touchstoneVersion
        val path = getValidResponsibilityPath(context, estimateRepository)
        val properties = context.postData<CreateBurdenEstimateSet>()

        val id = estimateRepository.createBurdenEstimateSet(path.groupId, path.touchstoneVersionId, path.scenarioId,
                properties = properties,
                uploader = context.username!!,
                timestamp = Instant.now())

        val url = "/modelling-groups/${path.groupId}/responsibilities/${path.touchstoneVersionId}/${path.scenarioId}/estimate-sets/$id/"
        return objectCreation(context, url)
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

        // Save to file
        val source = RequestDataSource.fromContentType(context)
        val stream = source.getContent()

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
        val info = getResumableInfo()

        return if (info.uploadFinished())
        {
            // First check if we're allowed to see this touchstoneVersion
            val path = getValidResponsibilityPath(context, estimateRepository)

            // Next, get the metadata that will enable us to interpret the CSV
            val setId = context.params(":set-id").toInt()

            // Stream estimates from file
            val data = DataTableDeserializer.deserialize(File(info.filePath).reader(),
                    BurdenEstimate::class, serializer).map {
                BurdenEstimateWithRunId(it, runId = null)
            }

            estimatesLogic.populateBurdenEstimateSet(
                    setId,
                    path.groupId, path.touchstoneVersionId, path.scenarioId,
                    data
            )

            File(info.filePath).delete()
            resumableInfoCache.remove(info)

            // Then, maybe close the burden estimate set
            val keepOpen = context.queryParams("keepOpen")?.toBoolean() ?: false
            if (!keepOpen)
            {
                estimatesLogic.closeBurdenEstimateSet(setId, path.groupId, path.touchstoneVersionId, path.scenarioId)
            }

            okayResponse()
        }
        else
        {
            throw BadRequest("This file has not been fully uploaded")
        }
    }

    private fun closeEstimateSetAndReturnMissingRowError(setId: Int, groupId: String, touchstoneVersionId: String,
                                                         scenarioId: String): Result
    {
        return try
        {
            estimatesLogic.closeBurdenEstimateSet(setId, groupId, touchstoneVersionId, scenarioId)
            okayResponse().asResult()
        }
        catch (error: MissingRowsError)
        {
            context.setResponseStatus(400)
            error.asResult()
        }
    }

    fun getEstimatesForOutcome(): BurdenEstimateDataSeries
    {
        val path = getValidResponsibilityPath(context, estimateRepository)
        val groupBy = context.queryParams("groupBy")
        val grouping = if (groupBy == "year")
        {
            BurdenEstimateGrouping.YEAR
        }
        else
        {
            BurdenEstimateGrouping.AGE
        }
        return estimatesLogic.getEstimates(context.params(":set-id").toInt(),
                path.groupId, path.touchstoneVersionId,
                path.scenarioId, context.params(":outcome-code"), grouping)
    }

    fun clearBurdenEstimateSet(): String
    {
        val path = getValidResponsibilityPath(context, estimateRepository)
        val setId = context.params(":set-id").toInt()
        estimateRepository.clearBurdenEstimateSet(setId, path.groupId, path.touchstoneVersionId, path.scenarioId)
        return okayResponse()
    }

    fun closeBurdenEstimateSet(): Result
    {
        val path = getValidResponsibilityPath(context, estimateRepository)
        val setId = context.params(":set-id").toInt()
        return closeEstimateSetAndReturnMissingRowError(setId, path.groupId, path.touchstoneVersionId, path.scenarioId)
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

    private fun getValidResponsibilityPath(
            context: ActionContext,
            estimateRepository: BurdenEstimateRepository,
            readEstimatesRequired: Boolean = false
    ): ResponsibilityPath
    {
        val path = ResponsibilityPath(context)
        context.checkEstimatePermissionsForTouchstoneVersion(path.groupId, path.touchstoneVersionId, estimateRepository, readEstimatesRequired)
        return path
    }

    private fun getResumableInfo(): ResumableInfo
    {
        val totalChunks = context.queryParams("resumableTotalChunks")?.toInt()
        val chunkSize = context.queryParams("resumableChunkSize")?.toLong()
        val uniqueIdentifier = context.queryParams("resumableIdentifier")
        val filename = context.queryParams("resumableFilename")

        if (totalChunks == null || chunkSize == null || uniqueIdentifier.isNullOrEmpty() || filename.isNullOrEmpty())
        {
            throw BadRequest("You must include all resumablejs query parameters")
        }

        val info = resumableInfoCache[uniqueIdentifier!!]
        return if (info != null)
        {
            info
        }
        else
        {
            //Here we add a ".temp" to every upload file to indicate NON-FINISHED
            File(UPLOAD_DIR).mkdir()
            val filePath = File(UPLOAD_DIR, filename).absolutePath + ".temp"
            resumableInfoCache.put(ResumableInfo(totalChunks, chunkSize, uniqueIdentifier, filePath))
            resumableInfoCache[uniqueIdentifier]!!
        }
    }

    companion object
    {
        const val UPLOAD_DIR = "upload_dir"
    }

}