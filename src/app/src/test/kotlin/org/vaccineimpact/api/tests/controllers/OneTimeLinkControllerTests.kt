package org.vaccineimpact.api.tests.controllers

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.app.ActionContext
import org.vaccineimpact.api.app.ErrorHandler
import org.vaccineimpact.api.app.OneTimeLinkActionContext
import org.vaccineimpact.api.app.controllers.*
import org.vaccineimpact.api.app.errors.InvalidOneTimeLinkToken
import org.vaccineimpact.api.app.errors.UnexpectedError
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.TokenRepository
import org.vaccineimpact.api.app.repositories.UserRepository
import org.vaccineimpact.api.security.WebTokenHelper
import spark.Request

class OneTimeLinkControllerTests : ControllerTests<OneTimeLinkController>()
{
    override fun makeController(controllerContext: ControllerContext)
            = OneTimeLinkController(controllerContext, MontaguControllers(controllerContext))

    @Test
    fun `fails if token is not in repo`()
    {
        val repo = makeRepository(allowToken = false)
        val controller = makeController()
        assertThatThrownBy { controller.onetimeLink(actionContext(), repo) }
                .isInstanceOf(InvalidOneTimeLinkToken::class.java)
    }

    @Test
    fun `fails if token cannot be verified`()
    {
        val controller = makeController(helperAllowToken = false)
        assertThatThrownBy { controller.onetimeLink(actionContext(), makeRepository()) }
                .isInstanceOf(InvalidOneTimeLinkToken::class.java)
    }

    @Test
    fun `fails if token has wrong subject`()
    {
        val controller = makeController(claims = mapOf(
                "sub" to "Wrong subject"
        ))
        assertThatThrownBy { controller.onetimeLink(actionContext(), makeRepository()) }
                .isInstanceOf(InvalidOneTimeLinkToken::class.java)
    }

    @Test
    fun `performs action if token is valid`()
    {
        val otherController = mock<PasswordController>()
        val controller = makeController(
                passwordController = otherController,
                claims = mapOf(
                        "sub" to WebTokenHelper.oneTimeActionSubject,
                        "action" to "set-password",
                        "payload" to ":username=test.user"
                ),
                repos = mock {
                    on { user } doReturn mock<UserRepository>()
                }
        )
        controller.onetimeLink(actionContext(), makeRepository())
        verify(otherController).setPasswordForUser(any<OneTimeLinkActionContext>(), any(), any())
    }

    @Test
    fun `redirects if redirect url query parameter exists`()
    {
        val otherController = mock<PasswordController>()
        val controller = makeController(
                passwordController = otherController,
                claims = mapOf(
                        "sub" to WebTokenHelper.oneTimeActionSubject,
                        "action" to "set-password",
                        "payload" to ":username=test.user",
                        "query" to "redirectUrl=https://localhost"
                ),
                repos = mock {
                    on { user } doReturn mock<UserRepository>()
                }
        )

        val fakeContext = actionContext()
        controller.onetimeLink(fakeContext, makeRepository())
        verify(fakeContext, times(1)).redirect("https://localhost?result=encoded")
    }

    @Test
    fun `does not redirect if redirect url query parameter does not exist`()
    {
        val otherController = mock<PasswordController>()
        val controller = makeController(
                passwordController = otherController,
                claims = mapOf(
                        "sub" to WebTokenHelper.oneTimeActionSubject,
                        "action" to "set-password",
                        "payload" to ":username=test.user"
                ),
                repos = mock {
                    on { user } doReturn mock<UserRepository>()
                }
        )

        val fakeContext = actionContext()
        controller.onetimeLink(fakeContext, makeRepository())
        verify(fakeContext, times(0)).redirect(any())
    }

    @Test
    fun `does not redirect if redirect url query parameter is empty`()
    {
        val otherController = mock<PasswordController>()
        val controller = makeController(
                passwordController = otherController,
                claims = mapOf(
                        "sub" to WebTokenHelper.oneTimeActionSubject,
                        "action" to "set-password",
                        "payload" to ":username=test.user",
                        "query" to "redirectUrl="
                ),
                repos = mock {
                    on { user } doReturn mock<UserRepository>()
                }
        )

        val fakeContext = actionContext()
        controller.onetimeLink(fakeContext, makeRepository())
        verify(fakeContext, times(0)).redirect(any())
    }

    @Test
    fun `does not redirect if redirect url not valid`()
    {
        val otherController = mock<PasswordController>()
        val controller = makeController(
                passwordController = otherController,
                claims = mapOf(
                        "sub" to WebTokenHelper.oneTimeActionSubject,
                        "action" to "set-password",
                        "payload" to ":username=test.user",
                        "query" to "redirectUrl=www.something.com"
                ),
                repos = mock {
                    on { user } doReturn mock<UserRepository>()
                },
                allowRedirect = false
        )

        val fakeContext = actionContext()
        controller.onetimeLink(fakeContext, makeRepository())
        verify(fakeContext, times(0)).redirect(any())
    }

    @Test
    fun `redirects on error if redirect url query parameter exists`()
    {
        val otherController = mock<PasswordController> {
            on(it.setPasswordForUser(any(), any(), any())) doThrow Exception()
        }

        val mockErrorHandler = mock<ErrorHandler>() {
            on { logExceptionAndReturnMontaguError(any(), any()) } doReturn UnexpectedError()
        }

        val controller = makeController(
                passwordController = otherController,
                claims = mapOf(
                        "sub" to WebTokenHelper.oneTimeActionSubject,
                        "action" to "set-password",
                        "payload" to ":username=test.user",
                        "query" to "redirectUrl=https://localhost"
                ),
                repos = mock {
                    on { user } doReturn mock<UserRepository>()
                },
                errorHandler = mockErrorHandler
        )

        val fakeContext = actionContext()
        controller.onetimeLink(fakeContext, makeRepository())
        verify(fakeContext, times(1)).redirect("https://localhost?result=encoded")
    }

    @Test
    fun `logs error if redirect url query parameter exists`()
    {
        val otherController = mock<PasswordController> {
            on(it.setPasswordForUser(any(), any(), any())) doThrow Exception()
        }

        val mockErrorHandler = mock<ErrorHandler>() {
            on { logExceptionAndReturnMontaguError(any(), any()) } doReturn UnexpectedError()
        }

        val controller = makeController(
                passwordController = otherController,
                claims = mapOf(
                        "sub" to WebTokenHelper.oneTimeActionSubject,
                        "action" to "set-password",
                        "payload" to ":username=test.user",
                        "query" to "redirectUrl=https://localhost"
                ),
                repos = mock {
                    on { user } doReturn mock<UserRepository>()
                },
                errorHandler = mockErrorHandler
        )

        controller.onetimeLink(actionContext(), makeRepository())
        verify(mockErrorHandler, times(1)).logExceptionAndReturnMontaguError(any(), any())
    }

    private fun makeController(
            helperAllowToken: Boolean = true,
            claims: Map<String, Any> = mapOf("sub" to WebTokenHelper.oneTimeActionSubject),
            passwordController: PasswordController? = null,
            repos: Repositories? = null,
            allowRedirect: Boolean = true,
            errorHandler: ErrorHandler = mock<ErrorHandler>()
    )
            : OneTimeLinkController
    {
        val helper = makeTokenHelper(allowToken = helperAllowToken, claims = claims)
        val controllerContext = mockControllerContext(
                webTokenHelper = helper,
                repositories = repos ?: mock()
        )
        val otherController = passwordController ?: mock()
        val otherControllers = mock<MontaguControllers> {
            on { password } doReturn otherController
        }
        val redirectValidator = mock<RedirectValidator> {
            on { redirectUrlIsValid(any()) } doReturn allowRedirect
        }
        return OneTimeLinkController(controllerContext, otherControllers, errorHandler)
    }

    private fun makeTokenHelper(allowToken: Boolean, claims: Map<String, Any>): WebTokenHelper
    {
        return mock<WebTokenHelper> {
            on(it.encodeResult(any<String>())) doReturn "encoded"
            if (allowToken)
            {
                on(it.verify(any())) doReturn claims
            }
            else
            {
                on(it.verify(any())) doThrow RuntimeException("X")
            }
        }
    }

    private fun makeRepository(allowToken: Boolean = true) = mock<TokenRepository> {
        on { it.validateOneTimeToken(any()) } doReturn allowToken
    }

    private fun actionContext() = mock<ActionContext> {
        on { params(":token") } doReturn "TOKEN"
        on { request } doReturn mock<Request>()
    }
}