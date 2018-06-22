package org.vaccineimpact.api.app.app_start

import org.slf4j.LoggerFactory
import org.vaccineimpact.api.app.ErrorHandler
import org.vaccineimpact.api.app.NotFoundHandler
import org.vaccineimpact.api.app.RequestLogger
import org.vaccineimpact.api.app.addTrailingSlashes
import org.vaccineimpact.api.app.app_start.route_config.MontaguRouteConfig
import org.vaccineimpact.api.app.repositories.RepositoryFactory
import org.vaccineimpact.api.db.Config
import org.vaccineimpact.api.security.CompressedWebTokenHelper
import org.vaccineimpact.api.security.KeyHelper
import org.vaccineimpact.api.security.WebTokenHelper
import org.vaccineimpact.api.serialization.MontaguSerializer
import java.io.File
import java.net.BindException
import java.net.ServerSocket
import kotlin.system.exitProcess
import spark.Spark as spk

class MontaguApi
{
    private val urlBase = "/v1"
    private val tokenHelper = CompressedWebTokenHelper(KeyHelper.keyPair)

    private val logger = LoggerFactory.getLogger(MontaguApi::class.java)

    fun run(repositoryFactory: RepositoryFactory)
    {
        setupPort()

        spk.redirect.get("/", urlBase)

        spk.before("*", ::addTrailingSlashes)
        spk.before("*", AllowedOriginsFilter(Config.getBool("allow.localhost")))

        spk.options("*", { _, res ->
            res.header("Access-Control-Allow-Headers", "Authorization")
            res.header("Access-Control-Allow-Credentials", "true")
        })

        RequestLogger.setup(repositoryFactory)
        NotFoundHandler().setup()
        ErrorHandler.setup()

        val router = Router(MontaguRouteConfig, MontaguSerializer.instance, tokenHelper, repositoryFactory)
        router.mapEndpoints(urlBase)

        if (!Config.authEnabled)
        {
            logger.warn("WARNING: AUTHENTICATION IS DISABLED")
        }
    }

    private fun setupPort()
    {
        val port = if (Config.authEnabled)
        {
            Config.getInt("app.port")
        }
        else
        {
            8888
        }
        var attempts = 5
        spk.port(port)

        while (!isPortAvailable(port) && attempts > 0)
        {
            logger.info("Waiting for port $port to be available, $attempts attempts remaining")
            Thread.sleep(2000)
            attempts--
        }
        if (attempts == 0)
        {
            logger.error("Unable to bind to port $port - it is already in use.")
            exitProcess(-1)
        }
    }

    private fun isPortAvailable(port: Int): Boolean
    {
        try
        {
            ServerSocket(port).use {}
            return true
        }
        catch (e: BindException)
        {
            return false
        }
    }
}