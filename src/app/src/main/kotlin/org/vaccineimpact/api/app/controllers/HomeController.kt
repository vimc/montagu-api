package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.controllers.endpoints.basicEndpoint
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.db.Config

class HomeController(
        val otherEndpoints: List<String>,
        val context: ControllerContext
) : AbstractController(context)
{
    override val urlComponent = ""
    override fun endpoints(repos: RepositoryFactory) = listOf(
            basicEndpoint("/", this::index)
    )

    @Suppress("UNUSED_PARAMETER")
    fun index(context: ActionContext) = Index("montagu", Config["app.version"], otherEndpoints)

    data class Index(val name: String, val version: String, val endpoints: List<String>)
}