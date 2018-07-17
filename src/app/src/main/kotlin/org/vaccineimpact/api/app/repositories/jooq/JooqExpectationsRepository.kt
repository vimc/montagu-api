package org.vaccineimpact.api.app.repositories.jooq

import org.jooq.DSLContext
import org.jooq.Record2
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.repositories.ExpectationsRepository
import org.vaccineimpact.api.app.repositories.ResponsibilitiesRepository
import org.vaccineimpact.api.db.Tables.*
import org.vaccineimpact.api.db.fromJoinPath
import org.vaccineimpact.api.db.tables.BurdenEstimateCountryExpectation
import org.vaccineimpact.api.db.tables.BurdenEstimateExpectation
import org.vaccineimpact.api.db.tables.BurdenEstimateOutcomeExpectation
import org.vaccineimpact.api.db.tables.records.BurdenEstimateExpectationRecord
import org.vaccineimpact.api.models.*

class JooqExpectationsRepository(dsl: DSLContext,
                                 private val responsibilitiesRepository: ResponsibilitiesRepository)
    : JooqRepository(dsl), ExpectationsRepository
{
    private object Tables
    {
        val expectations: BurdenEstimateExpectation = BURDEN_ESTIMATE_EXPECTATION
        val countries: BurdenEstimateCountryExpectation = BURDEN_ESTIMATE_COUNTRY_EXPECTATION
        val outcomes: BurdenEstimateOutcomeExpectation = BURDEN_ESTIMATE_OUTCOME_EXPECTATION
    }

    override fun getExpectationsForResponsibility(groupId: String,
                                                  touchstoneVersionId: String, scenarioId: String): Expectations
    {
        val responsibilityId = responsibilitiesRepository.getResponsibilityId(groupId, touchstoneVersionId, scenarioId)
        val basicData = dsl.fetchAny(
                Tables.expectations,
                Tables.expectations.RESPONSIBILITY.eq(responsibilityId)
        ) ?: throw UnknownObjectError(responsibilityId, "burden-estimate-expectation")

        val countries = getCountries(basicData)
        val outcomes = getOutcomes(basicData)

        return Expectations(
                basicData.yearMinInclusive..basicData.yearMaxInclusive,
                basicData.ageMinInclusive..basicData.ageMaxInclusive,
                CohortRestriction(basicData.cohortMinInclusive, basicData.cohortMaxInclusive),
                countries,
                outcomes
        )
    }

    private fun getOutcomes(basicData: BurdenEstimateExpectationRecord): List<String>
    {
        return dsl.select(Tables.outcomes.OUTCOME)
                .from(Tables.outcomes)
                .where(Tables.outcomes.BURDEN_ESTIMATE_EXPECTATION.eq(basicData.id))
                .fetchInto(String::class.java)
                .toList()
    }

    private fun getCountries(basicData: BurdenEstimateExpectationRecord): List<Country>
    {
        return dsl.select(COUNTRY.ID, COUNTRY.NAME)
                .fromJoinPath(Tables.countries, COUNTRY)
                .where(Tables.countries.BURDEN_ESTIMATE_EXPECTATION.eq(basicData.id))
                .fetchInto(Country::class.java)
                .toList()
    }
}