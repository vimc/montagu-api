package org.vaccineimpact.api.app.clients

import com.google.gson.JsonSyntaxException
import com.google.gson.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.vaccineimpact.api.app.errors.OrderlyWebError
import org.vaccineimpact.api.db.Config
import org.vaccineimpact.api.db.ConfigWrapper
import java.security.cert.X509Certificate
import javax.net.ssl.*

interface OrderlyWebAPIClient
{
    @Throws(OrderlyWebError::class)
    fun addUser(email: String, username: String, displayName: String)
}

data class OrderlyWebLoginResult(val access_token: String, val token_type: String, val expires_in: Int)
data class OrderlyWebUserDetails(val email: String, val username: String, val displayName: String, val source: String)

abstract class OkHttpOrderlyWebAPIClient(private val montaguToken: String,
                                         private val config: ConfigWrapper = Config): OrderlyWebAPIClient {

    companion object
    {
        fun create(montaguToken: String): OkHttpOrderlyWebAPIClient
        {
            return if (Config.getBool("allow.localhost"))
                LocalOkHttpMontaguApiClient(montaguToken)
            else
                RemoteHttpMontaguApiClient(montaguToken)
        }
    }

    private val baseUrl = config["orderlyweb.api.url"];
    private var orderlyWebToken: String? = null;

    private val gson = GsonBuilder().create()

    override fun addUser(email: String, username: String, displayName: String) {
        val orderlyWebToken = getOrderlyWebToken()
        val userDetails = OrderlyWebUserDetails(email, username, displayName, "Montagu")
        val postBody = gson.toJson(userDetails)
        val postResponse = post("$baseUrl/user/add", mapOf("Authorization" to "Bearer $orderlyWebToken"), postBody)
        val code = postResponse.code
        if (code != 200) {
            throw OrderlyWebError("Error adding user to OrderlyWeb. Code: $code")
        }
    }

    private fun getOrderlyWebToken(): String
    {
        if (orderlyWebToken == null) {
            val requestHeaders = mapOf(
                    "Authorization" to "token $montaguToken",
                    "Accept" to "application/json"
            )

            post("$baseUrl/login", requestHeaders, "")
                    .use { response ->
                        val body = response.body!!.string()

                        val loginResult = parseLoginResult(body)
                        orderlyWebToken = loginResult.access_token;
                    }
        }
        return orderlyWebToken!!
    }

    private fun post(url: String, headersMap: Map<String, String>, body: String): Response
    {
        val client = getHttpClient();
        val headers = buildHeaders(headersMap);
        val requestBody = body.toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
                .url(url)
                .headers(headers)
                .post(requestBody)
                .build()
        return client.newCall(request).execute()
    }

    private fun buildHeaders(headersMap: Map<String, String>): Headers
    {
        val headersBuilder = Headers.Builder()
        headersMap.forEach { k, v ->  headersBuilder.add(k, v)}
        return headersBuilder.build()
    }

    private fun parseLoginResult(jsonString: String): OrderlyWebLoginResult
    {
        return try
        {
            gson.fromJson<OrderlyWebLoginResult>(jsonString, OrderlyWebLoginResult::class.java);
        }
        catch(e: JsonSyntaxException)
        {
            throw OrderlyWebError("Failed to parse text as JSON.\nText was: $jsonString\n\n$e")
        }
    }

    protected abstract fun getHttpClient(): OkHttpClient
}

class LocalOkHttpMontaguApiClient(montaguToken: String): OkHttpOrderlyWebAPIClient(montaguToken)
{
    override fun getHttpClient(): OkHttpClient
    {
        //Stolen from https://stackoverflow.com/questions/25509296/trusting-all-certificates-with-okhttp
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
            }

            override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
        })

        val allHostnameVerifier = object : HostnameVerifier{
            override fun verify(var1: String, var2: SSLSession): Boolean
            { return true }
        }

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        return OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier(allHostnameVerifier)
                .build()
    }
}

class RemoteHttpMontaguApiClient(montaguToken: String): OkHttpOrderlyWebAPIClient(montaguToken)
{
    override fun getHttpClient(): OkHttpClient
    {
        return OkHttpClient()
    }
}
