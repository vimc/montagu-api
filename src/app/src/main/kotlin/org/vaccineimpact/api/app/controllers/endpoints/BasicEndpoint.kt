package org.vaccineimpact.api.app.controllers.endpoints

import org.vaccineimpact.api.ContentTypes
import org.vaccineimpact.api.app.ActionContext
import org.vaccineimpact.api.app.DirectActionContext
import spark.Route
import spark.route.HttpMethod

// For endpoints that just need an ActionContext and nothing else
fun basicEndpoint(
        urlFragment: String,
        route: (ActionContext) -> Any,
        method: HttpMethod = HttpMethod.get,
        contentType: String = ContentTypes.json
): Endpoint<(ActionContext) -> Any>
{
    return Endpoint(
            urlFragment,
            route,
            ::basicWrapper,
            method,
            contentType
    )
}

private fun basicWrapper(route: (ActionContext) -> Any): Route
{
    return Route({ req, res ->
        DirectActionContext(req, res).use { route(it) }
    })
}