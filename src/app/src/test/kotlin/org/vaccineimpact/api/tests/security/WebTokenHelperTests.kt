package org.vaccineimpact.api.tests.security

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.Test
import org.vaccineimpact.api.app.repositories.TokenRepository
import org.vaccineimpact.api.security.OneTimeTokenAuthenticator
import org.vaccineimpact.api.models.ErrorInfo
import org.vaccineimpact.api.models.Result
import org.vaccineimpact.api.models.ResultStatus
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.api.models.permissions.ReifiedRole
import org.vaccineimpact.api.security.*
import org.vaccineimpact.api.serialization.Serializer
import org.vaccineimpact.api.test_helpers.MontaguTests
import org.vaccineimpact.api.tests.mocks.MockRepositoryFactory
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.util.*

class WebTokenHelperTests : MontaguTests()
{
    lateinit var sut: WebTokenHelper
    val properties = UserProperties(
            username = "test.user",
            name = "Test User",
            email = "test@example.com",
            passwordHash = "",
            lastLoggedIn = null
    )
    val roles = listOf(
            ReifiedRole("roleA", Scope.Global()),
            ReifiedRole("roleB", Scope.Specific("prefix", "id"))
    )
    val permissions = listOf(
            ReifiedPermission("p1", Scope.Global()),
            ReifiedPermission("p2", Scope.Specific("prefix", "id"))
    )

    @Before
    fun setUp()
    {
        createHelper()
    }

    private fun createHelper(serializer: Serializer = mock<Serializer>())
    {
        sut = WebTokenHelper(KeyHelper.keyPair, serializer = serializer)
    }

    @Test
    fun `can generate bearer token`()
    {
        val token = sut.generateToken(InternalUser(properties, roles, permissions))
        val claims = sut.verify(token.deflated(), TokenType.BEARER, mock())

        assertThat(claims["iss"]).isEqualTo("vaccineimpact.org")
        assertThat(claims["token_type"]).isEqualTo("BEARER")
        assertThat(claims["sub"]).isEqualTo("test.user")
        assertThat(claims["exp"]).isInstanceOf(Date::class.java)
        assertThat(claims["roles"]).isEqualTo("*/roleA,prefix:id/roleB")
        assertThat(claims["permissions"]).isEqualTo("*/p1,prefix:id/p2")
    }

    @Test
    fun `can generate long-lived bearer token`()
    {
        val token = sut.generateToken(InternalUser(properties, roles, permissions), lifeSpan = Duration.ofDays(365))
        val claims = sut.verify(token.deflated(), TokenType.BEARER, mock())

        assertThat(claims["exp"] as Date).isAfter(Date.from(Instant.now() + Duration.ofDays(364)))
    }

    @Test
    fun `can generate shiny token for non report reviewer`()
    {
        val token = sut.generateShinyToken(InternalUser(properties, roles, permissions))
        val claims = sut.verify(token.deflated(), TokenType.SHINY, mock())

        assertThat(claims["iss"]).isEqualTo("vaccineimpact.org")
        assertThat(claims["token_type"]).isEqualTo("SHINY")
        assertThat(claims["sub"]).isEqualTo("test.user")
        assertThat(claims["exp"]).isInstanceOf(Date::class.java)
        assertThat(claims["allowed_shiny"]).isEqualTo("false")
    }

    @Test
    fun `can generate shiny token for report reviewer`()
    {
        val permissions = listOf(
                ReifiedPermission("reports.review", Scope.Global())
        )
        val token = sut.generateShinyToken(InternalUser(properties, roles, permissions))
        val claims = sut.verify(token.deflated(), TokenType.SHINY, mock())

        assertThat(claims["iss"]).isEqualTo("vaccineimpact.org")
        assertThat(claims["token_type"]).isEqualTo("SHINY")
        assertThat(claims["sub"]).isEqualTo("test.user")
        assertThat(claims["exp"]).isInstanceOf(Date::class.java)
        assertThat(claims["allowed_shiny"]).isEqualTo("true")
    }

    @Test
    fun `can generate old style onetime action token`()
    {
        val queryString = "query=answer"
        val token = sut.generateOldStyleOneTimeActionToken("test-action", mapOf(
                ":a" to "1",
                ":b" to "2"
        ), queryString, WebTokenHelper.oneTimeLinkLifeSpan, "test.user")
        val claims = sut.verify(token.deflated(), TokenType.LEGACY_ONETIME, mock())

        assertThat(claims["iss"]).isEqualTo("vaccineimpact.org")
        assertThat(claims["token_type"]).isEqualTo("LEGACY_ONETIME")
        assertThat(claims["sub"]).isEqualTo("onetime_link")
        assertThat(claims["exp"]).isInstanceOf(Date::class.java)
        assertThat(claims["action"]).isEqualTo("test-action")
        assertThat(claims["payload"]).isEqualTo(":a=1&:b=2")
        assertThat(claims["query"]).isEqualTo(queryString)
        assertThat(claims["username"]).isEqualTo("test.user")
    }

    @Test
    fun `can generate old style onetime action token with null query string`()
    {
        val token = sut.generateOldStyleOneTimeActionToken("test-action", mapOf(
                ":a" to "1",
                ":b" to "2"
        ), null, WebTokenHelper.oneTimeLinkLifeSpan, "test.user")
        val claims = sut.verify(token.deflated(), TokenType.LEGACY_ONETIME, mock())

        assertThat(claims["iss"]).isEqualTo("vaccineimpact.org")
        assertThat(claims["token_type"]).isEqualTo("LEGACY_ONETIME")
        assertThat(claims["sub"]).isEqualTo("onetime_link")
        assertThat(claims["exp"]).isInstanceOf(Date::class.java)
        assertThat(claims["action"]).isEqualTo("test-action")
        assertThat(claims["payload"]).isEqualTo(":a=1&:b=2")
        assertThat(claims["query"]).isNull()
        assertThat(claims["username"]).isEqualTo("test.user")
    }

    @Test
    fun `token fails validation when issuer is wrong`()
    {
        val claims = sut.claims(InternalUser(properties, roles, permissions))
        val badToken = sut.generator.generate(claims.plus("iss" to "unexpected.issuer"))
        val verifier = MontaguTokenAuthenticator(sut, TokenType.BEARER)
        assertThat(verifier.validateToken(badToken)).isNull()
        assertThatThrownBy { sut.verify(badToken.deflated(), TokenType.BEARER, mock()) }
    }

    @Test
    fun `token fails validation when token type is wrong`()
    {
        val claims = sut.claims(InternalUser(properties, roles, permissions))
        val badToken = sut.generator.generate(claims.plus("token_type" to "unexpected.type"))
        val verifier = MontaguTokenAuthenticator(sut, TokenType.BEARER)
        assertThat(verifier.validateToken(badToken)).isNull()
        assertThatThrownBy { sut.verify(badToken.deflated(), TokenType.BEARER, mock()) }
    }

    @Test
    fun `token fails validation when token is old`()
    {
        val claims = sut.claims(InternalUser(properties, roles, permissions))
        val badToken = sut.generator.generate(claims.plus("exp" to Date.from(Instant.now())))
        val verifier = MontaguTokenAuthenticator(sut, TokenType.BEARER)
        assertThat(verifier.validateToken(badToken)).isNull()
        assertThatThrownBy { sut.verify(badToken.deflated(), TokenType.BEARER, mock()) }
    }

    @Test
    fun `token fails validation when token is signed by wrong key`()
    {
        val sauron = WebTokenHelper(KeyHelper.generateKeyPair())
        val evilToken = sauron.generateToken(InternalUser(properties, roles, permissions))
        val verifier = MontaguTokenAuthenticator(sut, TokenType.BEARER)
        assertThat(verifier.validateToken(evilToken)).isNull()
        assertThatThrownBy { sut.verify(evilToken.deflated(), TokenType.BEARER, mock()) }
    }

    @Test
    fun `can generate new style onetime action token`()
    {
        val permissions = "*/can-login,modelling-group:IC-Garske/estimates.read"
        val roles = "*/user,modelling-group:IC-Garske/member"
        val mockTokenChecker = mock<OneTimeTokenChecker> {
            on { checkToken(any()) } doReturn true
        }

        val token = sut.generateNewStyleOnetimeActionToken("/some/url/", "username", permissions, roles)
        val claims = sut.verify(token.deflated(), TokenType.ONETIME, mockTokenChecker)
        assertThat(claims["iss"]).isEqualTo("vaccineimpact.org")
        assertThat(claims["token_type"]).isEqualTo("ONETIME")
        assertThat(claims["sub"]).isEqualTo("username")
        assertThat(claims["exp"] as Date).isAfter(Date.from(Instant.now()))
        assertThat(claims["permissions"]).isEqualTo(permissions)
        assertThat(claims["roles"]).isEqualTo(roles)
        assertThat(claims["url"]).isEqualTo("/some/url/")
        assertThat(claims["nonce"]).isNotNull()
    }

    @Test
    fun `new style onetime token fails validation when token fails onetime check`()
    {
        val mockTokenChecker = mock<OneTimeTokenChecker> {
            on { checkToken(any()) } doReturn false
        }
        val token = sut.generateNewStyleOnetimeActionToken("/some/url/", "username", "", "")
        assertThatThrownBy { sut.verify(token.deflated(), TokenType.ONETIME, mockTokenChecker) }
    }

    @Test
    fun `new style onetime token fails validation when URL is blank`()
    {
        val mockTokenChecker = mock<OneTimeTokenChecker> {
            on { checkToken(any()) } doReturn true
        }
        val token = sut.generateNewStyleOnetimeActionToken("", "username", "", "")
        assertThatThrownBy { sut.verify(token.deflated(), TokenType.ONETIME, mockTokenChecker) }
    }

    @Test
    fun `can encode result token`()
    {
        val serializer = mock<Serializer> {
            on { toJson(argThat { status == ResultStatus.SUCCESS }) } doReturn "successResult"
        }

        createHelper(serializer)
        val result = Result(ResultStatus.SUCCESS, "OK", listOf())
        val token = sut.encodeResult(result)
        val claims = sut.verify(token.deflated(), TokenType.API_RESPONSE, mock())

        assertThat(claims["token_type"]).isEqualTo("API_RESPONSE")
        assertThat(claims["sub"]).isEqualTo("api_response")
        assertThat(claims["result"]).isEqualTo("successResult")

    }

    @Test
    fun `can encode error result token`()
    {
        val serializer = mock<Serializer> {
            on { toJson(argThat { status == ResultStatus.FAILURE }) } doReturn "errorResult"
        }

        createHelper(serializer)
        val result = Result(ResultStatus.FAILURE, null,
                listOf(ErrorInfo("some-code", "some message")))
        val token = sut.encodeResult(result)
        val claims = sut.verify(token.deflated(), TokenType.API_RESPONSE, mock())

        assertThat(claims["token_type"]).isEqualTo("API_RESPONSE")
        assertThat(claims["sub"]).isEqualTo("api_response")
        assertThat(claims["result"]).isEqualTo("errorResult")
    }
}