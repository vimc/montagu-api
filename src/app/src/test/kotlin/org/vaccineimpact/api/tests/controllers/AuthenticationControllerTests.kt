package org.vaccineimpact.api.tests.controllers

import com.nhaarman.mockito_kotlin.*
import org.junit.Test
import org.pac4j.core.profile.CommonProfile
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.FormHelpers
import org.vaccineimpact.api.app.HTMLForm
import org.vaccineimpact.api.app.controllers.AuthenticationController
import org.vaccineimpact.api.app.controllers.ControllerContext
import org.vaccineimpact.api.app.repositories.UserRepository
import org.vaccineimpact.api.app.security.USER_OBJECT
import org.vaccineimpact.api.security.MontaguUser
import org.vaccineimpact.api.security.UserProperties
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.time.Duration

class AuthenticationControllerTests : MontaguTests()
{
    @Test
    fun `successful authentication updates last logged in timestamp`()
    {
        val fakeUserRepo = mock<UserRepository>()

        val fakeUser = MontaguUser(UserProperties("testusername", "", "", "", null),
                listOf(),
                listOf())

        val fakeProfile = CommonProfile()
        fakeProfile.addAttribute(USER_OBJECT, fakeUser)

        val fakeContext = mock<ActionContext> {
            on { it.userProfile } doReturn fakeProfile
        }

        val fakeFormHelpers = mock<FormHelpers>() {
            on {
                it.checkForm(fakeContext, mapOf("grant_type" to "client_credentials"))
            } doReturn (HTMLForm.ValidForm())
        }

        val fakeWebTokenHelper = mock<WebTokenHelper> {
            on { it.generateToken(fakeUser) } doReturn "token"
            on { it.lifeSpan } doReturn Duration.ofHours(1)
        }

        val sut = AuthenticationController(fakeContext, fakeUserRepo,
                fakeFormHelpers, fakeWebTokenHelper)

        sut.authenticate()
        verify(fakeUserRepo).updateLastLoggedIn("testusername")
    }

    @Test
    fun `unsuccessful authentication does not update last logged in timestamp`()
    {
        val fakeUserRepo = mock<UserRepository>()

        val fakeContext = mock<ActionContext>()
        val fakeFormHelpers = mock<FormHelpers>() {
            on {
                it.checkForm(fakeContext, mapOf("grant_type" to "client_credentials"))
            } doReturn (HTMLForm.InvalidForm(""))
        }

        val fakeWebTokenHelper = mock<WebTokenHelper> ()

        val sut = AuthenticationController(fakeContext, fakeUserRepo,
                fakeFormHelpers, fakeWebTokenHelper)

        sut.authenticate()
        verify(fakeUserRepo, never()).updateLastLoggedIn(any())
    }

}
