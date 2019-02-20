package org.vaccineimpact.api.app

import org.vaccineimpact.api.app.models.ResumableInfo
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

interface Cache<T> {
    operator fun get(uniqueIdentifier: String): T?
    fun put(item: T)
    fun remove(uniqueIdentifier: String)
}

class ResumableInfoCache(val flushInterval: Long = TimeUnit.HOURS.toMillis(1)): Cache<ResumableInfo>
{
    private val memoryCache = ConcurrentHashMap<String, ResumableInfo>()
    private val lastAccessedMap = ConcurrentHashMap<String, Long>()

    override operator fun get(uniqueIdentifier: String): ResumableInfo?
    {
        lastAccessedMap[uniqueIdentifier] = System.currentTimeMillis()
        recycle()
        return memoryCache[uniqueIdentifier]
    }

    override fun put(item: ResumableInfo)
    {
        lastAccessedMap[item.uniqueIdentifier] = System.currentTimeMillis()
        recycle()
        memoryCache[item.uniqueIdentifier] = item
    }

    override fun remove(uniqueIdentifier: String)
    {
        memoryCache[uniqueIdentifier]?.cleanUp()
        lastAccessedMap.remove(uniqueIdentifier)
        memoryCache.remove(uniqueIdentifier)
    }

    private fun recycle() {
        lastAccessedMap.filter {
            it.value < System.currentTimeMillis() - flushInterval
        }.map {
           remove(it.key)
        }
    }

    companion object
    {
        val instance = ResumableInfoCache()
    }
}