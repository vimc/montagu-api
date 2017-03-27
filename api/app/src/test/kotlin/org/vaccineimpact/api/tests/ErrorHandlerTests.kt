package org.vaccineimpact.api.tests

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.json
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.vaccineimpact.api.app.ErrorHandler
import org.vaccineimpact.api.app.errors.MontaguError
import org.vaccineimpact.api.app.models.ErrorInfo
import org.vaccineimpact.api.app.models.Result
import org.vaccineimpact.api.app.models.ResultStatus

class ErrorHandlerTests : MontaguTests()
{
    private lateinit var handler: ErrorHandler
    private lateinit var request: spark.Request
    private lateinit var response: spark.Response

    @Before
    fun makeHandler()
    {
        handler = ErrorHandler()
    }

    @Before
    fun makeMocks()
    {
        request = mock<spark.Request>()
        response = mock<spark.Response>()
    }

    @Test
    fun handleErrorSetsResponseBody()
    {
        var body: String? = null
        val error = mock<MontaguError> {
            on { asResult() } doReturn Result(
                    ResultStatus.FAILURE,
                    "TestString",
                    listOf(ErrorInfo("code", "message"))
            )
        }

        handler.handleError(error, request, response)

        val actualBody = getBodyAsJson()
        val expectedBody = json {
            obj(
                    "data" to "TestString",
                    "errors" to array(
                            obj(
                                    "code" to "code",
                                    "message" to "message"
                            )
                    ),
                    "status" to "failure"
            )
        }
        assertEquals(expectedBody, actualBody)
    }

    @Test
    fun handleErrorSetsHttpStatusCode()
    {
        val error = mock<MontaguError> {
            on { asResult() } doReturn Result(ResultStatus.FAILURE, "", emptyList())
            on { httpStatus } doReturn 999
        }

        handler.handleError(error, request, response)

        verify(response).status(999)
    }

    private fun getBodyAsJson(): JsonObject
    {
        val captor = argumentCaptor<String>()
        verify(response).body(captor.capture())
        return Parser().parse(StringBuilder(captor.firstValue)) as JsonObject
    }
}