package org.vaccineimpact.api.tests.models

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.app.models.ResumableInfo
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.io.File

class ResumableInfoTests : MontaguTests()
{

    @Test
    fun `detects upload has finished when all chunks are present`()
    {
        val sut = ResumableInfo(totalChunks = 10, chunkSize = 100, uniqueIdentifier = "uid", filePath = "file.csv")

        for (i in 1..10)
        {
            sut.uploadedChunks[i] = true
            assertThat(sut.uploadFinished()).isEqualTo(i == 10)
        }
    }

    @Test
    fun `renames file if upload has finished`()
    {
        val tempFile = File("file.csv.temp")
        val finalFile = File("file.csv")
        tempFile.createNewFile()

        val sut = ResumableInfo(totalChunks = 10, chunkSize = 100, uniqueIdentifier = "uid", file = finalFile)
        assertThat(sut.filePath).endsWith("file.csv.temp")

        for (i in 1..10)
        {
            sut.uploadedChunks[i] = true
        }

        val result = sut.uploadFinished()
        assertThat(result).isTrue()
        assertThat(sut.filePath).endsWith("file.csv")
        assertThat(tempFile.exists()).isFalse()
        assertThat(finalFile.exists()).isTrue()

        finalFile.delete()
    }

    @Test
    fun `deletes file on cleanup`() {
        val tempFile = File("file.csv.temp")
        val finalFile = File("file.csv")
        tempFile.createNewFile()

        val sut = ResumableInfo(totalChunks = 10, chunkSize = 100, uniqueIdentifier = "uid", file = finalFile)
        sut.cleanUp()

        assertThat(tempFile.exists()).isFalse()
        assertThat(finalFile.exists()).isFalse()
    }

}