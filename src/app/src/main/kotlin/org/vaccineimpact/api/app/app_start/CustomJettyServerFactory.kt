package org.vaccineimpact.api.app.app_start

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.util.thread.ThreadPool
import spark.embeddedserver.jetty.JettyServerFactory
import org.eclipse.jetty.util.thread.QueuedThreadPool
import org.bouncycastle.crypto.tls.ConnectionEnd.server
import spark.Spark.setPort
import org.eclipse.jetty.server.HttpConnectionFactory
import org.eclipse.jetty.http.HttpVersion
import org.eclipse.jetty.server.SslConnectionFactory
import org.eclipse.jetty.server.ServerConnector



class CustomJettyServerFactory : JettyServerFactory
{
    override fun create(maxThreads: Int, minThreads: Int, threadTimeoutMillis: Int): Server
    {
        val server = if (maxThreads > 0)
        {
            val min = if (minThreads > 0) minThreads else 8
            val idleTimeout = if (threadTimeoutMillis > 0) threadTimeoutMillis else 60000

            Server(QueuedThreadPool(maxThreads, min, idleTimeout))
        }
        else
        {
            Server()
        }
        val sslConnector = ServerConnector(server,
                SslConnectionFactory(sslContextFactory, HttpVersion.HTTP_1_1.asString()),
                HttpConnectionFactory(https_config))
        sslConnector.port = 8443
        server.addConnector(sslConnector)
        return server
    }

    override fun create(threadPool: ThreadPool?): Server
    {
        return if (threadPool != null) Server(threadPool) else Server()
    }
}