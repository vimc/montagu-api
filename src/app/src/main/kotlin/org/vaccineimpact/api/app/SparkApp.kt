package org.vaccineimpact.api.app

import org.vaccineimpact.api.app.controllers.AuthenticationController
import org.vaccineimpact.api.app.controllers.DiseaseController
import org.vaccineimpact.api.app.controllers.ModellingGroupController
import org.vaccineimpact.api.app.controllers.TouchstoneController
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.jooq.*
import org.vaccineimpact.api.security.WebTokenHelper
import spark.Spark as spk

fun main(args: Array<String>)
{
    val api = MontaguApi()
    api.run(api.makeRepositories())
}

class MontaguApi
{
    private val urlBase = "/v1"
    private val jsonTransform = Serializer::toResult
    private val tokenHelper = WebTokenHelper()

    fun makeRepositories(): Repositories
    {
        val simpleObjectsRepository = { JooqSimpleObjectsRepository() }
        val userRepository = { JooqUserRepository() }
        val touchstoneRepository = { JooqTouchstoneRepository() }
        val scenarioRepository = { JooqScenarioRepository() }
        val modellingGroupRepository = { JooqModellingGroupRepository(touchstoneRepository, scenarioRepository) }
        return Repositories(
                simpleObjectsRepository,
                userRepository,
                touchstoneRepository,
                scenarioRepository,
                modellingGroupRepository
        )
    }

    fun run(repositories: Repositories)
    {
        spk.port(8080)
        spk.redirect.get("/", urlBase)
        spk.before("*", ::addTrailingSlashes)
        ErrorHandler.setup()

        val controllers = listOf(
                AuthenticationController(tokenHelper, repositories.userRepository),
                DiseaseController(repositories.simpleObjectsRepository),
                TouchstoneController(repositories.touchstoneRepository),
                ModellingGroupController(repositories.modellingGroupRepository)
        )
        for (controller in controllers)
        {
            controller.mapEndpoints(urlBase)
        }

        spk.after("*", { _, res -> addDefaultResponseHeaders(res) })
    }
}
