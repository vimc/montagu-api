package org.vaccineimpact.api.app.models

import java.io.File
import java.util.concurrent.ConcurrentHashMap

data class ResumableInfo(val totalChunks: Int,
                         val chunkSize: Long,
                         val uniqueIdentifier: String,
                         var filePath: String
)
{
    constructor(totalChunks: Int, chunkSize: Long, uniqueIdentifier: String, file: File):
            this(totalChunks, chunkSize, uniqueIdentifier, "${file.absolutePath}.temp")

    val uploadedChunks = ConcurrentHashMap<Int, Boolean>()

    fun uploadFinished(): Boolean
    {
        for (i in 1..totalChunks)
        {
            if (!uploadedChunks.containsKey(i))
            {
                return false
            }
        }

        //Upload finished, change filename.
        val file = File(filePath)
        val newPath = filePath.substring(0, filePath.length - ".temp".length)
        filePath = newPath
        return file.renameTo(File(newPath))
    }

    fun cleanUp() {
        File(filePath).delete()
    }
}