package org.vaccineimpact.api.app.security

import org.pac4j.core.config.Config
import org.pac4j.core.config.ConfigFactory
import org.pac4j.core.context.HttpConstants
import org.pac4j.core.profile.CommonProfile
import org.pac4j.jwt.profile.JwtProfile
import org.pac4j.sparkjava.SparkWebContext
import org.vaccineimpact.api.app.context.DirectActionContext
import org.vaccineimpact.api.app.errors.MissingRequiredPermissionError
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.models.ErrorInfo
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.security.WebTokenHelper

class TokenVerifyingConfigFactory(
        tokenHelper: WebTokenHelper,
        private val requiredPermissions: Set<PermissionRequirement>,
        private val repositoryFactory: RepositoryFactory
) : ConfigFactory
{
    private val wrappedClients: List<MontaguSecurityClientWrapper> = listOf(
            JWTHeaderClient.Wrapper(tokenHelper),
            JWTParameterClient.Wrapper(tokenHelper, repositoryFactory)
    )
    private val clients = wrappedClients.map { it.client }

    override fun build(vararg parameters: Any?): Config
    {
        clients.forEach {
            it.addAuthorizationGenerator({ _, profile -> extractPermissionsFromToken(profile) })
        }
        return Config(clients).apply {
            setAuthorizer(MontaguAuthorizer(requiredPermissions))
            addMethodMatchers()
            httpActionAdapter = TokenActionAdapter(wrappedClients, repositoryFactory)
        }
    }

    fun allClients() = clients.joinToString { it::class.java.simpleName }

    private fun extractPermissionsFromToken(commonProfile: CommonProfile): CommonProfile
    {
        val profile = commonProfile as JwtProfile
        val permissions = PermissionSet((profile.getAttribute("permissions") as String)
                .split(',')
                .filter { it.isNotEmpty() }
        )
        commonProfile.addAttribute(PERMISSIONS, permissions)
        return commonProfile
    }
}

class TokenActionAdapter(wrappedClients: List<MontaguSecurityClientWrapper>, repositoryFactory: RepositoryFactory)
    : MontaguHttpActionAdapter(repositoryFactory)
{
    private val unauthorizedResponse: List<ErrorInfo> = wrappedClients.map { it.authorizationError }

    private fun forbiddenResponse(missingPermissions: Set<String>) = MissingRequiredPermissionError(missingPermissions).problems

    override fun adapt(code: Int, context: SparkWebContext): Any? = when (code)
    {
        HttpConstants.UNAUTHORIZED ->
        {
            haltWithError(code, context, unauthorizedResponse)
        }
        HttpConstants.FORBIDDEN ->
        {
            val profile = DirectActionContext(context).userProfile
            val missingPermissions = profile!!.getAttributeOrDefault(MISSING_PERMISSIONS, mutableSetOf<String>())
            val response = forbiddenResponse(missingPermissions).toList()
            haltWithError(code, context, response)
        }
        else -> super.adapt(code, context)
    }
}