package org.vaccineimpact.api.tests.clients

import org.junit.Test
import org.assertj.core.api.Assertions.*
import org.vaccineimpact.api.app.clients.CeleryClient
import org.vaccineimpact.api.test_helpers.MontaguTests

class CeleryTests: MontaguTests()
{
    @Test
    fun `can call task`()
    {
       val client = CeleryClient()
        val task = client.runDiagnosticReport("testGroup", "testDisease")

        val result = task.get().mapValues { DiagnosticReportTaskResult(it.value["published"]?: false) }
        assertThat(result.count()).isEqualTo(2)
        assertThat(result.entries.first().value.published).isTrue()
        assertThat(result.entries.last().value.published).isTrue()
    }
}

data class DiagnosticReportTaskResult(val published: Boolean)
