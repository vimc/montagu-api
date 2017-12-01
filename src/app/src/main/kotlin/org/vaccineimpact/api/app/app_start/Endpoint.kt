package org.vaccineimpact.api.app.app_start

import org.vaccineimpact.api.app.DefaultHeadersFilter
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.app.security.MontaguAuthorizer
import org.vaccineimpact.api.app.security.PermissionRequirement
import org.vaccineimpact.api.app.security.TokenVerifyingConfigFactory
import org.vaccineimpact.api.db.Config
import org.vaccineimpact.api.models.helpers.ContentTypes
import org.vaccineimpact.api.security.WebTokenHelper
import spark.Spark
import spark.route.HttpMethod

data class Endpoint(
        override val urlFragment: String,
        override val controllerName: String,
        override val actionName: String,
        override val contentType: String = ContentTypes.json,
        override val method: HttpMethod = HttpMethod.get,
        override val postProcess: ResultProcessor = ::passThrough,
        override val requiredPermissions: List<PermissionRequirement> = listOf()

) : EndpointDefinition
{
    init
    {
        if (!urlFragment.endsWith("/"))
        {
            throw Exception("All endpoint definitions must end with a forward slash: $urlFragment")
        }
    }

    override fun additionalSetup(url: String, webTokenHelper: WebTokenHelper, repositoryFactory: RepositoryFactory)
    {
        if (requiredPermissions.any())
        {
            addSecurityFilter(url, webTokenHelper, repositoryFactory)
        }
        Spark.after(url, contentType, DefaultHeadersFilter("$contentType; charset=utf-8", method))
    }

    private fun addSecurityFilter(url: String, webTokenHelper: WebTokenHelper, repositoryFactory: RepositoryFactory)
    {
        if (Config.authEnabled)
        {
            val configFactory = TokenVerifyingConfigFactory(webTokenHelper,
                    requiredPermissions.toSet(), repositoryFactory)

            val config = configFactory.build()

            Spark.before(url, org.pac4j.sparkjava.SecurityFilter(
                    config,
                    configFactory.allClients(),
                    MontaguAuthorizer::class.java.simpleName,
                    "method:$method"
            ))
        }
    }

}

fun Endpoint.secure(permissions: Set<String> = setOf()): Endpoint
{
    val allPermissions = (permissions + "*/can-login").map {
        PermissionRequirement.parse(it)
    }
    return this.copy(requiredPermissions = allPermissions)
}

fun Endpoint.json(): Endpoint
{
    return this.copy(contentType = ContentTypes.json)
}
fun Endpoint.csv(): Endpoint
{
    return this.copy(contentType = ContentTypes.csv)
}

private fun passThrough(x: Any?, context: ActionContext): Any? = x
