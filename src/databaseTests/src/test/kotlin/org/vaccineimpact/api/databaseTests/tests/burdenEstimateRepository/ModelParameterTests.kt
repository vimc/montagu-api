package org.vaccineimpact.api.databaseTests.tests.burdenEstimateRepository

import org.assertj.core.api.Assertions
import org.jooq.Record
import org.junit.Test
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.errors.DatabaseContentsError
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.databaseTests.tests.BurdenEstimateRepositoryTests
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.Tables
import org.vaccineimpact.api.db.fromJoinPath
import org.vaccineimpact.api.models.ModelRun

class ModelParameterTests : BurdenEstimateRepositoryTests()
{

    private val modelRuns = listOf(ModelRun("run1", mapOf("key1" to "value1", "key2" to "value2")),
            ModelRun("run2", mapOf("key1" to "v1", "key2" to "v2")))

    private var returnedIds: ReturnedIds? = null

    @Test
    fun `can add model run parameter set`()
    {
        given { db ->
            returnedIds = setupDatabase(db)
        } makeTheseChanges { repo ->
            repo.addModelRunParameterSet(groupId, touchstoneVersionId, diseaseId,
                     modelRuns, username, timestamp)
        } andCheckDatabase { db ->

            val record = db.dsl.select()
                    .fromJoinPath(Tables.MODEL_RUN_PARAMETER_SET, Tables.UPLOAD_INFO)
                    .fetchOne()

            Assertions.assertThat(record[Tables.MODEL_RUN_PARAMETER_SET.MODEL_VERSION]).isEqualTo(returnedIds!!.modelVersion!!)
            Assertions.assertThat(record[Tables.MODEL_RUN_PARAMETER_SET.RESPONSIBILITY_SET]).isEqualTo(returnedIds!!.responsibilitySetId)

            checkUploadInfo(record)
            checkParameters(db)
        }
    }

    private fun checkUploadInfo(record: Record)
    {
        Assertions.assertThat(record[Tables.UPLOAD_INFO.UPLOADED_BY]).isEqualTo(username)
        Assertions.assertThat(record[Tables.UPLOAD_INFO.UPLOADED_ON].toInstant()).isEqualTo(timestamp)
    }

    private fun checkParameters(db: JooqContext)
    {

        val runs = db.dsl.selectFrom(Tables.MODEL_RUN)
                .fetch()

        Assertions.assertThat(runs.count()).isEqualTo(2)

        val params = db.dsl.selectFrom(Tables.MODEL_RUN_PARAMETER)
        Assertions.assertThat(params.count()).isEqualTo(2)

        val paramValues = db.dsl.selectFrom(Tables.MODEL_RUN_PARAMETER_VALUE)
        Assertions.assertThat(paramValues.count()).isEqualTo(4)
    }

    @Test
    fun `cannot create model run parameter set if group has no model`()
    {
        JooqContext().use { db ->
            returnedIds = setupDatabase(db, addModel = false)
            val repo = makeRepository(db)

            Assertions.assertThatThrownBy {
                repo.addModelRunParameterSet(groupId, touchstoneVersionId, diseaseId,
                        modelRuns, username, timestamp)
            }.isInstanceOf(DatabaseContentsError::class.java)
                    .hasMessageContaining("Modelling group $groupId does not have any models/model versions in the database")
        }
    }

    @Test
    fun `cannot create model run parameter set if no model runs provided`()
    {
        JooqContext().use { db ->
            returnedIds = setupDatabase(db)
            val repo = makeRepository(db)

            Assertions.assertThatThrownBy {
                repo.addModelRunParameterSet(groupId, touchstoneVersionId, diseaseId,
                        listOf(), username, timestamp)
            }.isInstanceOf(BadRequest::class.java)
                    .hasMessageContaining("No model runs provided")
        }
    }

    @Test
    fun `cannot create model run parameter set if touchstone doesn't exist`()
    {
        assertUnknownObjectError(work = { repo ->
            repo.addModelRunParameterSet(groupId, "wrong-id", diseaseId,
                     modelRuns, "test.user", timestamp)
        })
    }

    @Test
    fun `cannot create model run parameter set if group doesn't exist`()
    {
        assertUnknownObjectError({ repo ->
            repo.addModelRunParameterSet("wrong-id", touchstoneVersionId, diseaseId,
                   modelRuns, "test.user", timestamp)
        })
    }

    private fun assertUnknownObjectError(work: (repo: BurdenEstimateRepository) -> Any)
    {
        JooqContext().use { db ->
            setupDatabase(db)
            val repo = makeRepository(db)
            Assertions.assertThatThrownBy {
                work(repo)
            }.isInstanceOf(UnknownObjectError::class.java)
        }
    }

    @Test
    fun `gets empty get model run parameter sets if group has no model`()
    {
        JooqContext().use { db ->
            returnedIds = setupDatabase(db, addModel = false)
            val repo = makeRepository(db)

            val sets = repo.getModelRunParameterSets(groupId, touchstoneVersionId)
            Assertions.assertThat(sets.any()).isFalse()
        }
    }

    @Test
    fun `can get model run parameter sets`()
    {
        given { db ->
            returnedIds = setupDatabase(db)
        } makeTheseChanges { repo ->
            repo.addModelRunParameterSet(groupId, touchstoneVersionId, diseaseId,
                   modelRuns, username, timestamp)

        } andCheck { repo ->
            val sets = repo.getModelRunParameterSets(groupId, touchstoneVersionId)
            val set = sets.first()

            Assertions.assertThat(set.uploadedBy).isEqualTo(username)
            Assertions.assertThat(set.uploadedOn).isEqualTo(timestamp)
            Assertions.assertThat(set.id).isGreaterThan(0)
            Assertions.assertThat(set.disease).isEqualTo(diseaseId)
        }
    }

    @Test
    fun `can get model run parameter sets and values for download`()
    {
        var records = listOf<ModelRun>()
        var contentType = ""

        given { db ->
            setupDatabaseWithModelRunParameterSetValues(db)
        } makeTheseChanges { repo ->
            val flexTableData = repo.getModelRunParameterSet(1)
            records = flexTableData.data.toList()
            contentType = flexTableData.contentType
        } andCheck { _ ->
            Assertions.assertThat(contentType).isEqualTo("text/csv")
            Assertions.assertThat(records.size).isEqualTo(2)
            val row1 = records[0]
            Assertions.assertThat(row1.runId).isEqualTo("1")
            Assertions.assertThat(row1.parameterValues["<param_1>"]).isEqualTo("aa")
            Assertions.assertThat(row1.parameterValues["<param_2>"]).isEqualTo("bb")
            val row2 = records[1]
            Assertions.assertThat(row2.runId).isEqualTo("2")
            Assertions.assertThat(row2.parameterValues["<param_1>"]).isEqualTo("cc")
            Assertions.assertThat(row2.parameterValues["<param_2>"]).isEqualTo("dd")
        }
    }

    @Test
    fun `cannot get model run parameter sets if touchstone doesn't exist`()
    {
        assertUnknownObjectError(work = { repo ->
            repo.getModelRunParameterSets(groupId, "wrong-id")
        })
    }

    @Test
    fun `cannot get model run parameter sets if group doesn't exist`()
    {
        assertUnknownObjectError({ repo ->
            repo.getModelRunParameterSets("wrong-id", touchstoneVersionId)
        })
    }

}