package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.app.ResumableInfoStorage
import org.vaccineimpact.api.app.app_start.Controller
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.models.ResumableInfo
import java.io.File
import java.io.RandomAccessFile
import javax.servlet.ServletException

class ResumableUploadController(context: ActionContext) : Controller(context)
{
    fun postChunk(): String
    {
        val request = context.request
        val resumableChunkNumber = context.queryParams("resumableChunkNumber")?.toInt()?: -1

        val info = getResumableInfo()
        val raf = RandomAccessFile(info.resumableFilePath, "rw")

        //Seek to position
        raf.seek((resumableChunkNumber - 1) * info.resumableChunkSize)

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


        //Mark as uploaded.
        info.uploadedChunks.add(ResumableInfo.ResumableChunkNumber(resumableChunkNumber))
        return if (info.checkIfUploadFinished())
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
        val resumableChunkNumber = context.queryParams("resumableChunkNumber")?.toInt()?: -1

        val info = getResumableInfo()

        if (info.uploadedChunks.contains(ResumableInfo.ResumableChunkNumber(resumableChunkNumber)))
        {
            return "OK"
        }
        else
        {
           throw UnknownObjectError(resumableChunkNumber, "chunk")
        }
    }

    @Throws(ServletException::class)
    private fun getResumableInfo(): ResumableInfo
    {
        val base_dir = UPLOAD_DIR

        val resumableChunkSize = context.queryParams("resumableChunkSize")?.toLong()?: -1
        val resumableTotalSize = context.queryParams("resumableTotalSize")?.toLong()?: -1
        val resumableIdentifier = context.queryParams("resumableIdentifier")!!
        val resumableFilename = context.queryParams("resumableFilename")!!
        val resumableRelativePath = context.queryParams("resumableRelativePath")!!
        //Here we add a ".temp" to every upload file to indicate NON-FINISHED
        File(base_dir).mkdir()
        val resumableFilePath = File(base_dir, resumableFilename).absolutePath + ".temp"

        val storage = ResumableInfoStorage.instance

        val info = storage.get(resumableChunkSize, resumableTotalSize,
                resumableIdentifier, resumableFilename, resumableRelativePath, resumableFilePath)
        if (!info.valid())
        {
            storage.remove(info)
            throw ServletException("Invalid request params.")
        }
        return info
    }

    companion object
    {
        const val UPLOAD_DIR = "upload_dir"
    }
}