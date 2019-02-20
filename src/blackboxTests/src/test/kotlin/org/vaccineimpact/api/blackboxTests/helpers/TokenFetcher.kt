package org.vaccineimpact.api.blackboxTests.helpers

class TokenFetcher
{
    fun getToken(email: String, password: String): TokenResponse
    {
        val url = EndpointBuilder.build("/authenticate/")
        val auth = khttp.structures.authorization.BasicAuthorization(email, password)
        val response = khttp.post(url,
                data = mapOf("grant_type" to "client_credentials"),
                auth = auth
        )
        val json = com.beust.klaxon.Parser().parse(StringBuilder(response.text)) as com.beust.klaxon.JsonObject
        if (json.containsKey("error"))
        {
            return TokenResponse.Error(json["error"] as String)
        }
        else if (json.containsKey("access_token"))
        {
            return TokenResponse.Token(TokenLiteral(json["access_token"] as String))
        }
        else
        {
            throw Exception("Malformed response from authentication endpoint: " + response.text)
        }
    }

    fun getUploadToken(setUrl: String, fileName: String, token: TokenLiteral) : String {
        val response = RequestHelper().get("$setUrl/actions/request-upload/$fileName/", token = token)
        val json = com.beust.klaxon.Parser().parse(StringBuilder(response.text)) as com.beust.klaxon.JsonObject
        return json["data"] as String
    }

    sealed class TokenResponse
    {
        class Token(val token: TokenLiteral): TokenResponse()
        class Error(val message: String): TokenResponse()
    }
}