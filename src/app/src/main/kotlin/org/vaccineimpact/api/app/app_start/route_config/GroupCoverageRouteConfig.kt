package org.vaccineimpact.api.app.app_start.route_config

import org.vaccineimpact.api.app.app_start.*
import org.vaccineimpact.api.app.controllers.GroupCoverageController
import org.vaccineimpact.api.app.controllers.OneTimeLinkController
import org.vaccineimpact.api.app.app_start.streamed

object GroupCoverageRouteConfig : RouteConfig
{
    private val baseUrl = "/modelling-groups/:group-id/responsibilities/:touchstone-version-id/:scenario-id"
    private val controller = GroupCoverageController::class

    private val groupScope = "modelling-group:<group-id>"
    val permissions = setOf(
            "*/scenarios.read",
            "$groupScope/responsibilities.read",
            "$groupScope/coverage.read"
    )

    override val endpoints: List<EndpointDefinition> = listOf(
            Endpoint("$baseUrl/coverage-sets/", controller, "getCoverageSets")
                    .json()
                    .secure(permissions),

            Endpoint("$baseUrl/coverage/", controller, "getCoverageDataAndMetadata")
                    // TODO: Return true multipart data and change the data type
                    .json().streamed()
                    .secure(permissions),

            Endpoint("$baseUrl/coverage/", controller, "getCoverageData")
                    .csv().streamed()
                    .secure(permissions),

            Endpoint("$baseUrl/coverage/get_onetime_link/",
                    OneTimeLinkController::class, "getTokenForCoverageData")
                    .json()
                    .secure(permissions))
}