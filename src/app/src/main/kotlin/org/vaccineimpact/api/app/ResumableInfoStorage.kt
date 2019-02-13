package org.vaccineimpact.api.app

import org.vaccineimpact.api.app.models.ResumableInfo
import java.util.HashMap

/**
 * by fanxu
 */
class ResumableInfoStorage//Single instance
private constructor()
{

    //resumableIdentifier --  ResumableInfo
    private val mMap = HashMap<String, ResumableInfo>()

    @Synchronized
    operator fun get(resumableChunkSize: Long, resumableTotalSize: Long,
                     resumableIdentifier: String, resumableFilename: String,
                     resumableRelativePath: String, resumableFilePath: String): ResumableInfo
    {

        var info: ResumableInfo? = mMap[resumableIdentifier]

        if (info == null)
        {
            info = ResumableInfo()

            info.resumableChunkSize = resumableChunkSize
            info.resumableTotalSize = resumableTotalSize
            info.resumableIdentifier = resumableIdentifier
            info.resumableFilename = resumableFilename
            info.resumableRelativePath = resumableRelativePath
            info.resumableFilePath = resumableFilePath

            mMap[resumableIdentifier] = info
        }
        return info
    }

    /**
     * ɾ��ResumableInfo
     * @param info
     */
    fun remove(info: ResumableInfo)
    {
        mMap.remove(info.resumableIdentifier)
    }

    companion object
    {
        val instance: ResumableInfoStorage = ResumableInfoStorage()
    }
}