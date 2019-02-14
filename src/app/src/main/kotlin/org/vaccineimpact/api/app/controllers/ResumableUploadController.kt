package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.app.ResumableInfoStorage
import org.vaccineimpact.api.app.app_start.Controller
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.models.ResumableInfo
import org.vaccineimpact.api.app.repositories.Repositories
import java.io.File
import java.io.RandomAccessFile

class ResumableUploadController(context: ActionContext, repositories: Repositories) : Controller(context)
{
    fun postChunk(): String
    {
        val resumableChunkNumber = context.queryParams("resumableChunkNumber")?.toInt()
                ?: throw BadRequest("Missing required query paramter: resumableChunkNumber")

        val info = getResumableInfo()
        val raf = RandomAccessFile(info.filePath, "rw")

        //Seek to position
        raf.seek((resumableChunkNumber - 1) * info.chunkSize)

        //Save to file
        val stream = context.request.raw().inputStream
        var readBytes: Long = 0
        val length = context.request.raw().contentLength
        val bytes = ByteArray(1024 * 100)
        while (readBytes < length)
        {
            val r = stream.read(bytes)
            if (r < 0)
            {
                break
            }
            raf.write(bytes, 0, r)
            readBytes += r.toLong()
        }
        raf.close()

        //Mark as uploaded
        info.uploadedChunks.add(resumableChunkNumber)
        return if (info.uploadFinished())
        { //Check if all chunks uploaded, and change filename
            ResumableInfoStorage.instance.remove(info)
            "Finished"
        }
        else
        {
            "Upload"
        }
    }

    fun getChunk(): String
    {
        val resumableChunkNumber = context.queryParams("resumableChunkNumber")?.toInt() ?: -1

        val info = getResumableInfo()

        if (info.uploadedChunks.contains(resumableChunkNumber))
        {
            return "OK"
        }
        else
        {
            throw UnknownObjectError(resumableChunkNumber, "chunk")
        }
    }

    private fun getResumableInfo(): ResumableInfo
    {
        val totalChunks = context.queryParams("resumableTotalChunks")?.toInt()
        val chunkSize = context.queryParams("resumableChunkSize")?.toLong()
        val uniqueIdentifier = context.queryParams("resumableIdentifier")
        val filename = context.queryParams("resumableFilename")

        if (totalChunks == null || chunkSize == null || uniqueIdentifier.isNullOrEmpty() || filename.isNullOrEmpty())
        {
            throw BadRequest("You must include all resumablejs query parameters")
        }

        val info = ResumableInfoStorage.instance[uniqueIdentifier!!]
        return if (info != null)
        {
            info
        }
        else
        {
            //Here we add a ".temp" to every upload file to indicate NON-FINISHED
            File(UPLOAD_DIR).mkdir()
            val filePath = File(UPLOAD_DIR, filename).absolutePath + ".temp"
            ResumableInfo(totalChunks, chunkSize, uniqueIdentifier, filePath)
        }

    }

    companion object
    {
        const val UPLOAD_DIR = "upload_dir"
    }
}