package org.vaccineimpact.api.blackboxTests.tests

import com.beust.klaxon.json
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.TestUserHelper
import org.vaccineimpact.api.blackboxTests.helpers.validate
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.test_helpers.DatabaseTest
import spark.route.HttpMethod
import java.time.Instant

class ResponsibilityCommentTests : DatabaseTest()
{
    private val now = Instant.now()
    private val modellingGroupId = "group-1"
    private val scenarioId = "scenario-1"

    private fun JooqContext.setupResponsibilities()
    {
        TestUserHelper.setupTestUser()
        addGroup(modellingGroupId)
        addScenarioDescription(scenarioId, "description 1", "disease-1", addDisease = true)
        addTouchstoneVersion("touchstone", 1, addTouchstone = true)
        addResponsibilityComment(
                addResponsibilityInNewSet(modellingGroupId, "touchstone-1", scenarioId),
                "comment 1",
                TestUserHelper.username,
                now
        )
    }

    @Test
    fun `can get comments`()
    {
        validate("/touchstones/touchstone-1/responsibilities/comments/") against "ResponsibilitySetsWithComments" given {
            it.setupResponsibilities()
        } requiringPermissions {
            PermissionSet("*/touchstones.read", "*/responsibilities.review")
        } andCheckArray {
            assertThat(it).isEqualTo(json {
                array(
                        obj(
                                "touchstone_version" to "touchstone-1",
                                "modelling_group_id" to "group-1",
                                "responsibilities" to array(
                                        obj(
                                                "scenario_id" to "scenario-1",
                                                "comment" to obj(
                                                        "comment" to "comment 1",
                                                        "added_by" to "test.user",
                                                        "added_on" to now.toString()
                                                )
                                        )
                                )
                        )
                )
            })
        }
    }

    @Test
    fun `can add comment`()
    {
        validate("/touchstones/touchstone-1/responsibilities/comments/?group_id=$modellingGroupId&scenario_id=$scenarioId&comment=comment%202", HttpMethod.post) given {
            it.setupResponsibilities()
        } withPermissions {
            PermissionSet("*/touchstones.read", "*/responsibilities.review")
        } withRequestSchema "" andCheckString {
            assertThat(it).isEqualTo("OK")
        }
    }
}
