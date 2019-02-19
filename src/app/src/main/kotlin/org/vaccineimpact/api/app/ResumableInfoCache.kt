package org.vaccineimpact.api.app

import org.vaccineimpact.api.app.models.ResumableInfo
import java.util.HashMap
import java.util.concurrent.TimeUnit

interface Cache<T> {
    operator fun get(uniqueIdentifier: String): T?
    fun put(item: T)
    fun remove(item: T)
}

object ResumableInfoCache: Cache<ResumableInfo>
{
    private val memoryCache = HashMap<String, ResumableInfo>()
    private var lastFlushTime = System.nanoTime()
    private val flushInterval: Long = TimeUnit.HOURS.toMillis(1)

    override operator fun get(uniqueIdentifier: String): ResumableInfo?
    {
        recycle()
        return memoryCache[uniqueIdentifier]
    }

    override fun put(item: ResumableInfo)
    {
        recycle()
        memoryCache[item.uniqueIdentifier] = item
    }

    override fun remove(item: ResumableInfo)
    {
        recycle()
        memoryCache.remove(item.uniqueIdentifier)
    }

    private fun recycle() {
        val shouldRecycle = System.nanoTime() - lastFlushTime >= TimeUnit.MILLISECONDS.toNanos(flushInterval)
        if (shouldRecycle)
        {
            memoryCache.clear()
        }
    }
}