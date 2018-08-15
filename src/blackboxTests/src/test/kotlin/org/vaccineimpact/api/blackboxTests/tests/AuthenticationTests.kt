package org.vaccineimpact.api.blackboxTests.tests

import com.auth0.jwt.JWT
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.json
import khttp.options
import khttp.post
import khttp.responses.Response
import khttp.structures.authorization.BasicAuthorization
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.EndpointBuilder
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.Tables.APP_USER
import org.vaccineimpact.api.security.UserHelper
import org.vaccineimpact.api.security.inflated
import org.vaccineimpact.api.test_helpers.DatabaseTest

data class ResponseWithJsonBody(val response: Response, val body: JsonObject)

class AuthenticationTests : DatabaseTest()
{
    @Before
    fun addUser()
    {
        //CertificateHelper.disableCertificateValidation()
        JooqContext().use {
            UserHelper.saveUser(it.dsl, "user", "Full Name", "email@example.com", "password")
        }
    }

    @Test
    fun `authentication fails without BasicAuth header`()
    {
        val result = post("email@example.com", "password", includeAuth = false)
        assertDoesNotAuthenticate(result.body)
    }

    @Test
    fun `unknown email does not authenticate`()
    {
        val result = post("bad@example.com", "password")
        assertDoesNotAuthenticate(result.body)
    }

    @Test
    fun `incorrect password does not authenticate`()
    {
        val result = post("email@example.com", "bad_password")
        assertDoesNotAuthenticate(result.body)
    }

    @Test
    fun `cannot login if use does not have password`()
    {
        JooqContext().use {
            it.dsl.update(APP_USER).set(mapOf(APP_USER.PASSWORD_HASH to null)).execute()
        }
        assertDoesNotAuthenticate(post("email@example.com", "").body)
        assertDoesNotAuthenticate(post("email@example.com", "password").body)
    }

    @Test
    fun `cannot authenticate with username`()
    {
        val result = post("user", "password")
        assertDoesNotAuthenticate(result.body)
    }

    @Test
    fun `correct password does authenticate`()
    {
        val response = post("email@example.com", "password")
        assertDoesAuthenticate(response.body)

        val cookie = response.response.headers["Set-Cookie"]!!
        assertThat(cookie).contains("HttpOnly")
        assertThat(cookie).contains("SameSite=Strict")

        val token = cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"))
        val claims = JWT.decode(token.inflated())
        val tokenType = claims.getClaim("token_type")
        assertThat(tokenType.asString()).isEqualTo("BEARER")
    }

    @Test
    fun `email authentication is not case sensitive`()
    {
        val result = post("EMAIL@example.cOm", "password")
        assertDoesAuthenticate(result.body)
    }

    @Test
    fun `can get OPTIONS for authentication endpoint`()
    {
        val result = options(url)
        assertThat(result.statusCode).isEqualTo(200)
    }

    private fun assertDoesAuthenticate(result: JsonObject)
    {
        assertThat(result).doesNotContainKey("error")
        assertThat(result).containsKey("access_token")
        assertThat(result["token_type"]).isEqualTo("bearer")
        assertThat(isLong(result["expires_in"].toString()))
    }

    private fun assertDoesNotAuthenticate(result: JsonObject)
    {
        assertThat(result).isEqualTo(json {
            obj(
                    "error" to "Bad credentials"
            )
        })
    }

    private fun isLong(raw: String): Boolean
    {
        try
        {
            raw.toLong()
            return true
        }
        catch (e: NumberFormatException)
        {
            return false
        }
    }

    companion object
    {
        val url = EndpointBuilder.build("/authenticate/")

        fun post(username: String, password: String, includeAuth: Boolean = true): ResponseWithJsonBody
        {
            val auth = if (includeAuth) BasicAuthorization(username, password) else null
            val response =  post(url,
                    data = mapOf("grant_type" to "client_credentials"),
                    auth = auth
            )
            val text = response.text
            println(text)
            val body = Parser().parse(StringBuilder(text)) as JsonObject
            return ResponseWithJsonBody(response, body)
        }
    }
}