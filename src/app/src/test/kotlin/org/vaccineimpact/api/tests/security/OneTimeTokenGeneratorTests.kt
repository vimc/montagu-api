package org.vaccineimpact.api.tests.security

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions
import org.junit.Test
import org.pac4j.core.profile.CommonProfile
import org.vaccineimpact.api.app.repositories.TokenRepository
import org.vaccineimpact.api.app.security.OneTimeTokenGenerator
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.security.deflate
import org.vaccineimpact.api.security.inflate
import org.vaccineimpact.api.test_helpers.MontaguTests

// There are other tests for this class in TokenGeneratorTests. Those
// are for the old style onetime tokens, and will be removed eventually
class OneTimeTokenGeneratorTests : MontaguTests()
{
    @Test
    fun `can generate token`()
    {
        val attributes = mapOf(
                "permissions" to "a,b,c",
                "roles" to "x,y,z"
        )
        val profile = mock<CommonProfile> {
            on { this.attributes } doReturn attributes
            on { this.id } doReturn "username"
        }
        val helper = mock<WebTokenHelper>(){
            on {this.generateNewStyleOnetimeActionToken("/some/url/", "username", "a,b,c", "x,y,z")} doReturn "token"
        }

        val repo = mock<TokenRepository>()
        val sut = OneTimeTokenGenerator(repo, helper)
        val token = sut.getNewStyleOneTimeLinkToken("/some/url/", profile)
        verify(repo).storeToken(inflate(token))
        Assertions.assertThat(token).isEqualTo(deflate("token"))
        verify(helper).generateNewStyleOnetimeActionToken("/some/url/", "username", "a,b,c", "x,y,z")
    }
}