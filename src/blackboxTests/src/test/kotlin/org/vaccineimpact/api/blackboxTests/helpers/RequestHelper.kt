package org.vaccineimpact.api.blackboxTests.helpers

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import khttp.responses.Response
import org.vaccineimpact.api.ContentTypes
import org.vaccineimpact.api.models.permissions.ReifiedPermission

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

    fun get(url: String, permissions: Set<ReifiedPermission>, contentType: String = ContentTypes.json): Response
    {
        val token = TestUserHelper().getTokenForTestUser(permissions)
        return get(url, token, contentType = contentType)
    }

    fun get(url: String, token: TokenLiteral? = null, contentType: String = ContentTypes.json): Response
    {
        var headers = mapOf(
                "Accept" to contentType,
                "Accept-Encoding" to "gzip"
        )
        if (token != null)
        {
            headers += mapOf("Authorization" to "Bearer $token")
        }
        return get(url, headers)
    }

    private fun get(url: String, headers: Map<String, String>)
            = khttp.get(EndpointBuilder.build(url), headers)
}

fun <T> Response.montaguData() : T?
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
fun Response.json() = Parser().parse(StringBuilder(text)) as JsonObject