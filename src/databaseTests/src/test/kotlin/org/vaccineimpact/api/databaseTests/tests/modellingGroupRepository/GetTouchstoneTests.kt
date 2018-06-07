package org.vaccineimpact.api.databaseTests.tests.modellingGroupRepository

import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.models.*

class GetTouchstoneTests : ModellingGroupRepositoryTests()
{
    private val diseaseId = "d1"
    private val groupId = "group-1"
    private val touchstoneName = "touchstoneVersion"
    private val touchstoneId = "touchstone-1"

    @Test
    fun `can get touchstones list for modelling group`()
    {
        val groupId2 = "group-2"
        val touchstone2Name = "touchstone-2"
        val touchstone3Name = "touchstone-3"

        given {
            setUpDb(it)

            it.addGroup(groupId2)

            it.addTouchstone(touchstone2Name, 1, addName = true, description = "descr 2", status = "open")
            it.addTouchstone(touchstone3Name, 1, addName = true, description = "descr 3", status = "open")

            addResponsibilitySetWithResponsibility(it, "scenario-1", groupId, touchstoneId, open = true)
            addResponsibilitySetWithResponsibility(it, "scenario-2", groupId, "$touchstone2Name-1", open = true)
            addResponsibilitySetWithResponsibility(it, "scenario-3", groupId2, "$touchstone3Name-1", open = true)

        } check { repo ->
            val touchstones = repo.getTouchstoneVersionsByGroupId(groupId)
            assertThat(touchstones).isInstanceOf(List::class.java)
            assertThat(touchstones).hasSize(2)
            assertThat(touchstones[0])
                    .isEqualTo(TouchstoneVersion(touchstoneId, "touchstone", 1, "descr 1", TouchstoneStatus.OPEN))
            assertThat(touchstones[1])
                    .isEqualTo(TouchstoneVersion("$touchstone2Name-1", touchstone2Name, 1, "descr 2", TouchstoneStatus.OPEN)
            )

        }
    }

    @Test
    fun `does not return open touchstone with no responsibilities`()
    {
        given {
            setUpDb(it)

        } check { repo ->
            val touchstones = repo.getTouchstoneVersionsByGroupId(groupId)
            assertThat(touchstones).hasSize(0)
        }
    }

    @Test
    fun `does not return finished touchstone with no responsibilities`()
    {
        given {
            setUpDb(it, touchstoneStatus = "finished")

        } check { repo ->
            val touchstones = repo.getTouchstoneVersionsByGroupId(groupId)
            assertThat(touchstones).hasSize(0)
        }
    }

    @Test
    fun `does not return open touchstone with closed responsibilities`()
    {
        val scenarioId = "scenario-1"

        given {
            setUpDb(it)
            addResponsibilitySetWithResponsibility(it, scenarioId, groupId, touchstoneId, open = false)

        } check { repo ->
            val touchstones = repo.getTouchstoneVersionsByGroupId(groupId)
            assertThat(touchstones).hasSize(0)
        }
    }

    @Test
    fun `does return finished touchstone with closed responsibilities`()
    {
        val scenarioId = "scenario-1"

        given {
            setUpDb(it, touchstoneStatus = "finished")
            addResponsibilitySetWithResponsibility(it, scenarioId, groupId, touchstoneId, open = false)

        } check { repo ->
            val touchstones = repo.getTouchstoneVersionsByGroupId(groupId)
            assertThat(touchstones).hasSize(1)
        }
    }

    @Test
    fun `does return in prep touchstone with closed responsibilities`()
    {
        val scenarioId = "scenario-1"

        given {
            setUpDb(it, touchstoneStatus = "in-preparation")
            addResponsibilitySetWithResponsibility(it, scenarioId, groupId, touchstoneId, open = false)

        } check { repo ->
            val touchstones = repo.getTouchstoneVersionsByGroupId(groupId)
            assertThat(touchstones).hasSize(1)
        }
    }

    @Test
    fun `exception is thrown if tries to get touchstones by non-existent modelling group ID`()
    {
        withRepo { repo ->
            Assertions.assertThatThrownBy {
                repo.getTouchstoneVersionsByGroupId("bad-id")
            }.isInstanceOf(org.vaccineimpact.api.app.errors.UnknownObjectError::class.java)
        }
    }

    private fun addResponsibilitySetWithResponsibility(db: JooqContext,
                                                       scenarioId: String,
                                                       groupId: String,
                                                       touchstoneId: String,
                                                       open: Boolean = true)
    {
        val setId = db.addResponsibilitySet(groupId, touchstoneId)
        db.addScenarioDescription(scenarioId, "description 1", diseaseId, addDisease = false)
        db.addResponsibility(setId, touchstoneId, scenarioId, open = open)
    }

    private fun setUpDb(db: JooqContext, touchstoneStatus: String = "open")
    {
        db.addDisease(diseaseId)
        db.addGroup(groupId)
        db.addTouchstone(touchstoneName, 1, addName = true, description = "descr 1", status = touchstoneStatus)

    }

}