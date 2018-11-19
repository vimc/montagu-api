package org.vaccineimpact.api.databaseTests.tests.burdenEstimateRepository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.Tables
import org.vaccineimpact.api.db.Tables.BURDEN_OUTCOME
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.models.BurdenEstimateGrouping
import org.vaccineimpact.api.models.BurdenEstimateSet
import org.vaccineimpact.api.models.BurdenEstimateSetType
import org.vaccineimpact.api.models.BurdenEstimateSetTypeCode
import java.time.Instant

class RetrieveBurdenEstimatesTests : BurdenEstimateRepositoryTests()
{
    @Test
    fun `can get outcome ids matching string`()
    {
        val result = withRepo {
            it.getBurdenOutcomeIds("deaths")
        }

        val outcomes = withDatabase {
            it.dsl.selectFrom(BURDEN_OUTCOME)
                    .where(BURDEN_OUTCOME.ID.`in`(result))
                    .fetch()
        }

        assertThat(result.count()).isGreaterThan(1)
        assertThat(outcomes.all { it.code.contains("deaths") }).isTrue()
    }

    @Test
    fun `can get estimates for responsibility grouped by age`()
    {

        val (responsibilityId, outcomeId, setId) = withDatabase {
            val ids = setupDatabase(it)
            val modelVersionId = ids.modelVersion!!
            val setId = it.addBurdenEstimateSet(ids.responsibility, modelVersionId, username)

            for (a in 1..10)
            {
                for (y in 2000..2003)
                {
                    it.addBurdenEstimate(setId, "AFG", year = y.toShort(), age = a.toShort(), outcome = "deaths")
                    it.addBurdenEstimate(setId, "AGO", year = y.toShort(), age = a.toShort(), outcome = "deaths")
                }
            }

            val outcomeId = it.dsl.select(Tables.BURDEN_OUTCOME.ID)
                    .from(Tables.BURDEN_OUTCOME)
                    .where(Tables.BURDEN_OUTCOME.CODE.eq("deaths"))
                    .fetchOne().value1()

            Triple(ids.responsibility, outcomeId, setId)
        }

        val result = withRepo {
            it.getEstimates(setId, responsibilityId, listOf(outcomeId))
        }

        assertThat(result.keys).hasSameElementsAs((1..10).map { it.toShort() })
        assertThat(result.values.all { it.count() == 4 }).isTrue()
        assertThat(result.values.all { it.all { it.value == 200F } }).isTrue()
    }

    @Test
    fun `can get estimates grouped by year`()
    {

        val (responsibilityId, outcomeId, setId) = withDatabase {
            val ids = setupDatabase(it)
            val modelVersionId = ids.modelVersion!!
            val setId = it.addBurdenEstimateSet(ids.responsibility, modelVersionId, username)

            for (a in 1..10)
            {
                for (y in 2000..2003)
                {
                    it.addBurdenEstimate(setId, "AFG", year = y.toShort(), age = a.toShort(), outcome = "deaths")
                    it.addBurdenEstimate(setId, "AGO", year = y.toShort(), age = a.toShort(), outcome = "deaths")
                }
            }

            val outcomeId = it.dsl.select(Tables.BURDEN_OUTCOME.ID)
                    .from(Tables.BURDEN_OUTCOME)
                    .where(Tables.BURDEN_OUTCOME.CODE.eq("deaths"))
                    .fetchOne().value1()

            Triple(ids.responsibility, outcomeId, setId)
        }

        val result = withRepo {
            it.getEstimates(setId, responsibilityId, listOf(outcomeId), BurdenEstimateGrouping.YEAR)
        }

        assertThat(result.keys).hasSameElementsAs((2000..2003).map { it.toShort() })
        assertThat(result.values.all { it.count() == 10 }).isTrue()
        assertThat(result.values.all { it.all { it.value == 200F } }).isTrue()
    }

    @Test
    fun `does not get estimates for wrong set`()
    {

        val (outcomeId, responsibilityId, badSetId) = withDatabase {
            val ids = setupDatabase(it)
            val modelVersionId = ids.modelVersion!!
            val setId = it.addBurdenEstimateSet(ids.responsibility, modelVersionId, username)
            val badSetId = it.addBurdenEstimateSet(ids.responsibility, modelVersionId, username)

            for (a in 1..10)
            {
                for (y in 2000..2003)
                {
                    it.addBurdenEstimate(setId, "AFG", year = y.toShort(), age = a.toShort(), outcome = "deaths")
                    it.addBurdenEstimate(setId, "AGO", year = y.toShort(), age = a.toShort(), outcome = "deaths")
                }
            }

            val outcomeId = it.dsl.select(Tables.BURDEN_OUTCOME.ID)
                    .from(Tables.BURDEN_OUTCOME)
                    .where(Tables.BURDEN_OUTCOME.CODE.eq("deaths"))
                    .fetchOne().value1()

            Triple(outcomeId, ids.responsibility, badSetId)
        }

        val result = withRepo {
            it.getEstimates(badSetId, responsibilityId, listOf(outcomeId))
        }

        assertThat(result.keys.count()).isEqualTo(0)
    }

    @Test
    fun `getEstimates throws error if set does not belong to responsibility`()
    {

        val (outcomeId, setId) = withDatabase {
            val ids = setupDatabase(it)
            val modelVersionId = ids.modelVersion!!
            val setId = it.addBurdenEstimateSet(ids.responsibility, modelVersionId, username)

            for (a in 1..10)
            {
                for (y in 2000..2003)
                {
                    it.addBurdenEstimate(setId, "AFG", year = y.toShort(), age = a.toShort(), outcome = "deaths")
                    it.addBurdenEstimate(setId, "AGO", year = y.toShort(), age = a.toShort(), outcome = "deaths")
                }
            }

            val outcomeId = it.dsl.select(Tables.BURDEN_OUTCOME.ID)
                    .from(Tables.BURDEN_OUTCOME)
                    .where(Tables.BURDEN_OUTCOME.CODE.eq("deaths"))
                    .fetchOne().value1()

            Pair(outcomeId, setId)
        }

        withRepo {
            assertThatThrownBy {
                it.getEstimates(setId, 67, listOf(outcomeId))
            }.isInstanceOf(UnknownObjectError::class.java).hasMessageContaining("burden-estimate-set")
        }

    }

    @Test
    fun `does not get estimates for wrong outcomes`()
    {

        val (responsibilityId, outcomeId, setId) = withDatabase {
            val ids = setupDatabase(it)
            val modelVersionId = ids.modelVersion!!
            val setId = it.addBurdenEstimateSet(ids.responsibility, modelVersionId, username)
            it.updateCurrentEstimate(ids.responsibility, setId)

            for (a in 1..10)
            {
                for (y in 2000..2003)
                {
                    it.addBurdenEstimate(setId, "AFG", year = y.toShort(), age = a.toShort(), outcome = "cases")
                    it.addBurdenEstimate(setId, "AGO", year = y.toShort(), age = a.toShort(), outcome = "cases")
                }
            }

            val outcomeId = it.dsl.select(Tables.BURDEN_OUTCOME.ID)
                    .from(Tables.BURDEN_OUTCOME)
                    .where(Tables.BURDEN_OUTCOME.CODE.eq("deaths"))
                    .fetchOne().value1()

            Triple(ids.responsibility, outcomeId, setId)
        }

        val result = withRepo {
            it.getEstimates(setId, responsibilityId, listOf(outcomeId))
        }

        assertThat(result.keys.count()).isEqualTo(0)
    }


    @Test
    fun `can retrieve burden estimate sets`()
    {
        val otherUser = "some.other.user"
        var setA = 0
        var setB = 0
        val before = Instant.now()
        given { db ->
            val ids = setupDatabase(db)
            val modelVersionId = ids.modelVersion!!
            db.addUserForTesting(otherUser)
            setA = db.addBurdenEstimateSet(ids.responsibility, modelVersionId, username)
            setB = db.addBurdenEstimateSet(ids.responsibility, modelVersionId, "some.other.user")
            db.addBurdenEstimateProblem("some problem", setB)
        } check { repo ->
            val after = Instant.now()
            val sets = repo.getBurdenEstimateSets(groupId, touchstoneVersionId, scenarioId).toList()
            val a = sets.single { it.id == setA }
            Assertions.assertThat(a.uploadedBy).isEqualTo(username)
            Assertions.assertThat(a.uploadedOn).isAfter(before)
            Assertions.assertThat(a.uploadedOn).isBefore(after)
            Assertions.assertThat(a.problems).isEmpty()

            val b = sets.single { it.id == setB }
            Assertions.assertThat(b.uploadedBy).isEqualTo("some.other.user")
            Assertions.assertThat(b.uploadedOn).isAfter(a.uploadedOn)
            Assertions.assertThat(b.uploadedOn).isBefore(after)
            Assertions.assertThat(b.problems).hasSameElementsAs(listOf("some problem"))
        }
    }

    @Test
    fun `can retrieve burden estimate set type`()
    {
        var setA = 0
        var setB = 0
        var setC = 0
        var setD = 0
        given { db ->
            val ids = setupDatabase(db)
            setA = addBurdenEstimateSet(db, ids, setType = "central-unknown", setTypeDetails = "unknown")
            setB = addBurdenEstimateSet(db, ids, setType = "central-single-run", setTypeDetails = null)
            setC = addBurdenEstimateSet(db, ids, setType = "central-averaged", setTypeDetails = "mean")
            setD = addBurdenEstimateSet(db, ids, setType = "stochastic", setTypeDetails = null)
        } check { repo ->
            val sets = repo.getBurdenEstimateSets(groupId, touchstoneVersionId, scenarioId)
            checkSetHasExpectedType(sets, setA, BurdenEstimateSetType(BurdenEstimateSetTypeCode.CENTRAL_UNKNOWN, "unknown"))
            checkSetHasExpectedType(sets, setB, BurdenEstimateSetType(BurdenEstimateSetTypeCode.CENTRAL_SINGLE_RUN, null))
            checkSetHasExpectedType(sets, setC, BurdenEstimateSetType(BurdenEstimateSetTypeCode.CENTRAL_AVERAGED, "mean"))
            checkSetHasExpectedType(sets, setD, BurdenEstimateSetType(BurdenEstimateSetTypeCode.STOCHASTIC, null))
        }
    }

    @Test
    fun `can retrieve single burden estimate set`()
    {
        val setId = withDatabase { db ->
            val ids = setupDatabase(db)
            val modelVersionId = ids.modelVersion!!
            db.addBurdenEstimateSet(ids.responsibility, modelVersionId, username)
        }
        withRepo { repo ->
            val set = repo.getBurdenEstimateSet(setId)
            assertThat(set.uploadedBy).isEqualTo(username)
            assertThat(set.problems).isEmpty()
        }
    }

    @Test
    fun `getSetForResponsibility set throws unknown object error if set is for different responsibility`()
    {
        val scenario2 = "scenario-2"
        val (setId, responsibilityId) = withDatabase {
            val returnedIds = setupDatabase(it)
            it.addScenarioDescription(scenario2, "Test scenario 2", diseaseId, addDisease = false)
            val responsibilityId = it.addResponsibility(returnedIds.responsibilitySetId, touchstoneVersionId, scenario2)
            val setId = it.addBurdenEstimateSet(responsibilityId, returnedIds.modelVersion!!, username, "complete")
            Pair(responsibilityId, setId)
        }
        withRepo { repo ->
            Assertions.assertThatThrownBy {
                repo.getBurdenEstimateSetForResponsibility(setId, responsibilityId)
            }.isInstanceOf(UnknownObjectError::class.java)
        }
    }

    private fun checkSetHasExpectedType(sets: List<BurdenEstimateSet>, setId: Int, expectedType: BurdenEstimateSetType)
    {
        val set = sets.singleOrNull { it.id == setId }
        assertThat(set?.type).isEqualTo(expectedType)
    }

    private fun addBurdenEstimateSet(
            db: JooqContext, ids: ReturnedIds, username: String? = null,
            setType: String = "central-single-run", setTypeDetails: String? = null
    ): Int
    {
        return db.addBurdenEstimateSet(
                ids.responsibility,
                ids.modelVersion!!,
                username ?: this.username,
                setType = setType, setTypeDetails = setTypeDetails
        )
    }
}