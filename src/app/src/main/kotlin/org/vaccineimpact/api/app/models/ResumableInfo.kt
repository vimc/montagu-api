package org.vaccineimpact.api.app.models

import java.io.File
import java.util.HashSet

class ResumableInfo
{
    var resumableChunkSize: Long = 0
    var resumableTotalSize: Long = 0
    var resumableIdentifier: String? = null
    var resumableFilename: String? = null
    var resumableRelativePath: String? = null

    //Chunks uploaded
    var uploadedChunks = HashSet<ResumableChunkNumber>()

    var resumableFilePath: String? = null

    class ResumableChunkNumber(var number: Int)
    {

        override fun equals(other: Any?): Boolean
        {
            return if (other is ResumableChunkNumber)
                other.number == this.number
            else
                false
        }

        override fun hashCode(): Int
        {
            return number
        }
    }

    fun valid(): Boolean
    {
        return !(resumableChunkSize < 0 || resumableTotalSize < 0
                || resumableIdentifier.isNullOrEmpty()
                || resumableFilename.isNullOrEmpty()
                || resumableRelativePath.isNullOrEmpty())
    }

    fun checkIfUploadFinished(): Boolean
    {
        //check if upload finished
        val count = Math.ceil(resumableTotalSize.toDouble() / resumableChunkSize.toDouble()).toInt()
        for (i in 1 until count)
        {
            if (!uploadedChunks.contains(ResumableChunkNumber(i)))
            {
                return false
            }
        }

        //Upload finished, change filename.
        val file = File(resumableFilePath!!)
        val newPath = file.absolutePath.substring(0, file.absolutePath.length - ".temp".length)
        file.renameTo(File(newPath))
        return true
    }
}