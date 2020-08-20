package org.vaccineimpact.api.tests.clients

import org.junit.Test
import com.geneea.celery.Celery
import org.assertj.core.api.Assertions.*
import org.vaccineimpact.api.db.Config

class CeleryTests
{
    @Test
    fun `can call task`()
    {
        val broker = Config["celery.broker"]
        val backend = Config["celery.backend"]
        val client = Celery.builder()
                .brokerUri(broker)
                .backendUri(backend)
                .build()

        val task = client.submit<ArrayList<String>>("src.task_run_diagnostic_reports.run_diagnostic_reports",
                arrayOf("testGroup", "testDisease") as Array<out Any>)

        // at this point could be fire and forget, but for the sake of testing
        val result = task.get()
        assertThat(result.count()).isEqualTo(2)
    }

}
