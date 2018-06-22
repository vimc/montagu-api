package org.vaccineimpact.api.app.security

import org.pac4j.core.context.WebContext
import org.pac4j.core.credentials.TokenCredentials
import org.pac4j.core.credentials.extractor.ParameterExtractor
import org.pac4j.http.client.direct.ParameterClient
import org.vaccineimpact.api.models.ErrorInfo
import org.vaccineimpact.api.security.*

// This client receives the token as TokenCredentials and stores the result as JwtProfile
class JWTCompressedParameterClient(helper: CompressedWebTokenHelper, oneTimeTokenChecker: OneTimeTokenChecker)
    : ParameterClient("access_token", OneTimeTokenAuthenticator(helper, oneTimeTokenChecker))
{
    init
    {
        @Suppress("UsePropertyAccessSyntax")
        this.setSupportGetRequest(true)
        credentialsExtractor = CompressedParameterExtractor(
                parameterName, isSupportGetRequest, isSupportPostRequest, name)
    }

    class Wrapper(helper: CompressedWebTokenHelper, oneTimeTokenChecker: OneTimeTokenChecker): MontaguSecurityClientWrapper
    {
        override val client = JWTCompressedParameterClient(helper, oneTimeTokenChecker)
        override val authorizationError = ErrorInfo(
                "onetime-token-invalid",
                "Onetime token not supplied, or onetime token was invalid"
        )
    }
}

class CompressedParameterExtractor(
        parameterName: String,
        supportGetRequest: Boolean,
        supportPostRequest: Boolean,
        clientName: String)
    : ParameterExtractor(parameterName, supportGetRequest, supportPostRequest, clientName)
{
    override fun extract(context: WebContext?): TokenCredentials?
    {
        val wrapped = super.extract(context)
        return if (wrapped != null)
        {
            TokenCredentials(inflate(wrapped.token), wrapped.clientName)
        }
        else null
    }
}
