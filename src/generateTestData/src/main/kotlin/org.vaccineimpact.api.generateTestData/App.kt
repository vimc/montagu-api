package org.vaccineimpact.api.generateTestData

import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.*

fun main(args: Array<String>)
{
    JooqContext().use { db ->
        db.addDisease("YF", "Yellow Fever")
        db.addVaccine("YF", "Yellow Fever")

        db.addScenarioDescription("yf-routine", "Yellow Fever, routine", "YF")
        db.addScenarioDescription("yf-campaign", "Yellow Fever, campaign", "YF")

        db.addTouchstone("op-2017", "Operational Forecast 2017")
        db.addTouchstoneVersion("op-2017", 1, "Operational Forecast 2017 (v1)", "finished")
        db.addTouchstoneVersion("op-2017", 2, "Operational Forecast 2017 (v2)", "open")

        val demographicTestData = DemographicTestData(db)
        demographicTestData.generate("op-2017-1", listOf("YF"))
        demographicTestData.generate("op-2017-2", listOf("YF"))

        val yfRoutine = db.addScenarioToTouchstone("op-2017-2", "yf-routine")
        val yfCampaign = db.addScenarioToTouchstone("op-2017-2", "yf-campaign")

        val yfNoVacc = db.addCoverageSet("op-2017-2", "Yellow Fever, no vaccination", "YF", "none", "none")
        val yfRoutineWithout = db.addCoverageSet("op-2017-2", "Yellow Fever, routine, without GAVI", "YF", "without", "routine")
        val yfRoutineWith = db.addCoverageSet("op-2017-2", "Yellow Fever, routine, with GAVI", "YF", "with", "routine")
        val yfCampaignWithout = db.addCoverageSet("op-2017-2", "Yellow Fever, campaign, without GAVI", "YF", "without", "campaign")
        val yfCampaignWith = db.addCoverageSet("op-2017-2", "Yellow Fever, campaign, with GAVI", "YF", "with", "campaign")
        db.generateCoverageData(yfNoVacc)
        db.generateCoverageData(yfRoutineWithout)
        db.generateCoverageData(yfRoutineWith)
        db.generateCoverageData(yfCampaignWithout)
        db.generateCoverageData(yfCampaignWith)

        db.addCoverageSetToScenario("yf-routine", "op-2017-2", yfNoVacc, 0)
        db.addCoverageSetToScenario("yf-routine", "op-2017-2", yfRoutineWithout, 1)
        db.addCoverageSetToScenario("yf-routine", "op-2017-2", yfRoutineWith, 2)
        db.addCoverageSetToScenario("yf-campaign", "op-2017-2", yfNoVacc, 0)
        db.addCoverageSetToScenario("yf-campaign", "op-2017-2", yfRoutineWithout, 1)
        db.addCoverageSetToScenario("yf-campaign", "op-2017-2", yfRoutineWith, 2)
        db.addCoverageSetToScenario("yf-campaign", "op-2017-2", yfCampaignWithout, 3)
        db.addCoverageSetToScenario("yf-campaign", "op-2017-2", yfCampaignWith, 4)

        db.addGroup("IC-Garske", "Imperial Yellow Fever modelling group")
        db.addGroup("IC-Imaginary", "Imperial speculative modelling group")
        db.addModel("yf-model", "IC-Garske", "YF", "Yellow Fever Model", versions = listOf("v1"))

        val setId = db.addResponsibilitySet("IC-Garske", "op-2017-2", "incomplete")
        val responsibilityId = db.addResponsibility(setId, yfRoutine)
        val secondResponsibilityId = db.addResponsibility(setId, yfCampaign)

        db.addExpectations(responsibilityId, countries = db.fetchCountries(2), outcomes = db.fetchOutcomes(2))
        db.addExpectations(secondResponsibilityId, countries = db.fetchCountries(96), outcomes = db.fetchOutcomes(1))

        db.addUserForTesting("test.user")
    }
}