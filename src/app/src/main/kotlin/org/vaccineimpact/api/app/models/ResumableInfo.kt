package org.vaccineimpact.api.app.models

import java.io.File
import java.util.HashSet

data class ResumableInfo(val totalChunks: Int,
                         val chunkSize: Long,
                         val uniqueIdentifier: String,
                         var filePath: String
)
{
    val uploadedChunks = HashSet<Int>()

    fun uploadFinished(): Boolean
    {
        for (i in 1..totalChunks)
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
        filePath = newPath
        return true
    }
}