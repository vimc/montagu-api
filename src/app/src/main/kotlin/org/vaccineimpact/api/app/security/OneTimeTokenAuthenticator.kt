package org.vaccineimpact.api.app.security

import com.nimbusds.jwt.JWT
import org.pac4j.core.credentials.TokenCredentials
import org.pac4j.core.exception.CredentialsException
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.security.MontaguTokenAuthenticator
import org.vaccineimpact.api.security.WebTokenHelper

class OneTimeTokenAuthenticator(
        tokenHelper: WebTokenHelper,
        private val repositoryFactory: RepositoryFactory
) : MontaguTokenAuthenticator(tokenHelper)
{
    override fun createJwtProfile(credentials: TokenCredentials, jwt: JWT)
    {
        super.createJwtProfile(credentials, jwt)
        checkTokenAgainstRepository(credentials)

        val claims = jwt.jwtClaimsSet
        val sub = claims.subject
        if (sub != WebTokenHelper.oneTimeActionSubject)
        {
            throw CredentialsException("Expected 'sub' claim to be ${WebTokenHelper.oneTimeActionSubject}")
        }

        val url = claims.getClaim("url")
        @Suppress("UselessCallOnNotNull")
        if (url.toString().isNullOrEmpty())
        {
            throw CredentialsException("No 'url' claim provided. Token is invalid")
        }
    }

    private fun checkTokenAgainstRepository(credentials: TokenCredentials)
    {
        // This transaction is immediately committed, regardless of result
        val tokenIsValid = repositoryFactory.inTransaction { repos ->
            !repos.token.validateOneTimeToken(credentials.token)
        }
        if (!tokenIsValid)
        {
            throw CredentialsException("Token has already been used (or never existed)")
        }
    }
}
