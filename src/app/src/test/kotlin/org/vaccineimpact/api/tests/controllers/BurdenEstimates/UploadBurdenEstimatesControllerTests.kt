package org.vaccineimpact.api.tests.controllers.BurdenEstimates

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.mockito.Mockito
import org.vaccineimpact.api.app.ResumableInfoCache
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.controllers.BurdenEstimates.BurdenEstimateUploadController
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.errors.MissingRowsError
import org.vaccineimpact.api.app.logic.BurdenEstimateLogic
import org.vaccineimpact.api.app.models.ResumableInfo
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.SimpleDataSet
import org.vaccineimpact.api.app.requests.PostDataHelper
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.security.TokenType
import org.vaccineimpact.api.security.TokenValidationException
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.tests.mocks.mockCSVPostData
import java.io.File

class UploadBurdenEstimatesControllerTests : BurdenEstimateControllerTestsBase()
{

    @Test
    fun `can populate central estimate set`()
    {
        val touchstoneSet = mockTouchstones()
        val logic = mockLogic()

        val expectedData = listOf(
                BurdenEstimateWithRunId("yf", null, 2000, 50, "AFG", "Afghanistan", 1000F, mapOf(
                        "deaths" to 10F,
                        "cases" to 100F
                )),
                BurdenEstimateWithRunId("yf", null, 1980, 30, "AGO", "Angola", 2000F, mapOf(
                        "deaths" to 20F,
                        "cases" to 73.6F
                ))
        )

        val mockContext = mockActionContext()
        verifyLogicIsInvokedToPopulateSet(mockContext, mockEstimatesRepository(touchstoneSet), logic, touchstoneSet,
                normalCSVData.asSequence(), expectedData)
    }

    @Test
    fun `can populate stochastic estimate set`()
    {
        val touchstoneSet = mockTouchstones()
        val logic = mockLogic()

        val csvData = listOf(
                StochasticBurdenEstimate("yf", "runA", 2000, 50, "AFG", "Afghanistan", 1000F, mapOf(
                        "deaths" to 10F,
                        "cases" to 100F
                )),
                StochasticBurdenEstimate("yf", "runB", 1980, 30, "AGO", "Angola", 2000F, mapOf(
                        "deaths" to 20F,
                        "dalys" to 73.6F
                ))
        )
        val expectedData = listOf(
                BurdenEstimateWithRunId("yf", "runA", 2000, 50, "AFG", "Afghanistan", 1000F, mapOf(
                        "deaths" to 10F,
                        "cases" to 100F
                )),
                BurdenEstimateWithRunId("yf", "runB", 1980, 30, "AGO", "Angola", 2000F, mapOf(
                        "deaths" to 20F,
                        "dalys" to 73.6F
                ))
        )

        val mockContext = mockActionContext()
        val repo = mockEstimatesRepository(touchstoneSet, existingBurdenEstimateSet = defaultEstimateSet.copy(
                type = BurdenEstimateSetType(BurdenEstimateSetTypeCode.STOCHASTIC)))
        verifyLogicIsInvokedToPopulateSet(mockContext, repo, logic, touchstoneSet,
                csvData.asSequence(), expectedData)
    }

    @Test
    fun `if keepOpen is not provided, populate closes estimate set`()
    {
        // This way, the webapps will carry on with the same behaviour as before.
        // It's only if a client explicitly sets keepOpen that we will see the partial state
        populateAndCheckIfSetIsClosed(keepOpen = null, expectedClosed = true)
    }

    @Test
    fun `if keepOpen is true, burden estimate set is left open`()
    {
        populateAndCheckIfSetIsClosed(keepOpen = "true", expectedClosed = false)
    }

    @Test
    fun `if keepOpen is false, populate closes burden estimate set`()
    {
        populateAndCheckIfSetIsClosed(keepOpen = "false", expectedClosed = true)
    }

    @Test
    fun `populate burden estimate set catches missing row error and returns result`()
    {
        val logic = mockLogic()
        Mockito.`when`(logic.closeBurdenEstimateSet(any(), any(), any(), any()))
                .doThrow(MissingRowsError("message"))
        val repo = mockEstimatesRepository()
        val mockContext = mockActionContext()
        val mockPostData = mockCSVPostData(normalCSVData)
        val result = BurdenEstimateUploadController(mockContext, mockRepositories(repo), logic, repo,
                postDataHelper = mockPostData).populateBurdenEstimateSet()
        Assertions.assertThat(result.status).isEqualTo(ResultStatus.FAILURE)
    }

    @Test
    fun `can get upload token`()
    {
        val mockTokenHelper = mock<WebTokenHelper> {
            on { generateUploadEstimatesToken("username", "group-1", "touchstone-1", "scenario-1", 1, "file.csv") } doReturn "TOKEN"
        }

        val sut = BurdenEstimateUploadController(mockActionContext(), mock(), mockLogic(),
                mockEstimatesRepository(mockTouchstones()), mock(),
                mockTokenHelper, mock())
        val result = sut.getUploadToken()
        assertThat(result).isEqualTo("TOKEN")
    }

    @Test
    fun `can populate central estimate set from local file`()
    {
        val touchstoneSet = mockTouchstones()
        val logic = mockLogic()

        val file = File("test.csv")
        createTempCSVFile("test.csv")

        val mockContext = mockPopulateFromLocalFileActionContext("user.name")
        val repo = mockEstimatesRepository(touchstoneSet)
        val cache = makeFakeCacheWithResumableInfo("uid", file, uploadFinished = true)
        val mockTokenHelper = getMockTokenHelper("user.name", "uid")

        val sut = BurdenEstimateUploadController(mockContext, mock(), logic, repo, mock(), mockTokenHelper, cache)

        val result = sut.populateBurdenEstimateSetFromLocalFile()

        assertThat(result).isEqualTo("OK")
        verify(logic).populateBurdenEstimateSet(eq(1), eq("g1"), eq("t1"), eq("s1"), any())
        verify(logic).closeBurdenEstimateSet(eq(1), eq("g1"), eq("t1"), eq("s1"))
    }

    @Test
    fun `populating central estimate set from local file deletes file and clears cache item on success`()
    {
        val touchstoneSet = mockTouchstones()
        val logic = mockLogic()

        val file = File("test.csv")
        val tempFile = createTempCSVFile("test.csv")

        val mockContext = mockPopulateFromLocalFileActionContext("user.name")
        val repo = mockEstimatesRepository(touchstoneSet)
        val cache = makeFakeCacheWithResumableInfo("uid", file, uploadFinished = true)
        val mockTokenHelper = getMockTokenHelper("user.name", "uid")

        val sut = BurdenEstimateUploadController(mockContext, mock(), logic, repo, mock(), mockTokenHelper, cache)

        val result = sut.populateBurdenEstimateSetFromLocalFile()

        assertThat(result).isEqualTo("OK")
        assertThat(cache["uid"]).isNull()
        assertThat(tempFile.exists()).isFalse()
        assertThat(file.exists()).isFalse()
    }

    @Test
    fun `populating set from local file throws error if uid not recognised`()
    {
        val mockContext = mockPopulateFromLocalFileActionContext("user.name")
        val mockTokenHelper = getMockTokenHelper("user.name", "uid")

        val sut = BurdenEstimateUploadController(mockContext, mock(), mock(), mock(), mock(), mockTokenHelper, mock())

        assertThatThrownBy { sut.populateBurdenEstimateSetFromLocalFile() }
                .isInstanceOf(BadRequest::class.java)
                .hasMessageContaining("Unrecognised file identifier - has this token already been used?")

    }

    @Test
    fun `populating set from local file requires file to be fully uploaded`()
    {
        val mockContext = mockPopulateFromLocalFileActionContext("user.name")
        val mockTokenHelper = getMockTokenHelper("user.name", "uid")
        val fakeCache = makeFakeCacheWithResumableInfo("uid", File("file.csv"), uploadFinished = false)

        val sut = BurdenEstimateUploadController(mockContext, mock(), mock(), mock(), mock(), mockTokenHelper, fakeCache)

        assertThatThrownBy { sut.populateBurdenEstimateSetFromLocalFile() }
                .isInstanceOf(BadRequest::class.java)
                .hasMessageContaining("This file has not been fully uploaded")

    }

    @Test
    fun `populating set from local file requires token to be validated`()
    {
        val mockContext = mockPopulateFromLocalFileActionContext("user.name")
        val fakeCache = makeFakeCacheWithResumableInfo("uid", File("file.csv"), uploadFinished = true)

        val sut = BurdenEstimateUploadController(mockContext, mock(), mock(), mock(), mock(), resumableInfoCache = fakeCache)

        assertThatThrownBy { sut.populateBurdenEstimateSetFromLocalFile() }
                .isInstanceOf(TokenValidationException::class.java)
                .hasMessageContaining("Could not verify token")

    }

    @Test
    fun `uploading file requires token to be validated`()
    {
        val mockContext = mockResumableUploadActionContext("uid")
        val fakeCache = makeFakeCacheWithResumableInfo("uid", File("file.csv"), uploadFinished = true)
        val sut = BurdenEstimateUploadController(mockContext, mock(), mock(), mock(), mock(), resumableInfoCache = fakeCache)

        assertThatThrownBy { sut.uploadBurdenEstimateFile() }
                .isInstanceOf(TokenValidationException::class.java)
                .hasMessageContaining("Could not verify token")

    }

    @Test
    fun `uploading file requires resumable query params`()
    {
        val mockContext = mockPopulateFromLocalFileActionContext("user.name")
        val fakeCache = makeFakeCacheWithResumableInfo("uid", File("file.csv"), uploadFinished = true)
        val sut = BurdenEstimateUploadController(mockContext, mock(), mock(), mock(), mock(), resumableInfoCache = fakeCache)

        assertThatThrownBy { sut.uploadBurdenEstimateFile() }
                .isInstanceOf(BadRequest::class.java)
                .hasMessageContaining("Missing required query parameter: resumableChunkNumber")

    }

    @Test
    fun `uploading file requires token username to match the current username`()
    {
        val mockContext = mockResumableUploadActionContext("uid", "file.csv", "wrong.name")
        val mockTokenHelper = getMockTokenHelper("user.name", "uid", "file.csv")
        val fakeCache = makeFakeCacheWithResumableInfo("uid", File("file.csv"), uploadFinished = true)
        val sut = BurdenEstimateUploadController(mockContext, mock(), mock(), mock(), mock(), mockTokenHelper, fakeCache)

        assertThatThrownBy { sut.uploadBurdenEstimateFile() }
                .isInstanceOf(BadRequest::class.java)
                .hasMessageContaining("The given token has not been issued for this user")

    }

    @Test
    fun `uploading file requires token filename to match the given filename`()
    {
        val mockContext = mockResumableUploadActionContext("uid", "wrong.file", "user.name")
        val mockTokenHelper = getMockTokenHelper("user.name", "uid", "file.csv")
        val fakeCache = makeFakeCacheWithResumableInfo("uid", File("file.csv"), uploadFinished = true)
        val sut = BurdenEstimateUploadController(mockContext, mock(), mock(), mock(), mock(), mockTokenHelper, fakeCache)

        assertThatThrownBy { sut.uploadBurdenEstimateFile() }
                .isInstanceOf(BadRequest::class.java)
                .hasMessageContaining("The given token has not been issued for the file wrong.file")

    }

    private fun getMockTokenHelper(username: String, uid: String, filename: String = "file.csv"): WebTokenHelper
    {
        return mock {
            on { verify(any(), eq(TokenType.UPLOAD)) } doReturn
                    mapOf(
                            "sub" to username,
                            "file-name" to filename,
                            "group-id" to "g1",
                            "scenario-id" to "s1",
                            "touchstone-id" to "t1",
                            "set-id" to 1,
                            "uid" to uid)
        }
    }

    private fun createTempCSVFile(fileName: String): File
    {
        val tempFile = File("$fileName.temp")
        tempFile.createNewFile()
        tempFile.writeText(normalCSVDataString)
        return tempFile
    }

    private fun makeFakeCacheWithResumableInfo(uid: String, file: File, uploadFinished: Boolean): ResumableInfoCache
    {
        val fakeInfo = ResumableInfo(totalChunks = 1, chunkSize = 100, uniqueIdentifier = uid, file = file)

        if (uploadFinished)
        {
            fakeInfo.uploadedChunks[1] = true
        }
        val cache = ResumableInfoCache()
        cache.put(fakeInfo)
        return cache
    }

    private fun populateAndCheckIfSetIsClosed(keepOpen: String?, expectedClosed: Boolean)
    {
        val timesExpected = if (expectedClosed) times(1) else never()

        val logic = mockLogic()
        val repo = mockEstimatesRepository()
        val mockContext = mockActionContext(keepOpen = keepOpen)
        val mockPostData = mockCSVPostData(normalCSVData)
        BurdenEstimateUploadController(mockContext, mockRepositories(repo), logic, repo, postDataHelper = mockPostData).populateBurdenEstimateSet()
        verify(logic, timesExpected).closeBurdenEstimateSet(defaultEstimateSet.id,
                "group-1", "touchstone-1", "scenario-1")
    }

    private fun mockActionContext(keepOpen: String? = null): ActionContext
    {
        return mock {
            on { username } doReturn "username"
            on { contentType() } doReturn "text/csv"
            on { params(":set-id") } doReturn "1"
            on { params(":file-name") } doReturn "file.csv"
            on { params(":group-id") } doReturn "group-1"
            on { params(":touchstone-version-id") } doReturn "touchstone-1"
            on { params(":scenario-id") } doReturn "scenario-1"
            on { queryParams("keepOpen") } doReturn keepOpen
        }
    }

    private fun mockPopulateFromLocalFileActionContext(user: String): ActionContext
    {
        return mock {
            on { username } doReturn user
            on { params(":set-id") } doReturn "1"
            on { params(":group-id") } doReturn "group-1"
            on { params(":touchstone-version-id") } doReturn "touchstone-1"
            on { params(":scenario-id") } doReturn "scenario-1"
            on { params(":token") } doReturn "faketoken"
        }
    }

    private fun mockResumableUploadActionContext(uniqueIdentifier: String,
                                                 fileName: String = "filename.csv",
                                                 user: String = "user.name"): ActionContext
    {
        return mock {
            on { username } doReturn user
            on { queryParams("resumableTotalChunks") } doReturn "10"
            on { queryParams("resumableChunkSize") } doReturn "100"
            on { queryParams("resumableChunkNumber") } doReturn "1"
            on { queryParams("resumableIdentifier") } doReturn uniqueIdentifier
            on { queryParams("resumableFilename") } doReturn fileName
        }
    }

    private fun <T : Any> verifyLogicIsInvokedToPopulateSet(
            actionContext: ActionContext,
            repo: BurdenEstimateRepository,
            logic: BurdenEstimateLogic,
            touchstoneVersionSet: SimpleDataSet<TouchstoneVersion, String>,
            actualData: Sequence<T>,
            expectedData: List<BurdenEstimateWithRunId>
    )
    {
        val postDataHelper = mock<PostDataHelper> {
            on { csvData<T>(any(), any()) } doReturn actualData
        }

        val sut = BurdenEstimateUploadController(actionContext, mockRepositories(repo), logic, repo, postDataHelper = postDataHelper)

        sut.populateBurdenEstimateSet()
        verify(touchstoneVersionSet).get("touchstone-1")
        verify(logic).populateBurdenEstimateSet(eq(1),
                eq("group-1"), eq("touchstone-1"), eq("scenario-1"),
                argWhere { it.toSet() == expectedData.toSet() }
        )
    }

    private val normalCSVData = listOf(
            BurdenEstimate("yf", 2000, 50, "AFG", "Afghanistan", 1000F, mapOf(
                    "deaths" to 10F,
                    "cases" to 100F
            )),
            BurdenEstimate("yf", 1980, 30, "AGO", "Angola", 2000F, mapOf(
                    "deaths" to 20F,
                    "cases" to 73.6F
            ))
    )

    private val normalCSVDataString = """
"disease", "year", "age", "country", "country_name", "cohort_size", "deaths", "cases"
   "yf",   2000,    50,     "AFG",  "Afghanistan",         1000,     10,    100
   "yf",   1980,    30,     "AGO",  "Angola",         2000,      20,    73.6
"""


    private fun mockRepositories(repo: BurdenEstimateRepository) = mock<Repositories> {
        on { burdenEstimates } doReturn repo
    }

}