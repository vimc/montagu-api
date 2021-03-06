package org.vaccineimpact.api.blackboxTests.tests

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.json
import com.opencsv.CSVReader
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.*
import org.vaccineimpact.api.blackboxTests.schemas.CSVSchema
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.Tables
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.db.fromJoinPath
import org.vaccineimpact.api.models.Outcome
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.test_helpers.DatabaseTest
import org.vaccineimpact.api.validateSchema.JSONValidator
import java.io.StringReader

class ResponsibilityTests : DatabaseTest()
{
    private val touchstoneVersionId = "touchstone-1"
    private val groupId = "awesome-group"
    private val groupScope = "modelling-group:$groupId"
    private val scenarioId = "yf-scenario"
    private val modelId = "model-1"

    val responsibilitiesUrl = "/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/"
    val responsibilityUrl = "/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/$scenarioId/"

    val outcomes = listOf(Outcome("test_deaths", "test deaths name"),
                          Outcome("test_dalys", "test dalys name"))

    @Test
    fun `getResponsibilities matches schema`()
    {
        validate(responsibilitiesUrl) against "ResponsibilitySet" given {
            addResponsibilities(it, touchstoneStatus = "open")
        } requiringPermissions {
            PermissionSet("$groupScope/responsibilities.read", "*/scenarios.read")
        } andCheck {
            assertThat(it["touchstone_version"]).isEqualTo(touchstoneVersionId)
            assertThat(it["modelling_group_id"]).isEqualTo(groupId)
            assertThat(it["status"]).isEqualTo("submitted")

            @Suppress("UNCHECKED_CAST")
            val responsibilities = it["responsibilities"] as JsonArray<JsonObject>
            val responsibility = responsibilities[0]
            val scenario = responsibility["scenario"]
            assertThat(scenario).isEqualTo(json {
                obj(
                        "id" to scenarioId,
                        "description" to
                                "description 1",
                        "disease" to "disease-1",
                        "touchstones" to array("touchstone-1")
                )
            })
            assertThat(responsibility["status"]).isEqualTo("invalid")
            assertThat(responsibility["problems"]).isEqualTo(json { array("problem") })
            assertThat(responsibility["current_estimate_set"]).isNotNull()
        }
    }

    @Test
    fun `cannot getResponsibilities if not a member of modelling group, and does not have global group read permission`()
    {
        val permissions = PermissionSet(
                "*/can-login",
                "*/touchstones.read",
                "$groupScope/responsibilities.read"
        )

        TestUserHelper.setupTestUser()


        val otherGroupId = "other-group"
        JooqContext().use {
            //Create standard responsibilities for test user
           addResponsibilities(it, touchstoneStatus = "open")

            //Create another group with responsibilities, to which the test user does not belong
            it.addGroup(otherGroupId, "another group")
            addResponsibilities(it, touchstoneStatus = "open",
                    includeUserGroupAndTouchstone = false,
                    responsibilityGroupId = otherGroupId,
                    responsibilityModelId = "other-model")
        }

        //sanity check that we can access our own group
        val comparisonUrl = "/modelling-groups/$groupId/responsibilities/"

        val comparisonResponse = RequestHelper().get(comparisonUrl, permissions)

        Assertions.assertThat(comparisonResponse.statusCode).isEqualTo(200)

        val url = "/modelling-groups/$otherGroupId/responsibilities/"

        val response = RequestHelper().get(url, permissions)

        Assertions.assertThat(response.statusCode).isEqualTo(403)
    }

    @Test
    fun `can getResponsibilities if not a member of modelling group, and does have global group read permission`()
    {
        val permissions = PermissionSet(
                "*/can-login",
                "*/touchstones.read",
                "*/responsibilities.read"
        )

        TestUserHelper.setupTestUser()

        val otherGroupId = "other-group"
        JooqContext().use {
            //Create standard responsibilities for test user
            addResponsibilities(it, touchstoneStatus = "open")

            //Create another group with responsibilities, to which the test user does not belong
            it.addGroup(otherGroupId, "another group")
            addResponsibilities(it, touchstoneStatus = "open",
                    includeUserGroupAndTouchstone = false,
                    responsibilityGroupId = otherGroupId,
                    responsibilityModelId = "other-model")
        }

        val url = "/modelling-groups/$otherGroupId/responsibilities/"

        val response = RequestHelper().get(url, permissions)

        Assertions.assertThat(response.statusCode).isEqualTo(200)
    }

    @Test
    fun `getResponsibilities returns expectations`()
    {
        val expectationsId = JooqContext().use { db ->
            TestUserHelper().setupTestUser(db)
            val responsibilityId = addResponsibilities(db, touchstoneStatus = "open", includeExpectations = false)
            db.addExpectations(responsibilityId)
        }
        val permissions = PermissionSet("$groupScope/responsibilities.read", "*/scenarios.read", "*/can-login")
        val response = RequestHelper().get(responsibilitiesUrl, permissions)
        val data = response.montaguData<JsonObject>()!!
        assertThat(data["expectations"]).isEqualTo(json {
            array(
                    obj(
                            "expectation" to obj(
                                    "id" to expectationsId,
                                    "description" to "description",
                                    "years" to obj("minimum_inclusive" to 2000, "maximum_inclusive" to 2100),
                                    "ages" to obj("minimum_inclusive" to 0, "maximum_inclusive" to 99),
                                    "cohorts" to obj("minimum_birth_year" to null, "maximum_birth_year" to null),
                                    "countries" to array(),
                                    "outcomes" to array()
                            ),
                            "applicable_scenarios" to array("yf-scenario"),
                            "disease" to "disease-1"
                    )
            )
        })
    }

    @Test
    fun `not-applicable status is returned when there are no responsibilities`()
    {
        validate(responsibilitiesUrl) against "ResponsibilitySet" given {
            addUserGroupAndTouchstone(it, touchstoneStatus = "open")
        } requiringPermissions {
            PermissionSet("$groupScope/responsibilities.read", "*/scenarios.read")
        } andCheck {
            assertThat(it).isEqualTo(json {
                obj(
                        "touchstone_version" to touchstoneVersionId,
                        "modelling_group_id" to groupId,
                        "status" to "not-applicable",
                        "responsibilities" to array(),
                        "expectations" to array()
                )
            })
        }
    }

    @Test
    fun `can get touchstones by group id`()
    {
        validate("/modelling-groups/$groupId/responsibilities/") against "Touchstones" given {
            addResponsibilities(it, touchstoneStatus = "open")
        } requiringPermissions {
            PermissionSet("*/touchstones.read", "$groupScope/responsibilities.read")
        } andCheckArray {
            assertThat(it).isEqualTo(json {
                array(
                        obj(
                                "id" to "touchstone",
                                "description" to "description",
                                "comment" to "comment",
                                "versions" to array(obj(
                                        "id" to touchstoneVersionId,
                                        "name" to "touchstone",
                                        "version" to 1,
                                        "description" to "version description",
                                        "status" to "open"
                                ))
                        )
                )
            })
        }
    }

    @Test
    fun `only touchstone preparer can see in-preparation responsibilities`()
    {
        val url = "/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/"
        val minimumPermissions = PermissionSet("*/can-login", "$groupScope/responsibilities.read", "*/scenarios.read")
        val permissionUnderTest = "*/touchstones.prepare"
        val checker = PermissionChecker(url, minimumPermissions + permissionUnderTest)
        checker.checkPermissionIsRequired(
                permissionUnderTest,
                expectedProblem = ExpectedProblem("unknown-touchstone-version", touchstoneVersionId),
                given = { addResponsibilities(it, touchstoneStatus = "in-preparation") }
        )
    }

    @Test
    fun `getResponsibility matches schema`()
    {
        validate(responsibilityUrl) against "ResponsibilityDetails" given {
            addResponsibilities(it, touchstoneStatus = "open")
        } requiringPermissions {
            PermissionSet("$groupScope/responsibilities.read", "*/scenarios.read")
        } andCheck {
            val touchstoneVersion = it["touchstone_version"] as JsonObject
            val responsibility = it["responsibility"] as JsonObject
            val scenario = responsibility["scenario"] as JsonObject
            assertThat(touchstoneVersion).isEqualTo(json {
                obj(
                        "id" to touchstoneVersionId,
                        "name" to "touchstone",
                        "version" to 1,
                        "description" to "version description",
                        "status" to "open"
                )
            })

            assertThat(scenario).isEqualTo(json {
                obj(
                        "id" to scenarioId,
                        "description" to "description 1",
                        "disease" to "disease-1",
                        "touchstones" to array("touchstone-1")
                )
            })
            assertThat(responsibility["status"]).isEqualTo("invalid")
            assertThat(responsibility["problems"]).isEqualTo(json { array("problem") })
            assertThat(responsibility["current_estimate_set"]).isNotNull()
        }
    }

    @Test
    fun `can get expectations with responsibility`()
    {
        val expectationsId: Int = JooqContext().use { db ->
            val responsibilityId = addResponsibilities(db, touchstoneStatus = "open", includeExpectations = false)
            db.addCountries(listOf("A", "B"))
            val id = db.addExpectations(
                    responsibilityId,
                    yearMinInclusive = 2000, yearMaxInclusive = 2100,
                    ageMinInclusive = 0, ageMaxInclusive = 99,
                    cohortMinInclusive = null, cohortMaxInclusive = 2050,
                    countries = listOf("A", "B"),
                    outcomes = outcomes
            )
            TestUserHelper().setupTestUser(db)
            id
        }
        val permissions = PermissionSet("$groupScope/responsibilities.read", "*/scenarios.read", "*/can-login")
        val response = RequestHelper().get(responsibilityUrl, permissions)
        val data = response.montaguData<JsonObject>()!!
        assertThat(data["expectations"]).isEqualTo(json {
            obj(
                    "id" to expectationsId,
                    "description" to "description",
                    "years" to obj("minimum_inclusive" to 2000, "maximum_inclusive" to 2100),
                    "ages" to obj("minimum_inclusive" to 0, "maximum_inclusive" to 99),
                    "cohorts" to obj("minimum_birth_year" to null, "maximum_birth_year" to 2050),
                    "countries" to array(
                            obj("id" to "A", "name" to "A-Name"),
                            obj("id" to "B", "name" to "B-Name")
                    ),
                    "outcomes" to array(
                            obj("code" to "test_deaths", "name" to "test deaths name"),
                            obj("code" to "test_dalys", "name" to "test dalys name")
                    )
            )
        })
    }

    @Test
    fun `only touchstone-preparer can see in-preparation responsibility`()
    {
        val url = "/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/$scenarioId/"
        val minimumPermissions = PermissionSet("*/can-login", "$groupScope/responsibilities.read", "*/scenarios.read")
        val permissionUnderTest = "*/touchstones.prepare"
        val checker = PermissionChecker(url, minimumPermissions + permissionUnderTest)
        checker.checkPermissionIsRequired(
                permissionUnderTest,
                expectedProblem = ExpectedProblem("unknown-touchstone-version", touchstoneVersionId),
                given = { addResponsibilities(it, touchstoneStatus = "in-preparation") }
        )
    }

    @Test
    fun `get coverage sets matches schema`()
    {
        val coverageSetId = 1
        validate("/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/$scenarioId/coverage-sets/") against "ScenarioAndCoverageSets" given {
            addResponsibilities(it, touchstoneStatus = "open")
            it.addCoverageSet(touchstoneVersionId, "coverage set name", "vaccine-1", "without", "routine", coverageSetId,
                    addVaccine = true)
            it.addCoverageSetToScenario(scenarioId, touchstoneVersionId, coverageSetId, 0)
        } requiringPermissions {
            PermissionSet("*/scenarios.read", "$groupScope/responsibilities.read", "$groupScope/coverage.read")
        } andCheck {
            assertThat(it).isEqualTo(json {
                obj(
                        "touchstone_version" to obj(
                                "id" to touchstoneVersionId,
                                "name" to "touchstone",
                                "version" to 1,
                                "description" to "version description",
                                "status" to "open"
                        ),
                        "scenario" to obj(
                                "id" to scenarioId,
                                "touchstones" to array(touchstoneVersionId),
                                "description" to "description 1",
                                "disease" to "disease-1"
                        ),
                        "coverage_sets" to array(obj(
                                "id" to coverageSetId,
                                "touchstone_version" to touchstoneVersionId,
                                "name" to "coverage set name",
                                "vaccine" to "vaccine-1",
                                "gavi_support" to "no gavi",
                                "activity_type" to "routine"
                        ))
                )
            })
        }
    }

    @Test
    fun `get central template has correct headers`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()
        var id = 0
        JooqContext().use {
            val responsibilityId = addResponsibilities(it, touchstoneStatus = "open")
            id = it.addExpectations(responsibilityId, outcomes = outcomes)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("/modelling-groups/$groupId/expectations/$touchstoneVersionId/$id/",
                PermissionSet("*/can-login", "*/scenarios.read", "$groupScope/responsibilities.read"), acceptsContentType = "text/csv")

        val schema = CSVSchema("BurdenEstimate")
        schema.validate(response.text)
    }

    @Test
    fun `get stochastic template has correct headers`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()
        var id = 0
        JooqContext().use {
            val responsibilityId = addResponsibilities(it, touchstoneStatus = "open")
            id = it.addExpectations(responsibilityId, outcomes = outcomes)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("/modelling-groups/$groupId/expectations/$touchstoneVersionId/$id/?type=stochastic",
                PermissionSet("*/can-login", "*/scenarios.read", "$groupScope/responsibilities.read"), acceptsContentType = "text/csv")

        val schema = CSVSchema("StochasticBurdenEstimate")
        schema.validate(response.text)
    }

    @Test
    fun `only touchstone preparer can see in-preparation coverage sets`()
    {
        val url = "/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/$scenarioId/coverage-sets/"
        val minimumPermissions = PermissionSet("*/can-login", "*/scenarios.read", "$groupScope/responsibilities.read", "$groupScope/coverage.read")
        val permissionUnderTest = "*/touchstones.prepare"
        val checker = PermissionChecker(url, minimumPermissions + permissionUnderTest)
        checker.checkPermissionIsRequired(
                permissionUnderTest,
                expectedProblem = ExpectedProblem("unknown-touchstone-version", touchstoneVersionId),
                given = { addResponsibilities(it, touchstoneStatus = "in-preparation") }
        )
    }

    @Test
    fun `can get responsibilities for touchstone`()
    {
        validate("/touchstones/$touchstoneVersionId/responsibilities/") against "ResponsibilitySets" given {
            addResponsibilities(it, touchstoneStatus = "open")
        } requiringPermissions {
            PermissionSet("*/touchstones.read", "*/responsibilities.read", "*/scenarios.read")
        } andCheckArray {
            val responsibilitySet = it[0] as JsonObject
            assertThat(responsibilitySet["touchstone_version"]).isEqualTo(touchstoneVersionId)
            assertThat(responsibilitySet["status"]).isEqualTo("submitted")
            assertThat(responsibilitySet["modelling_group_id"]).isEqualTo(groupId)
            assertThat(responsibilitySet["expectations"]).isNotNull

            @Suppress("UNCHECKED_CAST")
            val responsibilities = responsibilitySet["responsibilities"] as JsonArray<JsonObject>
            val responsibility = responsibilities[0]
            val scenario = responsibility["scenario"]
            assertThat(scenario).isEqualTo(json {
                obj(
                        "id" to scenarioId,
                        "description" to
                                "description 1",
                        "disease" to "disease-1",
                        "touchstones" to array("touchstone-1")
                )
            })
            assertThat(responsibility["status"]).isEqualTo("invalid")
            assertThat(responsibility["problems"]).isEqualTo(json { array("problem") })
            assertThat(responsibility["current_estimate_set"]).isNotNull()
        }
    }

    private fun addUserGroupAndTouchstone(db: JooqContext, touchstoneStatus: String)
    {
        db.addUserForTesting("model.user")
        db.addGroup(groupId, "description")
        db.addScenarioDescription(scenarioId, "description 1", "disease-1", addDisease = true)
        db.addScenarioDescription("scenario-2", "description 2", "disease-2", addDisease = true)
        db.addTouchstone("touchstone", "description", "comment")
        db.addTouchstoneVersion("touchstone", 1, "version description", touchstoneStatus)
    }

    private fun addResponsibilities(db: JooqContext, touchstoneStatus: String,
                                    includeExpectations: Boolean = true,
                                    includeUserGroupAndTouchstone: Boolean = true,
                                    responsibilityGroupId: String = groupId,
                                    responsibilityModelId: String = modelId): Int
    {
        if (includeUserGroupAndTouchstone) {
            addUserGroupAndTouchstone(db, touchstoneStatus)
        }
        val setId = db.addResponsibilitySet(responsibilityGroupId, touchstoneVersionId, "submitted")
        val responsibilityId = db.addResponsibility(setId, touchstoneVersionId, scenarioId)
        db.addResponsibility(setId, touchstoneVersionId, "scenario-2")
        db.addModel(responsibilityModelId, responsibilityGroupId, "disease-1")
        val version = db.addModelVersion(responsibilityModelId, "version-1")
        val burdenEstimateId = db.addBurdenEstimateSet(responsibilityId, version, "model.user")
        db.updateCurrentEstimate(responsibilityId, burdenEstimateId)
        db.addBurdenEstimateProblem("problem", burdenEstimateId)
        if (includeExpectations)
        {
            db.addExpectations(responsibilityId)
        }

        return responsibilityId
    }

}