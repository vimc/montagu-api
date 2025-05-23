package org.vaccineimpact.api.app.security

import org.pac4j.core.config.Config
import org.pac4j.core.config.ConfigFactory
import org.pac4j.core.context.HttpConstants
import org.pac4j.core.context.WebContext
import org.pac4j.core.exception.http.HttpAction
import org.pac4j.http.client.direct.DirectBasicAuthClient
import org.pac4j.sparkjava.SparkWebContext
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.models.FailedAuthentication
import org.vaccineimpact.api.serialization.MontaguSerializer
import org.vaccineimpact.api.serialization.Serializer

class TokenIssuingConfigFactory(private val repositoryFactory: RepositoryFactory,
                                private val serializer: Serializer = MontaguSerializer.instance) : ConfigFactory
{
    override fun build(vararg parameters: Any?): Config
    {
        val authClient = TokenIssuingBasicAuthClient(DatabasePasswordAuthenticator())
        return Config(authClient).apply {
            httpActionAdapter = BasicAuthActionAdapter(repositoryFactory, serializer)
            addMethodMatchers()
        }
    }
}

class TokenIssuingBasicAuthClient(authenticator: DatabasePasswordAuthenticator) : DirectBasicAuthClient(authenticator) {
    override fun addAuthenticateHeader(context: WebContext) {
        // By default DirectBasicAuthClient adds an "WWW-Authenticate: Basic".
        // header to unauthorized requests, but this causes some browsers
        // (namely Chrome) to open a password popup, even on requests made by
        // XHR.
        //
        // We override that behaviour with a non-standard scheme to avoid this
        // issue. pac4j forces a `WWW-Authenticate` on 401 errors, so doing
        // nothing here wouldn't be enough.
        context.setResponseHeader("WWW-Authenticate", "X-Basic");
    }
}

class BasicAuthActionAdapter(repositoryFactory: RepositoryFactory, serializer: Serializer)
    : MontaguHttpActionAdapter(repositoryFactory, serializer)
{
    private val unauthorizedResponse: String = serializer.gson.toJson(FailedAuthentication("invalid_client"))

    override fun adapt(action: HttpAction, context: WebContext): Any? = when (action.code)
    {
        HttpConstants.UNAUTHORIZED -> haltWithError(action, context as SparkWebContext, unauthorizedResponse)
        else -> super.adapt(action, context)
    }
}
