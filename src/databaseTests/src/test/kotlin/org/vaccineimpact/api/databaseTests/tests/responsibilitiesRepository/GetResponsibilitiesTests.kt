package org.vaccineimpact.api.databaseTests.tests.responsibilitiesRepository

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.filters.ScenarioFilterParameters
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.models.responsibilities.Responsibility
import org.vaccineimpact.api.models.responsibilities.ResponsibilitySetStatus
import org.vaccineimpact.api.models.responsibilities.ResponsibilityStatus

class GetResponsibilitiesTests : ResponsibilitiesRepositoryTests()
{

    @Test
    fun `getResponsibilities checks that touchstone exists`()
    {
        given {
            it.addGroup("group", "description")
        } check { repo ->
            assertThatThrownBy { repo.getResponsibilitiesForGroup("group", "touchstoneVersion", ScenarioFilterParameters()) }
                    .isInstanceOf(UnknownObjectError::class.java)
                    .hasMessageContaining("touchstoneVersion")
        }
    }

    @Test
    fun `getResponsibilities returns empty responsibility set when group has no responsibilities`()
    {
        given {
            it.addGroup("group", "description")
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters())
            assertThat(set.touchstoneVersion).isEqualTo("touchstone-1")
            assertThat(set.modellingGroupId).isEqualTo("group")
            assertThat(set.status).isEqualTo(ResponsibilitySetStatus.NOT_APPLICABLE)
            assertThat(set).isEmpty()
        }
    }

    @Test
    fun `getResponsibilities returns empty responsibility set when group has empty responsibilities`()
    {
        given {
            it.addGroup("group", "description")
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            it.addResponsibilitySet("group", "touchstone-1", "incomplete")
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters())
            assertThat(set.touchstoneVersion).isEqualTo("touchstone-1")
            assertThat(set.modellingGroupId).isEqualTo("group")
            assertThat(set.status).isEqualTo(ResponsibilitySetStatus.INCOMPLETE)
            assertThat(set).isEmpty()
        }
    }

    @Test
    fun `can get responsibilities`()
    {
        given {
            it.addGroup("group", "description")
            it.addScenarioDescription("scenario-1", "description 1", "disease 1", addDisease = true)
            it.addScenarioDescription("scenario-2", "description 2", "disease 2", addDisease = true)
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            val setId = it.addResponsibilitySet("group", "touchstone-1", "submitted")
            it.addResponsibility(setId, "touchstone-1", "scenario-1")
            it.addResponsibility(setId, "touchstone-1", "scenario-2")
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters())
            assertThat(set.touchstoneVersion).isEqualTo("touchstone-1")
            assertThat(set.modellingGroupId).isEqualTo("group")
            assertThat(set.status).isEqualTo(ResponsibilitySetStatus.SUBMITTED)
            assertThat(set).hasSameElementsAs(listOf(
                    Responsibility(
                            Scenario("scenario-1", "description 1", "disease 1", listOf("touchstone-1")),
                            ResponsibilityStatus.EMPTY,
                            emptyList(),
                            null
                    ),
                    Responsibility(
                            Scenario("scenario-2", "description 2", "disease 2", listOf("touchstone-1")),
                            ResponsibilityStatus.EMPTY,
                            emptyList(),
                            null
                    )
            ))
        }
    }

    @Test
    fun `can get responsibilities for touchstone`()
    {
        given {
            it.addGroup("group", "description")
            it.addGroup("group2", "description")
            it.addScenarioDescription("scenario-1", "description 1", "disease 1", addDisease = true)
            it.addScenarioDescription("scenario-2", "description 2", "disease 2", addDisease = true)
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            val setId = it.addResponsibilitySet("group", "touchstone-1", "submitted")
            it.addResponsibility(setId, "touchstone-1", "scenario-1")
            it.addResponsibility(setId, "touchstone-1", "scenario-2")

            it.addResponsibilitySet("group2", "touchstone-1", "submitted")

        } check { repo ->
            val sets = repo.getResponsibilitiesForTouchstone( "touchstone-1")
            assertThat(sets.count()).isEqualTo(2)

            val set = sets[0]
            assertThat(set.status).isEqualTo(ResponsibilitySetStatus.SUBMITTED)
            assertThat(set.responsibilities).hasSameElementsAs(listOf(
                    Responsibility(
                            Scenario("scenario-1", "description 1", "disease 1", listOf("touchstone-1")),
                            ResponsibilityStatus.EMPTY,
                            emptyList(),
                            null
                    ),
                    Responsibility(
                            Scenario("scenario-2", "description 2", "disease 2", listOf("touchstone-1")),
                            ResponsibilityStatus.EMPTY,
                            emptyList(),
                            null
                    )
            ))

            assertThat(sets[1].responsibilities.count()).isEqualTo(0)
        }
    }

    @Test
    fun `only gets open responsibilities`()
    {
        given {
            it.addGroup("group", "description")
            it.addScenarioDescription("scenario-1", "description 1", "disease 1", addDisease = true)
            it.addScenarioDescription("scenario-2", "description 2", "disease 2", addDisease = true)
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            val setId = it.addResponsibilitySet("group", "touchstone-1", "submitted")
            it.addResponsibility(setId, "touchstone-1", "scenario-1")
            it.addResponsibility(setId, "touchstone-1", "scenario-2", open = false)
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters())
            assertThat(set).hasSameElementsAs(listOf(
                    Responsibility(
                            Scenario("scenario-1", "description 1", "disease 1", listOf("touchstone-1")),
                            ResponsibilityStatus.EMPTY,
                            emptyList(),
                            null
                    )
            ))
        }
    }

    @Test
    fun `returns burden estimate`()
    {
        var burdenEstimateId = 0
        given {
            it.addUserForTesting("test.user")
            it.addGroup("group", "description")
            it.addScenarioDescription("scenario-1", "description 1", "disease 1", addDisease = true)
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            val setId = it.addResponsibilitySet("group", "touchstone-1", "submitted")
            val responsibilityId = it.addResponsibility(setId, "touchstone-1", "scenario-1")
            it.addModel("model-1", "group", "disease 1")
            val version = it.addModelVersion("model-1", "version-1")
            burdenEstimateId = it.addBurdenEstimateSet(responsibilityId, version, "test.user", filename = "file.csv")
            it.updateCurrentEstimate(responsibilityId, burdenEstimateId)
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters())
            assertThat(set.responsibilities.first().currentEstimateSet!!.id).isEqualTo(burdenEstimateId)
            assertThat(set.responsibilities.first().currentEstimateSet!!.originalFilename).isEqualTo("file.csv")
            assertThat(set.responsibilities.first().status).isEqualTo(ResponsibilityStatus.VALID)
        }
    }

    @Test
    fun `responsibility status is invalid if burden estimate has problems`()
    {
        var burdenEstimateId = 0
        given {
            it.addUserForTesting("test.user")
            it.addGroup("group", "description")
            it.addScenarioDescription("scenario-1", "description 1", "d1", addDisease = true)
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            val setId = it.addResponsibilitySet("group", "touchstone-1", "submitted")
            val responsibilityId = it.addResponsibility(setId, "touchstone-1", "scenario-1")
            it.addModel("model-1", "group", "d1")
            val version = it.addModelVersion("model-1", "version-1")
            burdenEstimateId = it.addBurdenEstimateSet(responsibilityId, version, "test.user")
            it.updateCurrentEstimate(responsibilityId, burdenEstimateId)
            it.addBurdenEstimateProblem("problem", burdenEstimateId)
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters())
            assertThat(set.responsibilities.first().currentEstimateSet!!.id).isEqualTo(burdenEstimateId)
            assertThat(set.responsibilities.first().status).isEqualTo(ResponsibilityStatus.INVALID)
            assertThat(set.responsibilities.first().problems).hasSameElementsAs(listOf("problem"))
        }
    }

    @Test
    fun `responsibilities from other groups are not returned`()
    {
        given {
            it.addGroup("group1", "description")
            it.addGroup("group2", "description")
            it.addScenarioDescription("scenario-1", "description 1", "disease 1", addDisease = true)
            it.addScenarioDescription("scenario-2", "description 2", "disease 2", addDisease = true)
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            val group1Set = it.addResponsibilitySet("group1", "touchstone-1", "submitted")
            val group2Set = it.addResponsibilitySet("group2", "touchstone-1", "incomplete")
            it.addResponsibility(group1Set, "touchstone-1", "scenario-1")
            it.addResponsibility(group2Set, "touchstone-1", "scenario-2")
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group1", "touchstone-1", ScenarioFilterParameters())
            assertThat(set.touchstoneVersion).isEqualTo("touchstone-1")
            assertThat(set.modellingGroupId).isEqualTo("group1")
            assertThat(set.status).isEqualTo(ResponsibilitySetStatus.SUBMITTED)
            assertThat(set.responsibilities).hasSameElementsAs(listOf(
                    Responsibility(
                            Scenario("scenario-1", "description 1", "disease 1", listOf("touchstone-1")),
                            ResponsibilityStatus.EMPTY,
                            emptyList(),
                            null
                    )
            ))
        }
    }

    @Test
    fun `responsibilities from other touchstones are not returned`()
    {
        given {
            it.addGroup("group", "description")
            it.addScenarioDescription("scenario-1", "description 1", "disease 1", addDisease = true)
            it.addScenarioDescription("scenario-2", "description 2", "disease 2", addDisease = true)
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            it.addTouchstoneVersion("touchstone", 2, "description", "open")
            val set1 = it.addResponsibilitySet("group", "touchstone-1", "submitted")
            val set2 = it.addResponsibilitySet("group", "touchstone-2", "incomplete")

            // Both scenarios appear in both touchstones - but the group is only responsible for
            // scenario 1 in touchstoneVersion 1, and scenario 2 in touchstoneVersion 2
            it.addResponsibility(set1, "touchstone-1", "scenario-1")
            it.addResponsibility(set2, "touchstone-2", "scenario-2")
            it.addScenarioToTouchstone("touchstone-1", "scenario-2")
            it.addScenarioToTouchstone("touchstone-2", "scenario-1")
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters())
            assertThat(set.touchstoneVersion).isEqualTo("touchstone-1")
            assertThat(set.modellingGroupId).isEqualTo("group")
            assertThat(set.status).isEqualTo(ResponsibilitySetStatus.SUBMITTED)
            assertThat(set).hasSameElementsAs(listOf(
                    Responsibility(
                            Scenario("scenario-1", "description 1", "disease 1", listOf(
                                    "touchstone-1",
                                    "touchstone-2"
                            )),
                            ResponsibilityStatus.EMPTY,
                            emptyList(),
                            null
                    )
            ))
        }
    }

    @Test
    fun `can filter responsibilities by disease`()
    {
        given {
            it.addGroup("group", "description")
            it.addScenarioDescription("scenario-1", "description 1", "disease 1", addDisease = true)
            it.addScenarioDescription("scenario-2", "description 2", "disease 2", addDisease = true)
            it.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
            val setId = it.addResponsibilitySet("group", "touchstone-1", "submitted")
            it.addResponsibility(setId, "touchstone-1", "scenario-1")
            it.addResponsibility(setId, "touchstone-1", "scenario-2")
        } check { repo ->
            val set = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters())
            assertThat(set).hasSize(2)
            val filteredSet = repo.getResponsibilitiesForGroup("group", "touchstone-1", ScenarioFilterParameters(
                    disease = "disease 2"
            ))
            assertThat(filteredSet).hasSameElementsAs(listOf(
                    Responsibility(
                            Scenario("scenario-2", "description 2", "disease 2", listOf("touchstone-1")),
                            ResponsibilityStatus.EMPTY,
                            emptyList(),
                            null
                    )
            ))
        }
    }
}