package org.vaccineimpact.api.app.controllers.helpers

import org.vaccineimpact.api.app.context.ActionContext

// Everything needed to precisely specify one responsibility
data class ResponsibilityPath(val groupId: String, val touchstoneVersionId: String, val scenarioId: String)
{
    constructor(context: ActionContext)
            : this(context.params(":group-id"), context.params(":touchstone-version-id"), context.params(":scenario-id"))

    constructor(claims: Map<String, Any>)
            : this(claims["group"].toString(), claims["touchstoneVersionId"].toString(), claims["scenarioId"].toString())
}


// Everything needed to precisely specify one set of expectations
data class ExpectationPath(val groupId: String, val touchstoneVersionId: String, val expectationId: Int)
{
    constructor(context: ActionContext)
            : this(context.params(":group-id"), context.params(":touchstone-version-id"),
            context.params(":expectation-id").toInt())
}