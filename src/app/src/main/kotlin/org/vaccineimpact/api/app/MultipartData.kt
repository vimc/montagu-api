package org.vaccineimpact.api.app

import org.apache.commons.fileupload.FileItemStream
import org.apache.commons.fileupload.servlet.ServletFileUpload
import org.vaccineimpact.api.app.context.RequestBodySource
import org.vaccineimpact.api.app.errors.MissingRequiredMultipartParameterError
import javax.servlet.http.HttpServletRequest

interface MultipartData
{
    fun isMultipartContent(request: HttpServletRequest): Boolean
    fun parts(request: HttpServletRequest): Sequence<FileItemStream>
}

// A simple wrapper to enable us to mock things out in unit tests
class ServletFileUploadWrapper : MultipartData
{
    override fun isMultipartContent(request: HttpServletRequest)
            = ServletFileUpload.isMultipartContent(request)

    override fun parts(request: HttpServletRequest): Sequence<FileItemStream>
    {
        val iterator = ServletFileUpload().getItemIterator(request)
        return generateSequence {
            if (iterator.hasNext()) iterator.next() else null
        }
    }
}

class MultipartDataMap(val map: Map<String, String>) : Map<String, String> by map
{
    constructor(vararg pairs: Pair<String, String>)
            : this(mapOf(*pairs))

    fun getPart(fieldName: String): String
    {
        return map[fieldName]
                ?: throw MissingRequiredMultipartParameterError(fieldName)
    }

    fun getPartAsRequestBodySource(fieldName: String)
            = RequestBodySource.InMemory(getPart(fieldName))
}

fun FileItemStream.contents() = this.openStream().bufferedReader().readText()