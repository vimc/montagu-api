package org.vaccineimpact.api.databaseTests.tests

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.app.repositories.ExpectationsRepository
import org.vaccineimpact.api.app.repositories.jooq.JooqExpectationsRepository
import org.vaccineimpact.api.databaseTests.RepositoryTests
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.models.CohortRestriction
import org.vaccineimpact.api.models.Country

class ExpectationsRepositoryTests : RepositoryTests<ExpectationsRepository>()
{
    override fun makeRepository(db: JooqContext) = JooqExpectationsRepository(db.dsl)

    private val groupId = "group"
    private val scenarioId = "scenario"
    private val touchstoneVersionId = "touchstone-1"

    @Test
    fun `can pull basic expectations`()
    {
        val responsibilityId = addResponsibilityAnd { db, responsibilityId ->
            db.addExpectations(
                    responsibilityId,
                    yearMinInclusive = 2000,
                    yearMaxInclusive = 2100,
                    ageMinInclusive = 0,
                    ageMaxInclusive = 99,
                    cohortMinInclusive = null,
                    cohortMaxInclusive = null,
                    countries = emptyList(),
                    outcomes = emptyList()
            )
            responsibilityId
        }
        withRepo { repo ->
            val result = repo.getExpectationsForResponsibility(responsibilityId)
            assertThat(result.years).isEqualTo(2000..2100)
            assertThat(result.ages).isEqualTo(0..99)
            assertThat(result.cohorts).isEqualTo(CohortRestriction())
            assertThat(result.countries).isEmpty()
            assertThat(result.outcomes).isEmpty()
        }
    }

    @Test
    fun `can pull cohort expectations`()
    {
        val responsibilityId = addResponsibilityAnd { db, responsibilityId ->
            db.addExpectations(
                    responsibilityId,
                    cohortMinInclusive = 2005,
                    cohortMaxInclusive = 2015
            )
            responsibilityId
        }
        withRepo { repo ->
            val result = repo.getExpectationsForResponsibility(responsibilityId)
            assertThat(result.cohorts).isEqualTo(CohortRestriction(
                    minimumBirthYear = 2005,
                    maximumBirthYear = 2015
            ))
        }
    }

    @Test
    fun `can pull country expectations`()
    {
        val responsibilityId = addResponsibilityAnd { db, responsibilityId ->
            db.addCountries(listOf("ABC", "DEF", "GHI"))
            db.addExpectations(
                    responsibilityId,
                    countries = listOf("ABC", "DEF")
            )
            responsibilityId
        }
        withRepo { repo ->
            val result = repo.getExpectationsForResponsibility(responsibilityId)
            assertThat(result.countries).hasSameElementsAs(listOf(
                    Country("ABC", "ABC-Name"),
                    Country("DEF", "DEF-Name")
            ))
        }
    }

    @Test
    fun `can pull outcome expectations`()
    {
        val responsibilityId = addResponsibilityAnd { db, responsibilityId ->
            db.addExpectations(
                    responsibilityId,
                    outcomes = listOf("cases", "deaths")
            )
            responsibilityId
        }
        withRepo { repo ->
            val result = repo.getExpectationsForResponsibility(responsibilityId)
            assertThat(result.outcomes).hasSameElementsAs(listOf(
                    "cases",
                    "deaths"
            ))
        }
    }

    @Test
    fun `can get expectation ids for group and touchstone`()
    {
        val expectationId = addResponsibilityAnd { db, responsibilityId ->
            db.addExpectations(
                    responsibilityId
            )
        }
        withRepo { repo ->
            val result = repo.getExpectationIdsForGroupAndTouchstone(groupId, touchstoneVersionId)
            assertThat(result).hasSameElementsAs(listOf(
                   expectationId
            ))
        }
    }

    @Test
    fun `getExpectationIdsForGroupAndTouchstone only retrieves expectation ids for given group`()
    {
        val badGroupId = "badgroup"
        addResponsibilityAnd { db, responsibilityId ->

            db.addGroup(badGroupId)
            db.addExpectations(
                    responsibilityId
            )
        }
        withRepo { repo ->
            val result = repo.getExpectationIdsForGroupAndTouchstone(badGroupId, touchstoneVersionId)
            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `getExpectationIdsForGroupAndTouchstone only retrieves expectation ids for given touchstone`()
    {
        val badTouchstoneId = "touchstone-2"
        addResponsibilityAnd { db, responsibilityId ->

            db.addTouchstoneVersion("touchstone", 2)
            db.addExpectations(
                    responsibilityId
            )
        }
        withRepo { repo ->
            val result = repo.getExpectationIdsForGroupAndTouchstone(groupId, badTouchstoneId)
            assertThat(result).isEmpty()
        }
    }

    @Test
    fun `can get expectation by id`()
    {
        val expectationId = addResponsibilityAnd { db, responsibilityId ->
            db.addExpectations(
                    responsibilityId,
                    yearMinInclusive = 2000,
                    yearMaxInclusive = 2100,
                    ageMinInclusive = 0,
                    ageMaxInclusive = 99,
                    cohortMinInclusive = null,
                    cohortMaxInclusive = null,
                    countries = emptyList(),
                    outcomes = emptyList()
            )
        }
        withRepo { repo ->
            val result = repo.getExpectationsById(expectationId)
            assertThat(result.years).isEqualTo(2000..2100)
            assertThat(result.ages).isEqualTo(0..99)
            assertThat(result.cohorts).isEqualTo(CohortRestriction())
            assertThat(result.countries).isEmpty()
            assertThat(result.outcomes).isEmpty()
        }
    }

    private fun addResponsibilityAnd(action: (JooqContext, Int) -> Int) = withDatabase { db ->
        db.addTouchstoneVersion("touchstone", 1, addTouchstone = true)
        db.addScenarioDescription(scenarioId, "desc", "YF", addDisease = true)
        db.addGroup(groupId)
        val responsibilityId = db.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)
        action(db, responsibilityId)
    }
}
