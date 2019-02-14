package org.vaccineimpact.api.app.models

import java.io.File
import java.util.HashSet

data class ResumableInfo(var chunkSize: Long,
                         var totalSize: Long,
                         var uniqueIdentifier: String,
                         var filename: String,
                         var relativePath: String,
                         var filePath: String
)
{
    val uploadedChunks = HashSet<Int>()

    fun uploadFinished(): Boolean
    {
        //check if upload finished
        val count = Math.ceil(totalSize.toDouble() / chunkSize.toDouble()).toInt()
        for (i in 1 until count)
        {
            if (!uploadedChunks.contains(i))
            {
                return false
            }
        }

        //Upload finished, change filename.
        val file = File(filePath)
        val newPath = file.absolutePath.substring(0, file.absolutePath.length - ".temp".length)
        file.renameTo(File(newPath))
        return true
    }
}