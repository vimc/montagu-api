package org.vaccineimpact.api.tests.clients

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.junit.Test
import org.vaccineimpact.api.app.clients.CeleryClient
import org.vaccineimpact.api.test_helpers.MontaguTests

class CeleryTests : MontaguTests()
{
    @Test
    fun `can call task`()
    {
        val client = CeleryClient()
        val task = client.runDiagnosticReport("testGroup", "testDisease", "testTouchstone")

        val result = task.get().mapValues { DiagnosticReportTaskResult(it.value["published"] ?: false) }
        assertThat(result.count()).isEqualTo(1)
        assertThat(result.entries.first().value.published).isTrue()
    }
}

data class DiagnosticReportTaskResult(val published: Boolean)
