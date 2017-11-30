package org.vaccineimpact.api.app.app_start

import org.slf4j.LoggerFactory
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.context.DirectActionContext
import org.vaccineimpact.api.app.app_start.route_config.RouteConfig
import org.vaccineimpact.api.app.errors.UnsupportedValueException
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.models.AuthenticationResponse
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.serialization.Serializer
import spark.Route
import spark.Spark
import spark.route.HttpMethod
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.functions
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.jvm.internal.impl.load.java.structure.JavaClass
import kotlin.reflect.jvm.javaType

class Router(val config: RouteConfig,
             val serializer: Serializer,
             val webTokenHelper: WebTokenHelper,
             val repositoryFactory: RepositoryFactory)
{
    private val logger = LoggerFactory.getLogger(Router::class.java)

    companion object
    {
        val urls: MutableList<String> = mutableListOf()
    }

    fun mapEndpoints(urlBase: String): List<String>
    {
        urls.addAll(config.endpoints.map {
            mapEndpoint(it, urlBase)
        })
        return urls
    }

    private fun transform(x: Any) = when (x)
    {
        is AuthenticationResponse -> serializer.gson.toJson(x)!!
        else -> serializer.toResult(x)
    }

    private fun mapEndpoint(
            endpoint: EndpointDefinition,
            urlBase: String): String
    {
        val fullUrl = urlBase + endpoint.urlFragment
        val route = getWrappedRoute(endpoint)::handle
        val contentType = endpoint.contentType

        logger.info("Mapping $fullUrl to ${endpoint.actionName} on Controller ${endpoint.controllerName}")
        when (endpoint.method)
        {
            HttpMethod.get -> Spark.get(fullUrl, contentType, route, this::transform)
            HttpMethod.post -> Spark.post(fullUrl, contentType, route, this::transform)
            HttpMethod.put -> Spark.put(fullUrl, contentType, route, this::transform)
            HttpMethod.patch -> Spark.patch(fullUrl, contentType, route, this::transform)
            HttpMethod.delete -> Spark.delete(fullUrl, contentType, route, this::transform)
            else -> throw UnsupportedValueException(endpoint.method)
        }

        endpoint.additionalSetup(fullUrl, webTokenHelper, repositoryFactory)
        return fullUrl
    }

    private fun getWrappedRoute(endpoint: EndpointDefinition): Route
    {
        return Route({ req, res ->
            repositoryFactory.inTransaction { repos ->
                invokeControllerAction(endpoint, DirectActionContext(req, res), repos)
            }
        })
    }

    private fun invokeControllerAction(
            endpoint: EndpointDefinition,
            context: ActionContext,
            repositories: Repositories
    ): Any?
    {
        val controllerName = endpoint.controllerName
        val actionName = endpoint.actionName


        val controllerType = Class.forName("org.vaccineimpact.api.app.controllers.${controllerName}Controller").kotlin
        val controller = instantiateController(controllerType, context, repositories)
        val action = controllerType.functions.single { it.name == actionName }

        return action.call(controller)
    }

    private fun instantiateController(
            controllerType: KClass<out Any>,
            context: ActionContext,
            repositories: Repositories
    ): Controller
    {
        val c1 = getConstructor(controllerType, ActionContext::class, Repositories::class, WebTokenHelper::class)
        val c2 = getConstructor(controllerType, ActionContext::class, Repositories::class)

        val controller = c1?.call(context, repositories, webTokenHelper)
                ?: c2?.call(context, repositories)
                ?: throw Exception("Unable to find a useable constructor for controller " + controllerType.simpleName)
        return controller as Controller
    }

    private fun getConstructor(controllerType: KClass<out Any>, vararg parameterTypes: KClass<*>): KFunction<*>?
    {
        return controllerType.constructors.firstOrNull { constructorMatches(it, *parameterTypes) }
    }

    private fun constructorMatches(constructor: KFunction<*>, vararg parameterTypes: KClass<*>): Boolean
    {
        return constructor.parameters.map { it.javaClass } ==
                parameterTypes.map { it.java }
    }

}