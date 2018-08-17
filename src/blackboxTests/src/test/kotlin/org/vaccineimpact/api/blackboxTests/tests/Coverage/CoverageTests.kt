package org.vaccineimpact.api.blackboxTests.tests.Coverage

import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.models.helpers.FlexibleColumns
import org.vaccineimpact.api.test_helpers.DatabaseTest
import java.math.BigDecimal

abstract class CoverageTests : DatabaseTest()
{
    // a helper class to deserialize the wide format coverage data
    @FlexibleColumns
    data class TestWideCoverageRow(
            val scenario: String,
            val setName: String,
            val vaccine: String,
            val gaviSupport: String,
            val activityType: String,
            val countryCode: String,
            val country: String,
            val ageFirst: Int?,
            val ageLast: Int?,
            val ageRangeVerbatim: String?,
            val coverageAndTargetPerYear: Map<String, String?>
    )

    protected val touchstoneVersionId = "touchstone-1"
    protected val scenarioId = "scenario-1"
    protected val coverageSetId = 1
    protected val groupId = "group-1"

    protected fun addCoverageData(db: JooqContext, touchstoneStatus: String,
                                testYear: Int = 1955,
                                target: BigDecimal = BigDecimal(100.12),
                                coverage: BigDecimal = BigDecimal(200.13))
    {
        db.addGroup(groupId, "description")
        db.addScenarioDescription(scenarioId, "description 1", "disease-1", addDisease = true)
        db.addTouchstoneVersion("touchstone", 1, "description", touchstoneStatus, addTouchstone = true)
        val setId = db.addResponsibilitySet(groupId, touchstoneVersionId, "submitted")
        db.addResponsibility(setId, touchstoneVersionId, scenarioId)
        db.addCoverageSet(touchstoneVersionId, "coverage set name", "vaccine-1", "without", "routine", coverageSetId,
                addVaccine = true)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, coverageSetId, 0)
        db.generateCoverageData(coverageSetId, countryCount = 2, yearRange = 1985..2000 step 5,
                ageRange = 0..20 step 5, testYear = testYear, target = target, coverage = coverage)
    }
}