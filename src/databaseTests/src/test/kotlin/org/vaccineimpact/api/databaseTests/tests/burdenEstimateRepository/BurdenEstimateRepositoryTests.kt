package org.vaccineimpact.api.databaseTests.tests.burdenEstimateRepository

import org.assertj.core.api.Assertions
import org.jooq.Record
import org.jooq.Result
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.burdenestimates.CentralBurdenEstimateWriter
import org.vaccineimpact.api.app.repositories.jooq.*
import org.vaccineimpact.api.app.repositories.jooq.mapping.BurdenMappingHelper
import org.vaccineimpact.api.databaseTests.RepositoryTests
import org.vaccineimpact.api.db.*
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.models.*
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset

abstract class BurdenEstimateRepositoryTests : RepositoryTests<BurdenEstimateRepository>()
{
    protected data class ReturnedIds(val modelVersion: Int?, val responsibility: Int, val responsibilitySetId: Int,
                                     val modelRunParameterSetId: Int? = null)

    override fun makeRepository(db: JooqContext): BurdenEstimateRepository
    {
        val scenario = JooqScenarioRepository(db.dsl)
        val touchstone = JooqTouchstoneRepository(db.dsl, scenario)

        val modellingGroup = JooqModellingGroupRepository(db.dsl, touchstone)
        return JooqBurdenEstimateRepository(db.dsl, scenario, touchstone, modellingGroup)
    }

    protected fun makeRepository(
            db: JooqContext,
            centralEstimateWriter: CentralBurdenEstimateWriter
    ): JooqBurdenEstimateRepository
    {
        val scenario = JooqScenarioRepository(db.dsl)
        val touchstone = JooqTouchstoneRepository(db.dsl, scenario)
        val modellingGroup = JooqModellingGroupRepository(db.dsl, touchstone)
        return JooqBurdenEstimateRepository(db.dsl, scenario, touchstone, modellingGroup, BurdenMappingHelper(),
                centralEstimateWriter)
    }

    protected val scenarioId = "scenario-1"
    protected val groupId = "group-1"
    protected val touchstoneVersionId = "touchstone-1"
    protected val modelId = "model-1"
    protected val modelVersion = "version-1"
    protected val username = "some.user"
    protected val timestamp = LocalDateTime.of(2017, Month.JUNE, 13, 12, 30).toInstant(ZoneOffset.UTC)
    protected val diseaseId = "Hib3"
    protected val diseaseName = "Hib3 Name"

    protected fun setupDatabase(db: JooqContext, addModel: Boolean = true,
                                responsibilitySetStatus: String = "incomplete"): ReturnedIds
    {
        db.addTouchstoneVersion("touchstone", 1, "Touchstone 1", addTouchstone = true)
        db.addDisease(diseaseId, diseaseName)
        db.addScenarioDescription(scenarioId, "Test scenario", diseaseId, addDisease = false)
        db.addGroup(groupId, "Test group")
        val modelVersionId = if (addModel)
        {
            db.addModel(modelId, groupId, diseaseId)
            db.addModelVersion(modelId, modelVersion, setCurrent = true)
        }
        else
        {
            null
        }
        val setId = db.addResponsibilitySet(groupId, touchstoneVersionId, responsibilitySetStatus)
        val responsibilityId = db.addResponsibility(setId, touchstoneVersionId, scenarioId)
        db.addUserForTesting(username)
        return ReturnedIds(modelVersionId, responsibilityId, setId)
    }

    protected fun setupDatabaseWithBurdenEstimateSet(
            db: JooqContext,
            status: String = "empty",
            type: String = "central-single-run"
    ): Int
    {
        val ids = setupDatabase(db)
        return db.addBurdenEstimateSet(ids.responsibility, ids.modelVersion!!, username,
                status = status, setType = type)
    }

    protected fun setupDatabaseWithBurdenEstimateSetAndReturnIds(
            db: JooqContext,
            status: String = "empty",
            type: String = "central-single-run"
    ): Pair<ReturnedIds, Int>
    {
        val ids = setupDatabase(db)
        return Pair(ids, db.addBurdenEstimateSet(ids.responsibility, ids.modelVersion!!, username,
                status = status, setType = type))
    }

    protected fun setupDatabaseWithModelRunParameterSet(db: JooqContext,
                                                        responsibilitySetStatus: String = "incomplete"): ReturnedIds
    {
        db.addTouchstoneVersion("touchstone", 1, "Touchstone 1", addTouchstone = true)
        db.addScenarioDescription(scenarioId, "Test scenario", "Hib3", addDisease = true)
        db.addGroup(groupId, "Test group")

        db.addModel(modelId, groupId, diseaseId)
        val modelVersionId = db.addModelVersion(modelId, modelVersion, setCurrent = true)

        val setId = db.addResponsibilitySet(groupId, touchstoneVersionId, responsibilitySetStatus)
        val responsibilityId = db.addResponsibility(setId, touchstoneVersionId, scenarioId)
        db.addUserForTesting(username)

        val parameterSetId = db.addModelRunParameterSet(setId, modelVersionId, username)

        return ReturnedIds(modelVersionId, responsibilityId, setId, parameterSetId)
    }

    protected fun setupDatabaseWithModelRunParameterSetValues(db: JooqContext)
    {
        db.addTouchstoneVersion("touchstone", 1, "Touchstone 1", addTouchstone = true)
        db.addScenarioDescription(scenarioId, "Test scenario", "Hib3", addDisease = true)
        db.addGroup(groupId, "Test group")

        db.addModel(modelId, groupId, diseaseId)
        val modelVersionId = db.addModelVersion(modelId, modelVersion, setCurrent = true)

        val setId = db.addResponsibilitySet(groupId, touchstoneVersionId, "incomplete")
        db.addResponsibility(setId, touchstoneVersionId, scenarioId)
        db.addUserForTesting(username)

        val paramsSetId = db.addModelRunParameterSet(setId, modelVersionId, username)
        val modelRunId = db.addModelRun(paramsSetId, "1")
        val modelRunId2 = db.addModelRun(paramsSetId, "2")
        val modelRunParameterId1 = db.addModelRunParameter(paramsSetId, "<param_1>")
        val modelRunParameterId2 = db.addModelRunParameter(paramsSetId, "<param_2>")
        db.addModelRunParameterValue(modelRunId, modelRunParameterId1, "aa")
        db.addModelRunParameterValue(modelRunId, modelRunParameterId2, "bb")
        db.addModelRunParameterValue(modelRunId2, modelRunParameterId1, "cc")
        db.addModelRunParameterValue(modelRunId2, modelRunParameterId2, "dd")
    }

    protected fun checkBurdenEstimateSetMetadata(db: JooqContext,
                                                 setId: Int,
                                                 returnedIds: ReturnedIds,
                                                 expectedStatus: String)
            : Int
    {
        val t = Tables.BURDEN_ESTIMATE_SET
        val set = db.dsl.selectFrom(t).where(t.ID.eq(setId)).fetchOne()
        Assertions.assertThat(set[t.MODEL_VERSION]).isEqualTo(returnedIds.modelVersion!!)
        Assertions.assertThat(set[t.RESPONSIBILITY]).isEqualTo(returnedIds.responsibility)
        Assertions.assertThat(set[t.UPLOADED_BY]).isEqualTo(username)
        Assertions.assertThat(set[t.UPLOADED_ON].toInstant()).isEqualTo(timestamp)
        Assertions.assertThat(set[t.STATUS]).isEqualTo(expectedStatus)
        return set[t.ID]
    }


    protected fun checkBurdenEstimates(db: JooqContext, setId: Int)
    {
        val records = getEstimatesInOrder(db)
        checkRecord(records[0], setId, 2000, 50, "AFG", "cases", 100F)
        checkRecord(records[1], setId, 2000, 50, "AFG", "cohort_size", 1000F)
        checkRecord(records[2], setId, 2000, 50, "AFG", "deaths", 10F)
        checkRecord(records[3], setId, 1980, 30, "AGO", "cohort_size", 2000F)
        checkRecord(records[4], setId, 1980, 30, "AGO", "dalys", 73.6F)
        checkRecord(records[5], setId, 1980, 30, "AGO", "deaths", 20F)
    }

    protected fun checkModelRuns(db: JooqContext, modelRunData: ModelRunTestData)
    {
        val records = getEstimatesInOrder(db)
        val expectedIds = modelRunData.internalIds
        for (i in 0..2)
        {
            Assertions.assertThat(records[i][Tables.BURDEN_ESTIMATE.MODEL_RUN]).isEqualTo(expectedIds[0])
        }
        for (i in 3..5)
        {
            Assertions.assertThat(records[i][Tables.BURDEN_ESTIMATE.MODEL_RUN]).isEqualTo(expectedIds[1])
        }
    }

    protected fun getEstimatesInOrder(db: JooqContext): Result<Record>
    {
        // We order the rows coming back so they are in a guaranteed order. This allows
        // us to write simple hardcoded expectations.
        val t = Tables.BURDEN_ESTIMATE
        return db.dsl.select(t.BURDEN_ESTIMATE_SET, t.YEAR, t.AGE, t.VALUE, t.MODEL_RUN)
                .select(Tables.BURDEN_OUTCOME.CODE)
                .select(Tables.COUNTRY.ID)
                .fromJoinPath(Tables.BURDEN_ESTIMATE, Tables.BURDEN_OUTCOME)
                .join(Tables.COUNTRY).on(Tables.BURDEN_ESTIMATE.COUNTRY.eq(Tables.COUNTRY.NID))
                .orderBy(Tables.BURDEN_ESTIMATE.COUNTRY, Tables.BURDEN_OUTCOME.CODE)
                .fetch()
    }

    private fun checkRecord(record: Record, setId: Int,
                            year: Short, age: Short, country: String, outcomeCode: String, outcomeValue: Float)
    {
        val t = Tables.BURDEN_ESTIMATE
        Assertions.assertThat(record[t.BURDEN_ESTIMATE_SET]).isEqualTo(setId)
        Assertions.assertThat(record[Tables.COUNTRY.ID]).isEqualTo(country)
        Assertions.assertThat(record[t.YEAR]).isEqualTo(year)
        Assertions.assertThat(record[t.AGE]).isEqualTo(age)
        Assertions.assertThat(record[Tables.BURDEN_OUTCOME.CODE]).isEqualTo(outcomeCode)
        Assertions.assertThat(record[t.VALUE]).isEqualTo(outcomeValue)
    }

    protected fun data(runs: List<String?> = listOf(null, null)) = sequenceOf(
            BurdenEstimateWithRunId(diseaseId, runs[0], 2000, 50, "AFG", "Afghanistan", 1000F, mapOf(
                    "deaths" to 10F,
                    "cases" to 100F
            )),
            BurdenEstimateWithRunId(diseaseId, runs[1], 1980, 30, "AGO", "Angola", 2000F, mapOf(
                    "deaths" to 20F,
                    "dalys" to 73.6F
            ))
    )

    protected fun addModelRuns(db: JooqContext, responsibilitySetId: Int, modelVersionId: Int): ModelRunTestData
    {
        val runs = listOf("marathon", "sprint")
        val runParameterSetId = db.addModelRunParameterSet(responsibilitySetId, modelVersionId, username)
        return ModelRunTestData(runParameterSetId, runs.map { runId ->
            runId to db.addModelRun(runParameterSetId, runId)
        })
    }

    protected val defaultProperties = CreateBurdenEstimateSet(
            BurdenEstimateSetType(BurdenEstimateSetTypeCode.CENTRAL_UNKNOWN),
            modelRunParameterSet = null
    )

    data class ModelRunTestData(val runParameterSetId: Int, val runs: List<Pair<String, Int>>)
    {
        val externalIds = runs.map { (runId, _) -> runId }
        val internalIds = runs.map { (_, internalId) -> internalId }
    }
}