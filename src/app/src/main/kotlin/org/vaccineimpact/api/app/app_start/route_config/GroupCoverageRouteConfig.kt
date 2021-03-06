
package org.vaccineimpact.api.app.app_start.route_config

import org.vaccineimpact.api.app.app_start.*
import org.vaccineimpact.api.app.app_start.streamed
import org.vaccineimpact.api.app.controllers.CoverageController

object GroupCoverageRouteConfig : RouteConfig
{
    private val baseUrl = "/modelling-groups/:group-id/responsibilities/:touchstone-version-id/:scenario-id"
    private val controller = CoverageController::class

    private val groupScope = "modelling-group:<group-id>"
    val permissions = setOf(
            "*/scenarios.read",
            "$groupScope/responsibilities.read",
            "$groupScope/coverage.read"
    )

    override val endpoints: List<EndpointDefinition> = listOf(
            Endpoint("$baseUrl/coverage-sets/", controller, "getCoverageSetsForGroup")
                    .json()
                    .secure(permissions),

            Endpoint("$baseUrl/coverage/", controller, "getCoverageDataAndMetadataForGroup")
                    // TODO: Return true multipart data and change the data type
                    .json().streamed()
                    .secure(permissions),

            Endpoint("$baseUrl/coverage/", controller, "getCoverageDataForGroup")
                    .csv().streamed()
                    .secure(permissions),

            Endpoint("$baseUrl/coverage/csv/", controller, "getCoverageDataForGroup")
                    .streamed()
                    .secure(permissions))
}