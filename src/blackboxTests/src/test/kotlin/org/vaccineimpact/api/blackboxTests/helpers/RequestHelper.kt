package org.vaccineimpact.api.blackboxTests.helpers

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import khttp.extensions.fileLike
import khttp.responses.Response
import khttp.structures.files.FileLike
import org.vaccineimpact.api.models.helpers.ContentTypes
import org.vaccineimpact.api.models.ErrorInfo
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.api.models.permissions.ReifiedRole
import org.vaccineimpact.api.security.CookieName
import org.vaccineimpact.api.validateSchema.JSONValidator
import java.io.File
import java.net.URLEncoder

data class TokenLiteral(val value: String)
{
    override fun toString() = value
}

class RequestHelper
{
    init
    {
        CertificateHelper.disableCertificateValidation()
    }

    fun get(url: String, permissions: Set<ReifiedPermission>, acceptsContentType: String = ContentTypes.json): Response
    {
        val token = TestUserHelper().getTokenForTestUser(permissions)
        return get(url, token, acceptsContentType = acceptsContentType)
    }

    fun get(url: String, token: TokenLiteral? = null, acceptsContentType: String = ContentTypes.json): Response
    {
        return get(url, standardHeaders(acceptsContentType, token))
    }

    fun getWithCookie(url: String, token: TokenLiteral? = null, acceptsContentType: String = ContentTypes.json): Response
    {
        // Okay, this is bizarre - it seems like khttp's built in "send a cookie" functionality
        // doesn't actually do anything, so I've instead manually constructed the cookie header
        val headers = standardHeaders(acceptsContentType, token = null) +
                mapOf("Cookie" to "${CookieName.Main.cookieName}=$token")
        return get(url, headers)
    }

    fun getWithoutGzip(url: String, permissions: Set<ReifiedPermission>, contentType: String = ContentTypes.json): Response
    {
        val token = TestUserHelper().getTokenForTestUser(permissions)
        return get(url, headersWithoutGzip(contentType, token))
    }

    fun getOneTimeToken(url: String, token: TokenLiteral? = null): String
    {
        val encodedUrl = URLEncoder.encode("/v1$url", "UTF-8")
        val response = get("/onetime_token/?url=$encodedUrl", token)
        JSONValidator().validateSuccess(response.text)
        return response.montaguData()!!
    }

    fun post(url: String, permissions: Set<ReifiedPermission>, data: JsonObject): Response
    {
        return post(url, permissions, data.toJsonString(prettyPrint = true))
    }

    fun post(url: String, permissions: Set<ReifiedPermission>, data: String? = null): Response
    {
        val token = TestUserHelper().getTokenForTestUser(permissions)
        return post(url, data, token = token)
    }

    fun post(url: String, data: JsonObject,
             token: TokenLiteral? = null,
             acceptsContentType: String = ContentTypes.json
    ): Response
    {
        return post(url, data.toJsonString(prettyPrint = true),
                token = token,
                acceptsContentType = acceptsContentType)
    }

    fun post(url: String, data: String? = null,
             token: TokenLiteral? = null,
             acceptsContentType: String = ContentTypes.json
    ): Response
    {
        return post(
                url,
                standardHeaders(acceptsContentType, token),
                data
        )
    }

    fun postFile(url: String, fileContents: String, data: Map<String, String> = mapOf(), token: TokenLiteral? = null, acceptsContentType: String = ContentTypes.json): Response
    {
        val file = File("file")
        try
        {
            file.printWriter().use { w -> w.write(fileContents) }
            val files = listOf(file.fileLike())
            return postFiles(
                    url,
                    standardHeaders(acceptsContentType, token),
                    files,
                    data
            )
        }
        finally
        {
            file.delete()
        }
    }

    fun standardHeaders(acceptsContentType: String, token: TokenLiteral?): Map<String, String>
    {
        var headers = mapOf(
                "Accept" to acceptsContentType,
                "Accept-Encoding" to "gzip"
        )
        if (token != null)
        {
            headers += mapOf("Authorization" to "Bearer $token")
        }
        return headers
    }

    private fun headersWithoutGzip(contentType: String, token: TokenLiteral?): Map<String, String>
    {
        var headers = mapOf(
                "Accept" to contentType,
                "Accept-Encoding" to ""
        )
        if (token != null)
        {
            headers += mapOf("Authorization" to "Bearer $token")
        }
        return headers
    }

    private fun post(url: String, headers: Map<String, String>, data: Any?) = khttp.post(
            EndpointBuilder.build(url),
            data = data,
            headers = headers,
            allowRedirects = false
    )

    private fun postFiles(url: String, headers: Map<String, String>, files: List<FileLike>, data: Map<String, String> = mapOf()) = khttp.post(
            EndpointBuilder.build(url),
            headers = headers,
            files = files,
            data = data,
            allowRedirects = false
    )

    private fun get(url: String, headers: Map<String, String>) = khttp.get(EndpointBuilder.build(url), headers)
}

fun <T> Response.montaguData(): T?
{
    val data = this.json()["data"]
    if (data != "")
    {
        @Suppress("UNCHECKED_CAST")
        return data as T
    }
    else
    {
        return null
    }
}

fun Response.montaguErrors(): List<ErrorInfo>
{
    val errors = json()["errors"]
    if (errors is JsonArray<*>)
    {
        return errors.filterIsInstance<JsonObject>().map {
            ErrorInfo(it["code"] as String, it["message"] as String)
        }
    }
    else
    {
        throw Exception("Unable to get error collection from this response: " + this.text)
    }
}

fun Response.json() = try
{
    Parser().parse(StringBuilder(text)) as JsonObject
}
catch (e: RuntimeException)
{
    throw Exception("Unable to parse response as JSON: $text")
}