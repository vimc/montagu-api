package org.vaccineimpact.api.app

import org.vaccineimpact.api.app.models.ResumableInfo
import java.util.HashMap


class ResumableInfoStorage
{

    private val memoryCache = HashMap<String, ResumableInfo>()

    operator fun get(uniqueIdentifier: String): ResumableInfo?
    {
       return memoryCache[uniqueIdentifier]
    }

    fun remove(info: ResumableInfo)
    {
        memoryCache.remove(info.uniqueIdentifier)
    }

    companion object
    {
        val instance: ResumableInfoStorage = ResumableInfoStorage()
    }
}