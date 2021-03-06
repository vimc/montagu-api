package org.vaccineimpact.api.app.app_start.route_config

import org.vaccineimpact.api.app.app_start.Endpoint
import org.vaccineimpact.api.app.app_start.EndpointDefinition
import org.vaccineimpact.api.app.app_start.json
import org.vaccineimpact.api.app.app_start.secure
import org.vaccineimpact.api.app.controllers.PasswordController
import spark.route.HttpMethod

object PasswordRouteConfig : RouteConfig
{
    private val controller = PasswordController::class
    private val urlBase = "/password"

    override val endpoints: List<EndpointDefinition> = listOf(
            Endpoint("$urlBase/set/", controller, "setPassword", method = HttpMethod.post)
                    .json()
                    .secure(),
            Endpoint("$urlBase/request-link/", controller, "requestResetPasswordLink", method = HttpMethod.post)
                    .json()
    )
}