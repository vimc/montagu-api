package org.vaccineimpact.api.tests

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.json
import com.google.gson.JsonSyntaxException
import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThat
import org.jooq.exception.DataAccessException
import org.junit.Before
import org.junit.Test
import org.slf4j.Logger
import org.vaccineimpact.api.app.ErrorHandler
import org.vaccineimpact.api.app.PostgresErrorHandler
import org.vaccineimpact.api.app.errors.*
import org.vaccineimpact.api.models.ErrorInfo
import org.vaccineimpact.api.models.Result
import org.vaccineimpact.api.models.ResultStatus
import org.vaccineimpact.api.serialization.validation.ValidationException
import org.vaccineimpact.api.test_helpers.MontaguTests
import javax.servlet.http.HttpServletResponse

class ErrorHandlerTests : MontaguTests()
{
    private lateinit var handler: ErrorHandler
    private lateinit var request: spark.Request
    private lateinit var response: spark.Response
    private lateinit var postgresErrorHandler: PostgresErrorHandler
    private var logger: Logger = mock<Logger>()

    @Before
    fun makeHandler()
    {
        postgresErrorHandler = mock {
            on { handleException(any()) } doReturn DuplicateKeyError(emptyMap())
        }
        handler = ErrorHandler(logger, postgresHandler = postgresErrorHandler)
    }

    @Before
    fun makeMocks()
    {
        request = mock<spark.Request>() {
            on { uri() } doReturn "www.url.com"
        }
        response = mock<spark.Response> {
            on { raw() } doReturn mock<HttpServletResponse>()
        }
    }

    @Test
    fun `handleError sets Response body`()
    {
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
        assertThat(actualBody).isEqualTo(expectedBody)
    }

    @Test
    fun `handleError sets HTTP status code`()
    {
        val error = mock<MontaguError> {
            on { asResult() } doReturn Result(ResultStatus.FAILURE, "", emptyList())
            on { httpStatus } doReturn 999
        }

        handler.handleError(error, request, response)

        verify(response).status(999)
    }

    @Test
    fun `logExceptionAndReturnMontaguError just warns if exception is a MontaguError`()
    {
        val error = UnknownObjectError("id", "type")

        val result = handler.logExceptionAndReturnMontaguError(error, request)

        verify(logger, times(0)).error(any())
        verify(logger).warn("For request www.url.com, a UnknownObjectError occurred with the following problems: [Unknown type with id 'id']")

        assertThat(result).isEqualTo(error)
    }

    @Test
    fun `logExceptionAndReturnMontaguError logs error and returns unexpected error if exception is not a MontaguError`()
    {
        val error = Exception("message")

        val result = handler.logExceptionAndReturnMontaguError(error, request)

        verify(logger).error("An unhandled exception occurred", error)
        verify(logger).warn(any())

        assertThat(result is UnexpectedError).isTrue()
    }

    @Test
    fun `logExceptionAndReturnMontaguError converts JsonSyntaxException`()
    {
        val error = JsonSyntaxException("message")
        assertThat(handler.logExceptionAndReturnMontaguError(error, request))
                .isInstanceOf(UnableToParseJsonError::class.java)
    }

    @Test
    fun `logExceptionAndReturnMontaguError converts ValidationException`()
    {
        val error = ValidationException(emptyList())
        assertThat(handler.logExceptionAndReturnMontaguError(error, request))
                .isInstanceOf(ValidationError::class.java)
    }

    @Test
    fun `logExceptionAndReturnMontaguError passes DataAccessException to PostgresErrorHandler`()
    {
        val error = DataAccessException("message")
        assertThat(handler.logExceptionAndReturnMontaguError(error, request))
                .isInstanceOf(DuplicateKeyError::class.java)
        verify(postgresErrorHandler).handleException(error)
    }

    private fun getBodyAsJson(): JsonObject
    {
        val captor = argumentCaptor<String>()
        verify(response).body(captor.capture())
        return Parser().parse(StringBuilder(captor.firstValue)) as JsonObject
    }
}