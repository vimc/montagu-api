package org.vaccineimpact.api.app

import org.vaccineimpact.api.app.models.ChunkedFile
import org.vaccineimpact.api.db.Config
import java.io.File
import java.io.InputStream
import java.io.RandomAccessFile

open class ChunkedFileManager
{
    init
    {
        File(UPLOAD_DIR).mkdir()
    }

    // Note: currentChunk is 1-indexed
    open fun writeChunk(inputStream: InputStream, contentLength: Int, metadata: ChunkedFile, currentChunk: Int)
    {
        val uploadPath = "$UPLOAD_DIR/${metadata.uniqueIdentifier}.temp"
        val raf = RandomAccessFile(uploadPath, "rw")

        // Seek to position
        raf.seek((currentChunk - 1) * metadata.chunkSize)

        // Write to file
        var readBytes: Long = 0
        val bytes = ByteArray(1024 * 100)
        while (readBytes < contentLength)
        {
            val r = inputStream.read(bytes)
            if (r < 0)
            {
                break
            }
            raf.write(bytes, 0, r)
            readBytes += r.toLong()
        }
        raf.close()
    }

    fun markFileAsComplete(chunkedFile: ChunkedFile)
    {
        if (chunkedFile.uploadFinished())
        {
            val uploadPath = "$UPLOAD_DIR/${chunkedFile.uniqueIdentifier}.temp"
            val tempFile = File(uploadPath)
            val newPath = uploadPath.substring(0, uploadPath.length - ".temp".length)
            tempFile.renameTo(File(newPath))
        }
    }

    companion object
    {
        val UPLOAD_DIR = Config["upload.dir"]
    }
}