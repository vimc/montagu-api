package org.vaccineimpact.api.tests

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.app.ResumableInfoCache
import org.vaccineimpact.api.app.models.ResumableInfo
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

class ResumableInfoCacheTests : MontaguTests()
{
    @Test
    fun `can put and read object from cache multiple times`()
    {
        val sut = ResumableInfoCache()
        val testInfo = ResumableInfo(10, 100, "uid", "file.csv")
        sut.put(testInfo)

        var result = sut["uid"]
        assertThat(result).isSameAs(testInfo)

        result = sut["uid"]
        assertThat(result).isSameAs(testInfo)
    }

    @Test
    fun `can remove object from cache`()
    {
        val sut = ResumableInfoCache()
        val testInfo = ResumableInfo(10, 100, "uid", "file.csv")
        sut.put(testInfo)

        var result = sut["uid"]
        assertThat(result).isSameAs(testInfo)

        sut.remove(testInfo)

        result = sut["uid"]
        assertThat(result).isNull()
    }

    @Test
    fun `removes old objects from cache on put operations`()
    {
        val flushInterval = TimeUnit.MILLISECONDS.toMillis(5)
        val sut = ResumableInfoCache(flushInterval)

        val oldUID = "old"
        val freshUID = "fresh"

        val oldInfo = ResumableInfo(10, 100, oldUID, "file.csv")
        sut.put(oldInfo)

        // first confirm that the object can be retrieved
        var result = sut[oldUID]
        assertThat(result).isNotNull()

        // now wait longer than the flush interval
        Thread.sleep(10)

        // make a fresh put request
        val newInfo = ResumableInfo(10, 100, freshUID, "somefile.csv")
        sut.put(newInfo)

        // the old object should have been flushed
        result = sut[oldUID]
        assertThat(result).isNull()

        // the new object should not have been flushed
        val newResult = sut[freshUID]
        assertThat(newResult).isNotNull()
    }


    @Test
    fun `removes old objects from cache on get operations`()
    {
        val oldUID = "old"
        val freshUID = "fresh"

        val flushInterval = TimeUnit.MILLISECONDS.toMillis(5)
        val sut = ResumableInfoCache(flushInterval)

        val oldInfo = ResumableInfo(10, 100, oldUID, "file.csv")
        sut.put(oldInfo)

        // first confirm that the object can be retrieved
        var result = sut[oldUID]
        assertThat(result).isNotNull()

        // now wait longer than the flush interval
        Thread.sleep(10)

        // make a fresh request for a different object
        val dummyGetRequest = sut[freshUID]
        result = sut[oldUID]
        assertThat(result).isNull()

    }

    @Test
    fun `can update and read from ResumableInfo items in concurrent threads`()
    {
        val sut = ResumableInfoCache()
        val testInfo = ResumableInfo(10, 100, "uid", "file.csv")
        sut.put(testInfo)

        var result = false
        val threads = mutableListOf<Thread>()
        for (i in 1..10)
        {
            threads.add(
                    thread(start = true) {
                        sut["uid"]!!.uploadedChunks[i] = true
                        result = sut["uid"]!!.uploadFinished()
                    })
        }

        while (threads.any { it.isAlive })
        {
            // just wait for threads to finish
        }

        assertThat(result).isTrue()
    }

}