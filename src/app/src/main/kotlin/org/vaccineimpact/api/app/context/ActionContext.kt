package org.vaccineimpact.api.app.context

import org.pac4j.core.profile.CommonProfile
import org.vaccineimpact.api.app.MultipartData
import org.vaccineimpact.api.app.MultipartDataMap
import org.vaccineimpact.api.app.Part
import org.vaccineimpact.api.app.ServletFileUploadWrapper
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import spark.Request
import java.io.OutputStream
import java.io.Reader
import kotlin.reflect.KClass

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

    fun queryParams(key: String): String?
    fun queryString(): String?
    fun params(): Map<String, String>
    fun params(key: String): String
    fun getPart(name: String, multipartData: MultipartData = ServletFileUploadWrapper()): UploadedFile
    fun getParts(multipartData: MultipartData = ServletFileUploadWrapper()): MultipartDataMap

    fun <T : Any> postData(klass: Class<T>): T
    fun <T : Any> csvData(klass: KClass<T>, from: RequestBodySource): Sequence<T>
    fun <T : Any> csvData(klass: KClass<T>, raw: Part): Sequence<T>

    fun addResponseHeader(key: String, value: String): Unit
    fun addAttachmentHeader(filename: String): Unit
    fun setResponseStatus(status: Int): Unit
    fun streamedResponse(contentType: String, work: (OutputStream) -> Unit): Unit

    fun hasPermission(requirement: ReifiedPermission): Boolean
    fun requirePermission(requirement: ReifiedPermission): Unit
    fun redirect(url: String)
}

inline fun <reified T : Any> ActionContext.postData() = this.postData(T::class.java)
inline fun <reified T : Any> ActionContext.csvData(from: RequestBodySource = RequestBodySource.Simple()) = this.csvData(T::class, from)
inline fun <reified T : Any> ActionContext.csvData(raw: Part) = this.csvData(T::class, raw)
