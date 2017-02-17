package uk.ac.imperial.vimc.demo.app

import com.google.gson.JsonSyntaxException
import org.slf4j.LoggerFactory
import spark.Request
import spark.Response
import uk.ac.imperial.vimc.demo.app.controllers.ModellingGroupController
import uk.ac.imperial.vimc.demo.app.controllers.ScenarioController
import uk.ac.imperial.vimc.demo.app.repositories.jooq.JooqModellingGroupRepository
import uk.ac.imperial.vimc.demo.app.repositories.jooq.JooqScenarioRepository
import java.net.URL
import spark.Spark as spk

fun main(args: Array<String>)
{
    val scenarioRepository = JooqScenarioRepository()
    val scenarioController = ScenarioController(scenarioRepository)

    val modellingGroupRepository = JooqModellingGroupRepository()
    val modellingGroupController = ModellingGroupController(modellingGroupRepository)

    DemoApp().run(scenarioController, modellingGroupController)
}

class DemoApp
{
    private val urlBase = "/v1"
    private val logger = LoggerFactory.getLogger(DemoApp::class.java)

    fun run(scenarios: ScenarioController, modellers: ModellingGroupController)
    {
        spk.port(8080)

        spk.exception(Exception::class.java) { e, req, res ->
            logger.error("An unhandled exception occurred", e)
            res.body("An error occurred: $e")
        }
        spk.exception(JsonSyntaxException::class.java) { e, req, res ->
            res.body("Error: Unable to parse supplied JSON: ${req.body()}")
        }

        spk.redirect.get("/", urlBase)
        spk.before("*", this::addTrailingSlashes)

        spk.get("$urlBase/", { req, res -> "root.json" }, this::fromFile)
        spk.get("$urlBase/scenarios/", scenarios::getAllScenarios, this::toJson)
        spk.get("$urlBase/scenarios/:scenario-id/", scenarios::getScenario, this::toJson)
        spk.get("$urlBase/scenarios/:scenario-id/countries/", scenarios::getCountriesInScenario, this::toJson)
        spk.get("$urlBase/modellers/", modellers::getAllModellingGroups, this::toJson)
        spk.get("$urlBase/modellers/", modellers::getAllModellingGroups, this::toJson)
        spk.get("$urlBase/modellers/:group-code/", modellers::getModellingGroup, this::toJson)
        spk.get("$urlBase/modellers/:group-code/models/", modellers::getModels, this::toJson)
        spk.get("$urlBase/modellers/:group-code/responsibilities/", modellers::getResponsibilities, this::toJson)
        spk.get("$urlBase/modellers/:group-code/estimates/", modellers::getAllEstimates, this::toJson)
        spk.get("$urlBase/modellers/:group-code/estimates/:estimate-id/", modellers::getEstimate, this::toJson)
        spk.post("$urlBase/modellers/:group-code/estimates/", modellers::createEstimate, this::toJson)

        spk.after("*", { req, res ->
            res.type("application/json")
            res.header("Content-Encoding", "gzip")
        })
    }

    fun toJson(x: Any): String = Serializer.gson.toJson(x)

    fun fromFile(fileName: Any): String
    {
        if (fileName is String)
        {
            val url: URL? = DemoApp::class.java.classLoader.getResource(fileName)
            if (url != null)
            {
                return url.readText()
            } else
            {
                return "Unknown file name '$fileName'"
            }
        } else
        {
            throw Exception("Unable to use $fileName as a file name")
        }
    }

    fun addTrailingSlashes(req: Request, res: Response)
    {
        if (!req.pathInfo().endsWith("/"))
        {
            var path = req.pathInfo() + "/"
            if (req.queryString() != null)
            {
                path += "/?" + req.queryString()
            }
            res.redirect(path)
        }
    }
}
