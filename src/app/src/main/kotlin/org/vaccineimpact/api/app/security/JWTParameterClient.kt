package org.vaccineimpact.api.app.security

import org.pac4j.http.client.direct.ParameterClient
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.models.ErrorInfo
import org.vaccineimpact.api.security.WebTokenHelper

// This client receives the token as TokenCredentials and stores the result as JwtProfile
class JWTParameterClient(helper: WebTokenHelper, repositoryFactory: RepositoryFactory)
    : ParameterClient("access_token", OneTimeTokenAuthenticator(helper, repositoryFactory))
{
    init
    {
        @Suppress("UsePropertyAccessSyntax")
        this.setSupportGetRequest(true)
    }

    class Wrapper(helper: WebTokenHelper, repositoryFactory: RepositoryFactory): MontaguSecurityClientWrapper
    {
        override val client = JWTParameterClient(helper, repositoryFactory)
        override val authorizationError = ErrorInfo(
                "onetime-token-invalid",
                "Onetime token not supplied, or onetime token was invalid"
        )
    }
}