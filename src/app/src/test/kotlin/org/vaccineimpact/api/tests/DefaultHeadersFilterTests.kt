package org.vaccineimpact.api.tests

import com.nhaarman.mockito_kotlin.*
import org.junit.Test
import org.vaccineimpact.api.app.DefaultHeadersFilter
import org.vaccineimpact.api.test_helpers.MontaguTests
import spark.Request
import spark.Response
import spark.route.HttpMethod
import javax.servlet.http.HttpServletResponse

class DefaultHeadersFilterTests : MontaguTests()
{
    val contentType = "txt/ancient-manuscripts"

    @Test
    fun `filter sets content type`()
    {
        val filter = DefaultHeadersFilter(contentType, HttpMethod.get, true)
        val mockResponse = filter.handleMockRequest(HttpMethod.get)
        verify(mockResponse).contentType = contentType
    }

    @Test
    fun `filter sets headers`()
    {
        val filter = DefaultHeadersFilter(contentType, HttpMethod.get, true)
        val mockResponse = filter.handleMockRequest(HttpMethod.get)
        verify(mockResponse).addHeader("Content-Encoding", "gzip")
        verify(mockResponse).addHeader("Access-Control-Allow-Credentials", "true")
    }

    @Test
    fun `filter doesn't set gzip header if request does not accept it`()
    {
        val filter = DefaultHeadersFilter(contentType, HttpMethod.get, true)
        val mockResponse = filter.handleMockRequest(HttpMethod.get, false)
        verify(mockResponse, never()).addHeader("Content-Encoding", "gzip")
    }

    @Test
    fun `filter is not applied if method does not match`()
    {
        val filter = DefaultHeadersFilter(contentType, HttpMethod.get, true)
        val mockResponse = filter.handleMockRequest(HttpMethod.post)
        verifyZeroInteractions(mockResponse)
    }

    @Test
    fun `filter does not set allow credentials header if told not to`()
    {
        val filter = DefaultHeadersFilter(contentType, HttpMethod.get, false)
        val mockResponse = filter.handleMockRequest(HttpMethod.get)
        verify(mockResponse).addHeader("Content-Encoding", "gzip")
        verify(mockResponse, never()).addHeader("Access-Control-Allow-Credentials", "true")
    }

    private fun DefaultHeadersFilter.handleMockRequest(requestMethod: HttpMethod, gzip: Boolean = true): HttpServletResponse
    {
        val mockRequest = mock<Request> {
            if (gzip)
            {
                on { headers("Accept-Encoding") } doReturn "gzip"
            }
            on { requestMethod() } doReturn requestMethod.toString()
        }
        val mockServletResponse = mock<HttpServletResponse>()
        val mockResponse = mock<Response> {
            on { raw() } doReturn mockServletResponse
        }
        handle(mockRequest, mockResponse)
        return mockServletResponse
    }
}