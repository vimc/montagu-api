package org.vaccineimpact.api.blackboxTests.tests.BurdenEstimates

import com.beust.klaxon.JsonObject
import com.beust.klaxon.json
import com.opencsv.CSVReader
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.TestUserHelper
import org.vaccineimpact.api.blackboxTests.helpers.validate
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.blackboxTests.helpers.RequestHelper
import org.vaccineimpact.api.blackboxTests.tests.AuthenticationTests.Companion.url
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.models.Outcome
import org.vaccineimpact.api.validateSchema.JSONValidator
import java.io.StringReader


class RetrieveBurdenEstimateTests : BurdenEstimateTests()
{
    @Test
    fun `can get burden estimate sets`()
    {
        validate(setUrl) against "BurdenEstimates" given { db ->
            val ids = setUp(db)
            db.addUserForTesting("some.user")
            val setId = db.addBurdenEstimateSet(ids.responsibilityId, ids.modelVersionId, "some.user",
                    setType = "central-averaged", setTypeDetails = "mean", filename = "file.csv")
            db.addBurdenEstimateProblem("a problem", setId)
        } requiringPermissions {
            PermissionSet(
                    "$groupScope/estimates.read",
                    "$groupScope/responsibilities.read"
            )
        } andCheckArray { data ->
            val obj = data.first() as JsonObject
            assertThat(obj["uploaded_by"]).isEqualTo("some.user")
            assertThat(obj["problems"]).isEqualTo(json {
                array("a problem")
            })
            assertThat(obj["type"]).isEqualTo(json {
                obj("type" to "central-averaged", "details" to "mean")
            })
            assertThat(obj["original_filename"]).isEqualTo("file.csv")
        }
    }

    @Test
    fun `can get single burden estimate set`()
    {
        validate("${setUrl}1/") against "BurdenEstimateSet" given { db ->
            val ids = setUp(db)
            db.addUserForTesting("some.user")
            val setId = db.addBurdenEstimateSet(ids.responsibilityId, ids.modelVersionId, "some.user",
                    setType = "central-averaged", setTypeDetails = "mean", filename = "file.csv")
            db.addBurdenEstimateProblem("a problem", setId)
        } requiringPermissions {
            PermissionSet(
                    "$groupScope/estimates.read",
                    "$groupScope/responsibilities.read"
            )
        } andCheck { data ->
            assertThat(data["uploaded_by"]).isEqualTo("some.user")
            assertThat(data["problems"]).isEqualTo(json {
                array("a problem")
            })
            assertThat(data["type"]).isEqualTo(json {
                obj("type" to "central-averaged", "details" to "mean")
            })
            assertThat(data["original_filename"]).isEqualTo("file.csv")
        }
    }

    @Test
    fun `can get burden estimate set with no original file`()
    {
        validate("${setUrl}1/") against "BurdenEstimateSet" given { db ->
            val ids = setUp(db)
            db.addUserForTesting("some.user")
            val setId = db.addBurdenEstimateSet(ids.responsibilityId, ids.modelVersionId, "some.user",
                    setType = "central-averaged", setTypeDetails = "mean", filename = null)
            db.addBurdenEstimateProblem("a problem", setId)
        } requiringPermissions {
            PermissionSet(
                    "$groupScope/estimates.read",
                    "$groupScope/responsibilities.read"
            )
        } andCheck { data ->
            assertThat(data["uploaded_by"]).isEqualTo("some.user")
            assertThat(data["problems"]).isEqualTo(json {
                array("a problem")
            })
            assertThat(data["type"]).isEqualTo(json {
                obj("type" to "central-averaged", "details" to "mean")
            })

            // set to do not serialise if null, so this field should simply be missing
            assertThat(data.containsKey("original_filename")).isFalse()
        }
    }

    @Test
    fun `can get burden estimate data`()
    {
        JooqContext().use {

            val outcomes = listOf(
                Outcome("hepb_chronic_symptomatic_in_acute_phase", "cases name"),
                Outcome("hepb_deaths_acute", "dalys name"),
                Outcome("dalys", "deaths name")
            )

            val setId = setUpWithBurdenEstimateSet(it, expectedOutcomes = outcomes)
            it.addCountries(listOf("ABC", "DEF"))
            it.addBurdenEstimate(setId, "ABC", year = 2000, age = 20, outcome = "cohort_size", value = 100F)
            it.addBurdenEstimate(setId, "ABC", year = 2000, age = 20, outcome = "hepb_chronic_symptomatic_in_acute_phase", value = 40F)
            it.addBurdenEstimate(setId, "ABC", year = 2000, age = 20, outcome = "dalys", value = 25.5f)
            it.addBurdenEstimate(setId, "ABC", year = 2000, age = 20, outcome = "hepb_deaths_acute", value = 2F)

            it.addBurdenEstimate(setId, "DEF", year = 2000, age = 20, outcome = "hepb_chronic_symptomatic_in_acute_phase", value = 10F)
            it.addBurdenEstimate(setId, "DEF", year = 2000, age = 20, outcome = "dalys", value = 5.5f)
            it.addBurdenEstimate(setId, "DEF", year = 2000, age = 20, outcome = "hepb_deaths_acute", value = 1F)
            it.addBurdenEstimate(setId, "DEF", year = 2000, age = 20, outcome = "cohort_size", value = 50F)

            it.addBurdenEstimate(setId, "ABC", year = 2000, age = 21, outcome = "dalys", value = 35.5f)
            it.addBurdenEstimate(setId, "ABC", year = 2000, age = 21, outcome = "cohort_size", value = 200F)
            it.addBurdenEstimate(setId, "ABC", year = 2000, age = 21, outcome = "hepb_chronic_symptomatic_in_acute_phase", value = 80F)
            it.addBurdenEstimate(setId, "ABC", year = 2000, age = 21, outcome = "hepb_deaths_acute", value = 3F)

            it.addBurdenEstimate(setId, "ABC", year = 2001, age = 20, outcome = "hepb_deaths_acute", value = 4F)
            it.addBurdenEstimate(setId, "ABC", year = 2001, age = 20, outcome = "cohort_size", value = 150F)
            it.addBurdenEstimate(setId, "ABC", year = 2001, age = 20, outcome = "hepb_chronic_symptomatic_in_acute_phase", value = 60F)
            it.addBurdenEstimate(setId, "ABC", year = 2001, age = 20, outcome = "dalys", value = 30.5f)
        }

        val estimatesUrl ="${setUrl}1/estimates/"

        val permissions = PermissionSet(
                "*/can-login",
                "$groupScope/estimates.read",
                "$groupScope/responsibilities.read"
        )
        val response = RequestHelper().get(estimatesUrl, permissions, acceptsContentType = "text/csv")

        Assertions.assertThat(response.headers["Content-Type"]).isEqualTo("text/csv");
        Assertions.assertThat(response.headers["Content-Disposition"])
                .isEqualTo("attachment; filename=\"burden_estimates_group-1_touchstone-1_scenario-1.csv\"");

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val firstRow = csv.first().toList() //headers

        Assertions.assertThat(firstRow.count()).isEqualTo(9)
        Assertions.assertThat(firstRow[0]).isEqualTo("disease")
        Assertions.assertThat(firstRow[1]).isEqualTo("year")
        Assertions.assertThat(firstRow[2]).isEqualTo("age")
        Assertions.assertThat(firstRow[3]).isEqualTo("country")
        Assertions.assertThat(firstRow[4]).isEqualTo("country_name")
        Assertions.assertThat(firstRow[5]).isEqualTo("cohort_size")
        Assertions.assertThat(firstRow[6]).isEqualTo("dalys")
        Assertions.assertThat(firstRow[7]).isEqualTo("hepb_deaths_acute")
        Assertions.assertThat(firstRow[8]).isEqualTo("hepb_chronic_symptomatic_in_acute_phase")

        var dataRow = csv.drop(1).first().toList()

        Assertions.assertThat(dataRow.count()).isEqualTo(9)
        Assertions.assertThat(dataRow[0]).isEqualTo("Hib3")
        Assertions.assertThat(dataRow[1]).isEqualTo("2000")
        Assertions.assertThat(dataRow[2]).isEqualTo("20")
        Assertions.assertThat(dataRow[3]).isEqualTo("ABC")
        Assertions.assertThat(dataRow[4]).isEqualTo("ABC-Name")
        Assertions.assertThat(dataRow[5]).isEqualTo("100.0")
        Assertions.assertThat(dataRow[6]).isEqualTo("25.5")
        Assertions.assertThat(dataRow[7]).isEqualTo("2.0")
        Assertions.assertThat(dataRow[8]).isEqualTo("40.0")

        dataRow = csv.drop(2).first().toList()

        Assertions.assertThat(dataRow.count()).isEqualTo(9)
        Assertions.assertThat(dataRow[0]).isEqualTo("Hib3")
        Assertions.assertThat(dataRow[1]).isEqualTo("2000")
        Assertions.assertThat(dataRow[2]).isEqualTo("21")
        Assertions.assertThat(dataRow[3]).isEqualTo("ABC")
        Assertions.assertThat(dataRow[4]).isEqualTo("ABC-Name")
        Assertions.assertThat(dataRow[5]).isEqualTo("200.0")
        Assertions.assertThat(dataRow[6]).isEqualTo("35.5")
        Assertions.assertThat(dataRow[7]).isEqualTo("3.0")
        Assertions.assertThat(dataRow[8]).isEqualTo("80.0")

        dataRow = csv.drop(3).first().toList()

        Assertions.assertThat(dataRow.count()).isEqualTo(9)
        Assertions.assertThat(dataRow[0]).isEqualTo("Hib3")
        Assertions.assertThat(dataRow[1]).isEqualTo("2001")
        Assertions.assertThat(dataRow[2]).isEqualTo("20")
        Assertions.assertThat(dataRow[3]).isEqualTo("ABC")
        Assertions.assertThat(dataRow[4]).isEqualTo("ABC-Name")
        Assertions.assertThat(dataRow[5]).isEqualTo("150.0")
        Assertions.assertThat(dataRow[6]).isEqualTo("30.5")
        Assertions.assertThat(dataRow[7]).isEqualTo("4.0")
        Assertions.assertThat(dataRow[8]).isEqualTo("60.0")

        dataRow = csv.drop(4).first().toList()

        Assertions.assertThat(dataRow.count()).isEqualTo(9)
        Assertions.assertThat(dataRow[0]).isEqualTo("Hib3")
        Assertions.assertThat(dataRow[1]).isEqualTo("2000")
        Assertions.assertThat(dataRow[2]).isEqualTo("20")
        Assertions.assertThat(dataRow[3]).isEqualTo("DEF")
        Assertions.assertThat(dataRow[4]).isEqualTo("DEF-Name")
        Assertions.assertThat(dataRow[5]).isEqualTo("50.0")
        Assertions.assertThat(dataRow[6]).isEqualTo("5.5")
        Assertions.assertThat(dataRow[7]).isEqualTo("1.0")
        Assertions.assertThat(dataRow[8]).isEqualTo("10.0")
    }

    @Test
    fun `getting burden estimate set for non-existent group returns a 404`()
    {
        JooqContext().use {

            setUpWithBurdenEstimateSet(it)
        }

        val estimatesUrl ="/modelling-groups/NOTAGROUP/responsibilities/$touchstoneVersionId/$scenarioId/estimate-sets/1/"
        val permissions = PermissionSet(
                "*/can-login",
                "*/estimates.read",
                "*/responsibilities.read"
        )

        val response = RequestHelper().get(estimatesUrl, permissions)

        Assertions.assertThat(response.statusCode).isEqualTo(404)
        JSONValidator().validateError(response.text, "unknown-modelling-group")
    }

    @Test
    fun `getting burden estimate set for non-existent group returns a 403 where group-scoped permissions`()
    {
        JooqContext().use {

            setUpWithBurdenEstimateSet(it)
        }

        val estimatesUrl ="/modelling-groups/NOTAGROUP/responsibilities/$touchstoneVersionId/$scenarioId/estimate-sets/1/"
        val permissions = PermissionSet(
                "*/can-login",
                "$groupScope/estimates.read",
                "$groupScope/responsibilities.read"
        )

        val response = RequestHelper().get(estimatesUrl, permissions)

        Assertions.assertThat(response.statusCode).isEqualTo(403)
    }

    @Test
    fun `getting burden estimate set for non-existent scenario returns a 404`()
    {
        JooqContext().use {

            setUpWithBurdenEstimateSet(it)
        }

        val estimatesUrl ="/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/NOTASCENARIO/estimate-sets/1/"
        val permissions = PermissionSet(
                "*/can-login",
                "*/estimates.read",
                "*/responsibilities.read"
        )

        val response = RequestHelper().get(estimatesUrl, permissions)

        Assertions.assertThat(response.statusCode).isEqualTo(404)
        JSONValidator().validateError(response.text, "unknown-scenario-description")
    }


    @Test
    fun `getting nonexistent burden estimate set returns a 404`()
    {
        val url = "${setUrl}99/"
        val permissions = PermissionSet(
                "*/can-login",
                "$groupScope/estimates.read",
                "$groupScope/responsibilities.read"
        )

        JooqContext().use {
            setUpWithBurdenEstimateSet(it)
        }

        val response = RequestHelper().get(url, permissions)

        Assertions.assertThat(response.statusCode).isEqualTo(404)
        JSONValidator().validateError(response.text, "unknown-burden-estimate-set")
    }

    @Test
    fun `getting burden estimate set data for non-existent scenario returns a 404`()
    {
        JooqContext().use {

            setUpWithBurdenEstimateSet(it)
        }

        val estimatesUrl ="/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/NOTASCENARIO/estimate-sets/1/estimates/"
        val permissions = PermissionSet(
                "*/can-login",
                "*/estimates.read",
                "*/responsibilities.read"
        )

        val response = RequestHelper().get(estimatesUrl, permissions, acceptsContentType= "text/csv")

        Assertions.assertThat(response.statusCode).isEqualTo(404)
        JSONValidator().validateError(response.text, "unknown-scenario-description")
    }

    @Test
    fun `getting burden estimate set data for non-existent touchstone returns a 404`()
    {
        JooqContext().use {

            setUpWithBurdenEstimateSet(it)
        }

        val estimatesUrl ="/modelling-groups/$groupId/responsibilities/NOTATOUCHSTONE/$scenarioId/estimate-sets/1/estimates/"
        val permissions = PermissionSet(
                "*/can-login",
                "*/estimates.read",
                "*/responsibilities.read"
        )

        val response = RequestHelper().get(estimatesUrl, permissions, acceptsContentType= "text/csv")

        Assertions.assertThat(response.statusCode).isEqualTo(404)
        JSONValidator().validateError(response.text, "unknown-touchstone-version")
    }

    @Test
    fun `getting nonexistent burden estimate set data returns a 404`()
    {
        val url = "${setUrl}99/estimates/"
        val permissions = PermissionSet(
                "*/can-login",
                "$groupScope/estimates.read",
                "$groupScope/responsibilities.read"
        )

        JooqContext().use {
            setUpWithBurdenEstimateSet(it)
        }

        val response = RequestHelper().get(url, permissions, acceptsContentType = "text/csv")

        Assertions.assertThat(response.statusCode).isEqualTo(404)
        JSONValidator().validateError(response.text, "unknown-burden-estimate-set")
    }

    @Test
    fun `getting data for stochastic burden estimate set returns a 400`()
    {
        JooqContext().use {

            setUpWithBurdenEstimateSet(it, setType="stochastic")
        }

        val estimatesUrl ="${setUrl}1/estimates/"
        val permissions = PermissionSet(
                "*/can-login",
                "$groupScope/estimates.read",
                "$groupScope/responsibilities.read"
        )

        val response = RequestHelper().get(estimatesUrl, permissions, acceptsContentType = "text/csv")

        Assertions.assertThat(response.statusCode).isEqualTo(400)
    }

    @Test
    fun `can get estimated deaths for scenario`()
    {
        validate("$setUrl/1/estimates/deaths/") against "ChartSeries" given { db ->
            val returnedIds = setUp(db)
            TestUserHelper.setupTestUser()

            db.addExpectations(returnedIds.responsibilityId, yearMinInclusive = 1996, yearMaxInclusive = 1997,
                    ageMaxInclusive = 50, ageMinInclusive = 50, countries = listOf("AFG", "AGO"))
            db.addBurdenEstimateSet(
                    returnedIds.responsibilityId,
                    returnedIds.modelVersionId,
                    TestUserHelper.username,
                    status = "complete",
                    setId = 1)
            db.updateCurrentEstimate(returnedIds.responsibilityId, 1)
            db.addBurdenEstimate(1, "AFG", age = 50, year = 1996, value = 100F, outcome = "deaths")
        } requiringPermissions {
            PermissionSet(
                    "$groupScope/estimates.read",
                    "$groupScope/responsibilities.read"
            )
        } andCheck { data ->
            assertThat(data["50"]).isEqualTo(json {
                array(obj("x" to 1996, "y" to 100.0))
            })
        }
    }

    @Test
    fun `can get estimated cases for scenario`()
    {
        validate("$setUrl/1/estimates/cases/") against "ChartSeries" given { db ->
            val returnedIds = setUp(db)
            TestUserHelper.setupTestUser()

            db.addExpectations(returnedIds.responsibilityId, yearMinInclusive = 1996, yearMaxInclusive = 1997,
                    ageMaxInclusive = 50, ageMinInclusive = 50, countries = listOf("AFG", "AGO"))
            db.addBurdenEstimateSet(
                    returnedIds.responsibilityId,
                    returnedIds.modelVersionId,
                    TestUserHelper.username,
                    status = "complete",
                    setId = 1)
            db.updateCurrentEstimate(returnedIds.responsibilityId, 1)
            db.addBurdenEstimate(1, "AFG", age = 50, year = 1996, value = 100F, outcome = "cases")
        } requiringPermissions {
            PermissionSet(
                    "$groupScope/estimates.read",
                    "$groupScope/responsibilities.read"
            )
        } andCheck { data ->
            assertThat(data["50"]).isEqualTo(json {
                array(obj("x" to 1996, "y" to 100.0))
            })
        }
    }

}