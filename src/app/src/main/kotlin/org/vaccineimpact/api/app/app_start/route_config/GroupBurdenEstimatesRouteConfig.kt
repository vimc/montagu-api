package org.vaccineimpact.api.app.app_start.route_config

import org.vaccineimpact.api.app.app_start.Endpoint
import org.vaccineimpact.api.app.app_start.csv
import org.vaccineimpact.api.app.app_start.json
import org.vaccineimpact.api.app.app_start.secure
import org.vaccineimpact.api.app.controllers.GroupBurdenEstimatesController
import spark.route.HttpMethod

object GroupBurdenEstimatesRouteConfig : RouteConfig
{
    private val baseUrl = "/modelling-groups/:group-id/responsibilities/:touchstone-version-id/:scenario-id/estimate-sets"
    private val baseEstimatesUrl = "/modelling-groups/:group-id/responsibilities/:touchstone-version-id/:scenario-id/estimates"
    private val controller = GroupBurdenEstimatesController::class

    private val groupScope = "modelling-group:<group-id>"
    private val readPermissions = setOf(
            "$groupScope/estimates.read",
            "$groupScope/responsibilities.read"
    )
    private val writePermissions = setOf(
            "$groupScope/estimates.write",
            "$groupScope/responsibilities.read"
    )

    override val endpoints = listOf(
            // Get and create sets
            Endpoint("$baseUrl/", controller, "getBurdenEstimates")
                    .json()
                    .secure(readPermissions),

            Endpoint("$baseUrl/", controller, "createBurdenEstimateSet", method = HttpMethod.post)
                    .json()
                    .secure(writePermissions),

            // Populate sets
            Endpoint("$baseUrl/:set-id/", controller, "populateBurdenEstimateSet", method = HttpMethod.post)
                    .json()
                    .secure(writePermissions),
            // Actions
            Endpoint("$baseUrl/:set-id/actions/clear/",
                    controller, "clearBurdenEstimateSet", method = HttpMethod.post)
                    .json()
                    .secure(writePermissions),

            Endpoint("$baseUrl/:set-id/actions/close/",
                    controller, "closeBurdenEstimateSet", method = HttpMethod.post)
                    .json()
                    .secure(writePermissions),

            Endpoint("$baseEstimatesUrl/deaths/",
                    controller, "getEstimatedDeathsForResponsibility")
                    .json()
                    .secure(readPermissions),

            Endpoint("$baseEstimatesUrl/aggregated-deaths/",
                    controller, "getAggregatedEstimatedDeathsForResponsibility")
                    .json()
                    .secure(readPermissions)
    )
}