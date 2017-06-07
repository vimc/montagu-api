package org.vaccineimpact.api.blackboxTests.helpers

import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.*

object CertificateHelper
{
    fun disableCertificateValidation()
    {
        val context = SSLContext.getInstance("TLS")
        context.init(null, arrayOf(TrustAll()), SecureRandom())
        HttpsURLConnection.setDefaultSSLSocketFactory(context.socketFactory)
        HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
    }

    class TrustAll : X509TrustManager
    {
        override fun getAcceptedIssuers() = emptyArray<X509Certificate>()
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) { }
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) { }
    }
}