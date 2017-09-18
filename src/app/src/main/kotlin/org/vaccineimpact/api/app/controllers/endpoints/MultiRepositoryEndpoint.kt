package org.vaccineimpact.api.app.controllers.endpoints

import org.vaccineimpact.api.ContentTypes
import org.vaccineimpact.api.app.ActionContext
import org.vaccineimpact.api.app.DirectActionContext
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import spark.Route
import spark.route.HttpMethod

// This endpoint expects the handler to take two arguments: An ActionContext and
// a the Repositories collection. From there, the action is expected to instantiate
// and cleanup any repositories it needs.
fun multiRepoEndpoint(
        urlFragment: String,
        route: (ActionContext, Repositories) -> Any,
        method: HttpMethod = HttpMethod.get,
        contentType: String = ContentTypes.json
): Endpoint<(ActionContext, Repositories) -> Any>
{
    return Endpoint(
            urlFragment,
            route,
            { wrapRoute(it) },
            method,
            contentType
    )
}

private fun wrapRoute(route: (ActionContext, Repositories) -> Any): Route
{
    val repositoryFactory = RepositoryFactory()
    return Route({ req, res ->
        repositoryFactory.inTransaction { repos ->
            route(DirectActionContext(req, res), repos)
        }
    })
}