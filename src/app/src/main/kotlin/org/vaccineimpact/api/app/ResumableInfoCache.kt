package org.vaccineimpact.api.app

import org.vaccineimpact.api.app.models.ResumableInfo
import java.util.HashMap

interface Cache<T> {
    operator fun get(uniqueIdentifier: String): T?
    fun put(item: T)
    fun remove(item: T)
}

object ResumableInfoCache: Cache<ResumableInfo>
{
    private val memoryCache = HashMap<String, ResumableInfo>()

    override operator fun get(uniqueIdentifier: String): ResumableInfo?
    {
        return memoryCache[uniqueIdentifier]
    }

    override fun put(item: ResumableInfo)
    {
        memoryCache[item.uniqueIdentifier] = item
    }

    override fun remove(item: ResumableInfo)
    {
        memoryCache.remove(item.uniqueIdentifier)
    }

}