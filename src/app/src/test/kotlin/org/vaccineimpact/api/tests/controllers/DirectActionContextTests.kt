package org.vaccineimpact.api.tests.controllers

import com.beust.klaxon.json
import com.nhaarman.mockito_kotlin.*
import org.apache.commons.fileupload.FileItemStream
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.pac4j.core.profile.CommonProfile
import org.pac4j.core.profile.ProfileManager
import org.pac4j.core.profile.UserProfile
import org.pac4j.sparkjava.SparkWebContext
import org.vaccineimpact.api.app.context.DirectActionContext
import org.vaccineimpact.api.app.context.postData
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.errors.MissingRequiredMultipartParameterError
import org.vaccineimpact.api.app.requests.MultipartData
import org.vaccineimpact.api.db.ConfigWrapper
import org.vaccineimpact.api.models.Scope
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.api.security.CookieName
import org.vaccineimpact.api.test_helpers.MontaguTests
import spark.Request
import spark.Response
import java.io.ByteArrayInputStream
import javax.servlet.http.HttpServletRequest

class DirectActionContextTests : MontaguTests()
{

    @Test
    fun `can get user profile`()
    {
        val profile = CommonProfile()
        val context = DirectActionContext(mockWebContext(), mockProfileManager(profile))
        assertThat(context.userProfile).isEqualTo(profile)
    }

    @Test
    fun `can get user permissions`()
    {
        val profile = CommonProfile().apply {
            this.permissions = setOf(
                    "*/can-login",
                    "modelling-group:IC-Garske/coverage.read"
            )
        }
        val context = DirectActionContext(mockWebContext(), mockProfileManager(profile))
        assertThat(context.permissions).hasSameElementsAs(listOf(
                ReifiedPermission("can-login", Scope.Global()),
                ReifiedPermission("coverage.read", Scope.Specific("modelling-group", "IC-Garske"))
        ))
    }

    @Test
    fun `requirePermission throws exception is user does not have permission`()
    {
        val profile = CommonProfile().apply {
            this.permissions = setOf("*/can-login")
        }
        val context = DirectActionContext(mockWebContext(), mockProfileManager(profile))
        // Does not throw exception
        context.requirePermission(ReifiedPermission.parse("*/can-login"))
        assertThatThrownBy { context.requirePermission(ReifiedPermission.parse("*/can-dance")) }
                .hasMessageContaining("*/can-dance")
    }

    data class Model(val a: Int, val b: List<String>, val c: Model?)

    @Test
    fun `can deserialize post data`()
    {
        val body = json {
            obj(
                    "a" to 1,
                    "b" to array("x", "y"),
                    "c" to obj(
                            "a" to 100,
                            "b" to array("z")
                    )
            )
        }
        val mockRequest = mock<Request> {
            on { body() } doReturn body.toJsonString(prettyPrint = true)
        }
        val mockWebContext = mock<SparkWebContext> {
            on { sparkRequest } doReturn mockRequest
        }
        val context = DirectActionContext(mockWebContext)
        val model = context.postData<Model>()
        assertThat(model).isEqualTo(Model(1, listOf("x", "y"), Model(100, listOf("z"), null)))
    }

    @Test
    fun `throws BadRequest if getPart called on non multipart request`()
    {
        val mockData = mock<MultipartData> {
            on { isMultipartContent(any()) } doReturn false
        }
        val context = DirectActionContext(mockWebContext(contentType = "some/content/type"))
        assertThatThrownBy { context.getPart("partB", mockData) }
                .isInstanceOf(BadRequest::class.java)
                .hasMessageContaining("Trying to extract a part from multipart/form-data but this request is of type some/content/type")
    }

    @Test
    fun `throw exception if getPart called on non existent part`()
    {
        val data = sequenceOf(
                mockFileItem("partA", "Message A", "text/a"),
                mockFileItem("partC", "Message C", "text/c")
        )
        val mockData = mock<MultipartData> {
            on { isMultipartContent(any()) } doReturn true
            on { parts(any()) } doReturn data
        }
        val context = DirectActionContext(mockWebContext())
        assertThatThrownBy { context.getPart("partB", mockData) }
                .isInstanceOf(MissingRequiredMultipartParameterError::class.java)
                .hasMessageContaining("You must supply a 'partB' parameter in the multipart body")
    }

    @Test
    fun `can get part`()
    {
        val data = sequenceOf(
                mockFileItem("partA", "Message A", "text/a"),
                mockFileItem("partB", "Message B", "text/b"),
                mockFileItem("partC", "Message C", "text/c")
        )
        val mockData = mock<MultipartData> {
            on { isMultipartContent(any()) } doReturn true
            on { parts(any()) } doReturn data
        }
        val context = DirectActionContext(mockWebContext())
        val actual = context.getPart("partB", mockData)
        assertThat(actual.reader().readText()).isEqualTo("Message B")
    }

    @Test
    fun `cookie is Secure if allowLocalhost is false`()
    {
        val config = mock<ConfigWrapper> {
            on { it.getBool("allow.localhost") } doReturn false
        }
        val mockResponse = mock<Response>()
        val webContext = mock<SparkWebContext> {
            on { it.sparkResponse } doReturn mockResponse
        }
        val sut = DirectActionContext(webContext)
        sut.setCookie(CookieName.Main, "TOKEN", config)
        verify(mockResponse).header(eq("Set-Cookie"), eq("montagu_jwt_token=TOKEN; Path=/; Secure; HttpOnly; SameSite=Strict"))
    }

    @Test
    fun `cookie is not Secure if allowLocalhost is true`()
    {
        val config = mock<ConfigWrapper> {
            on { it.getBool("allow.localhost") } doReturn true
        }
        val mockResponse = mock<Response>()
        val webContext = mock<SparkWebContext> {
            on { it.sparkResponse } doReturn mockResponse
        }
        val sut = DirectActionContext(webContext)
        sut.setCookie(CookieName.ModelReview, "TOKEN", config)
        verify(mockResponse).header(eq("Set-Cookie"), eq("jwt_token=TOKEN; Path=/; HttpOnly; SameSite=Strict"))
    }

    @Test
    fun `can get auth token from cookie`()
    {
        val mockRequest = mock<Request> {
            on { it.cookie("montagu_jwt_token") } doReturn "test_montagu_cookie_token"
        }
        val webContext = mock<SparkWebContext> {
            on { it.sparkRequest } doReturn mockRequest
        }

        val sut = DirectActionContext(webContext)
        val authToken = sut.authenticationToken()

        assertThat(authToken).isEqualTo("test_montagu_cookie_token")
    }

    @Test
    fun `can get auth token from header`()
    {
        val mockRequest = mock<Request> {
            on { it.headers("Authorization") } doReturn "Bearer test_montagu_header_token"
        }
        val webContext = mock<SparkWebContext> {
            on { it.sparkRequest } doReturn mockRequest
        }

        val sut = DirectActionContext(webContext)
        val authToken = sut.authenticationToken()

        assertThat(authToken).isEqualTo("test_montagu_header_token")
    }

    private fun mockFileItem(name: String, contents: String, contentType: String): FileItemStream
    {
        return mock {
            on { fieldName } doReturn name
            on { openStream() } doReturn ByteArrayInputStream(contents.toByteArray())
            on { this.contentType } doReturn contentType
        }
    }

    private fun mockWebContext(contentType: String? = null): SparkWebContext
    {
        val mockServletRequest = mock<HttpServletRequest>()
        val mockRequest = mock<Request> {
            on { raw() } doReturn mockServletRequest
            on { contentType() } doReturn contentType
        }
        return mock {
            on { sparkRequest } doReturn mockRequest
        }
    }

    private fun mockProfileManager(profile: UserProfile? = null): ProfileManager {
        return mock<ProfileManager> {
            on { it.profiles } doReturn listOf(profile)
        }
    }
}