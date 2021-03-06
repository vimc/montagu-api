package org.vaccineimpact.api.app.context

import org.pac4j.core.profile.CommonProfile
import org.vaccineimpact.api.app.requests.MultipartData
import org.vaccineimpact.api.app.requests.MultipartDataMap
import org.vaccineimpact.api.app.requests.ServletFileUploadWrapper
import org.vaccineimpact.api.db.Config
import org.vaccineimpact.api.db.ConfigWrapper
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.api.security.CookieName
import spark.Request
import java.io.InputStream
import java.io.OutputStream
import java.io.Reader

interface ActionContext
{
    val request: Request

    val permissions: PermissionSet
    val userProfile: CommonProfile?
    /** If the user logged in with a token this will be their username
     *  Otherwise it is null
     */
    val username: String?

    val redirectUrl: String?

    fun contentType(): String
    fun contentLength(): Int

    fun queryParams(key: String): String?
    fun queryString(): String?
    fun params(): Map<String, String>
    fun params(key: String): String
    fun getPart(name: String, multipartData: MultipartData = ServletFileUploadWrapper()): InputStream
    fun getParts(multipartData: MultipartData = ServletFileUploadWrapper()): MultipartDataMap

    fun requestReader(): Reader
    fun getInputStream(): InputStream
    fun <T : Any> postData(klass: Class<T>): T

    fun addResponseHeader(key: String, value: String): Unit
    fun addAttachmentHeader(filename: String): Unit
    fun setResponseStatus(status: Int): Unit
    fun streamedResponse(contentType: String, work: (OutputStream) -> Unit): Unit
    fun setCookie(name: CookieName, value: String, config: ConfigWrapper = Config)

    fun hasPermission(requirement: ReifiedPermission): Boolean
    fun requirePermission(requirement: ReifiedPermission): Unit
    fun redirect(url: String)

    fun authenticationToken(): String?
}

inline fun <reified T : Any> ActionContext.postData() = this.postData(T::class.java)
