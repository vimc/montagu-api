package org.vaccineimpact.api.app.app_start

import org.slf4j.LoggerFactory
import org.vaccineimpact.api.app.app_start.route_config.RouteConfig
import org.vaccineimpact.api.app.consumeRemainder
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.context.DirectActionContext
import org.vaccineimpact.api.app.errors.MontaguError
import org.vaccineimpact.api.app.errors.UnsupportedValueException
import org.vaccineimpact.api.app.models.PublicEndpointDescription
import org.vaccineimpact.api.app.repositories.Repositories
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.models.AuthenticationResponse
import org.vaccineimpact.api.models.Result
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.serialization.Serializer
import org.vaccineimpact.api.serialization.StreamSerializable
import spark.Route
import spark.Spark
import spark.route.HttpMethod
import java.lang.reflect.InvocationTargetException

class Router(val config: RouteConfig,
             val serializer: Serializer,
             val webTokenHelper: WebTokenHelper,
             val repositoryFactory: RepositoryFactory)
{
    private val logger = LoggerFactory.getLogger(Router::class.java)

    companion object
    {
        val urls: MutableList<PublicEndpointDescription> = mutableListOf()
    }

    fun mapEndpoints(urlBase: String)
    {
        urls.addAll(config.endpoints
                .sortedBy { it.urlFragment }
                .map { mapEndpoint(it, urlBase) }
        )
    }

    private fun transform(x: Any) = when (x)
    {
        is AuthenticationResponse -> serializer.gson.toJson(x)!!
        is StreamSerializable<*> -> throw Exception("Streamable content is not expected here")
        // in some cases we may wish to build up the result object ourselves, .e.g to return a failure status
        // without throwing an exception
        else -> if (x is Result) serializer.toJson(x) else serializer.toResult(x)
    }

    private fun mapEndpoint(
            endpoint: EndpointDefinition,
            urlBase: String): PublicEndpointDescription
    {
        val fullUrl = urlBase + endpoint.urlFragment
        val route = getWrappedRoute(endpoint)::handle
        val contentType = endpoint.contentType

        logger.info("Mapping $fullUrl to ${endpoint.actionName} on ${endpoint.controller.simpleName}")

        if (contentType.contains("json"))
        {
            when (endpoint.method)
            {
                HttpMethod.get -> Spark.get(fullUrl, contentType, route, this::transform)
                HttpMethod.post -> Spark.post(fullUrl, contentType, route, this::transform)
                HttpMethod.put -> Spark.put(fullUrl, contentType, route, this::transform)
                HttpMethod.patch -> Spark.patch(fullUrl, contentType, route, this::transform)
                HttpMethod.delete -> Spark.delete(fullUrl, contentType, route, this::transform)
                else -> throw UnsupportedValueException(endpoint.method)
            }
        }
        else
        {
            when (endpoint.method)
            {
                HttpMethod.get -> Spark.get(fullUrl, contentType, route)
                else -> throw UnsupportedValueException(endpoint.method)
            }
        }

        endpoint.additionalSetup(fullUrl, webTokenHelper, repositoryFactory)
        return PublicEndpointDescription(
                fullUrl,
                endpoint.method,
                contentType
        )
    }

    fun getWrappedRoute(endpoint: EndpointDefinition): Route
    {
        return Route({ req, res ->
            try
            {
                repositoryFactory.inTransaction { repos ->
                    invokeControllerAction(endpoint, DirectActionContext(req, res), repos)
                }
            }
            finally
            {
                req.consumeRemainder()
            }
        })
    }

    fun invokeControllerAction(endpoint: EndpointDefinition, context: ActionContext,
                               repositories: Repositories): Any?
    {
        val actionName = endpoint.actionName
        val controllerType = endpoint.controller.java
        val controller = instantiateController(controllerType, context, repositories)
        val action = controllerType.getMethod(actionName)

        val result = try
        {
            action.invoke(controller)
        }
        catch (e: InvocationTargetException)
        {
            logger.warn("Exception was thrown whilst using reflection to invoke " +
                    "$controllerType.$actionName, see below for details")
            throw e.targetException
        }
        return endpoint.postProcess(result, context)
    }

    private fun instantiateController(controllerType: Class<*>, context: ActionContext, repositories: Repositories): Controller
    {
        val constructor = try
        {
            controllerType.getConstructor(ActionContext::class.java, Repositories::class.java)
        }
        catch (e: NoSuchMethodException)
        {
            throw NoSuchMethodException("There is a problem with $controllerType. " +
                    "All new-style controllers must have a secondary constructor that takes" +
                    "an ActionContext and a Repositories instance")
        }
        return constructor.newInstance(context, repositories) as Controller
    }
}