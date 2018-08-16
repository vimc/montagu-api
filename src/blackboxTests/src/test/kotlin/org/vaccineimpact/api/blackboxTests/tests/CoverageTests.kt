package org.vaccineimpact.api.blackboxTests.tests

import com.opencsv.CSVReader
import org.assertj.core.api.Assertions
import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.*
import org.vaccineimpact.api.blackboxTests.schemas.CSVSchema
import org.vaccineimpact.api.blackboxTests.schemas.SplitSchema
import org.vaccineimpact.api.blackboxTests.validators.SplitValidator
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.db.toDecimal
import org.vaccineimpact.api.models.helpers.FlexibleColumns
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.serialization.DataTableDeserializer
import org.vaccineimpact.api.test_helpers.DatabaseTest
import org.vaccineimpact.api.validateSchema.JSONValidator
import java.io.StringReader
import java.math.BigDecimal

class CoverageTests : DatabaseTest()
{
    val groupId = "group-1"
    val touchstoneVersionId = "touchstone-1"
    val scenarioId = "scenario-1"
    val coverageSetId = 1
    val groupScope = "modelling-group:$groupId"
    val minimumPermissions = PermissionSet("*/can-login", "*/scenarios.read", "$groupScope/responsibilities.read", "$groupScope/coverage.read")
    val url = "/modelling-groups/$groupId/responsibilities/$touchstoneVersionId/$scenarioId/coverage/"

    @Test
    fun `can get coverage data for responsibility`()
    {
        val schema = SplitSchema(json = "ScenarioAndCoverageSets", csv = "MergedCoverageData")
        val test = validate(url) against (schema) given {
            addCoverageData(it, touchstoneStatus = "open")
        } requiringPermissions { minimumPermissions }
        test.run()
    }

    @Test
    fun `can get streamed data without gzip`()
    {
        val userHelper = TestUserHelper()
        JooqContext().use {
            userHelper.setupTestUser(it)
            addCoverageData(it, touchstoneStatus = "open")
        }

        val response = RequestHelper().getWithoutGzip(url, minimumPermissions)
        SplitSchema(json = "ScenarioAndCoverageSets", csv = "MergedCoverageData")
                .validateResponse(response.text, response.headers["Content-Type"])

        Assertions.assertThat(response.headers["Content-Encoding"]).isNull()
    }

    @Test
    fun `can get wide coverage data for responsibility`()
    {
        val schema = SplitSchema(json = "ScenarioAndCoverageSets", csv = "MergedWideCoverageData")
        val test = validate("$url?format=wide") against (schema) given {
            addCoverageData(it, touchstoneStatus = "open")
        } requiringPermissions { minimumPermissions }
        test.run()
    }

    @Test
    fun `wide format coverage year columns are sorted`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980
        val testTarget = BigDecimal(123.123)
        val testCoverage = BigDecimal(456.456)

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?format=wide", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first().toList()
        val firstRow = csv.drop(1).first().toList()

        val expectedHeaders = listOf("scenario", "set_name", "vaccine", "gavi_support", "activity_type",
                "country_code", "country", "age_first", "age_last", "age_range_verbatim", "coverage_$testYear",
                "coverage_1985", "coverage_1990", "coverage_1995", "coverage_2000",
                "target_$testYear",
                "target_1985", "target_1990", "target_1995", "target_2000")

        headers.forEachIndexed { index, h ->
            Assertions.assertThat(h).isEqualTo(expectedHeaders[index])
        }

        Assertions.assertThat(BigDecimal(firstRow[10])).isEqualTo(testCoverage)
        Assertions.assertThat(BigDecimal(firstRow[15])).isEqualTo(testTarget)

    }

    @Test
    fun `wide format coverage rows are sorted`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            createUnorderedCoverageData(it)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?format=wide", minimumPermissions, acceptsContentType = "text/csv")
        val yearMap = mapOf("coverage_2000" to "<NA>",
                "coverage_2001" to "<NA>", "target_2000" to "<NA>", "target_2001" to "<NA>")

        val expected = listOf(
                TestWideCoverageRow(scenarioId, "First", "AF", "no gavi", "routine",
                        "AAA", "AAA-Name", 2, 4, "<NA>", yearMap),
                // first order by vaccine
                TestWideCoverageRow(scenarioId, "Second", "BF", "no gavi", "campaign",
                        "AAA", "AAA-Name", 1, 2, "<NA>", yearMap),
                // then by activity type
                TestWideCoverageRow(scenarioId, "Third", "BF", "no gavi", "routine",
                        "AAA", "AAA-Name", 1, 2, "<NA>", yearMap),
                // then by country
                TestWideCoverageRow(scenarioId, "Third", "BF", "no gavi", "routine",
                        "BBB", "BBB-Name", 1, 2, "<NA>", yearMap),
                // then by age first
                TestWideCoverageRow(scenarioId, "Third", "BF", "no gavi", "routine",
                        "BBB", "BBB-Name", 2, 2, "<NA>", yearMap),
                // then by age last
                TestWideCoverageRow(scenarioId, "Third", "BF", "no gavi", "routine",
                        "BBB", "BBB-Name", 2, 4, "<NA>", yearMap)
        )

        val rows = DataTableDeserializer.deserialize(response.text, TestWideCoverageRow::class)

        Assertions.assertThat(rows.toList()).containsExactlyElementsOf(expected)
    }

    @Test
    fun `can get pure CSV coverage data for responsibility`()
    {
        val schema = CSVSchema("MergedCoverageData")
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open")
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get(url, minimumPermissions, acceptsContentType = "text/csv")
        schema.validate(response.text)
    }

    @Test
    fun `can get pure CSV coverage data via one time link`()
    {
        validate("$url/get_onetime_link/") against "Token" given {
            addCoverageData(it, touchstoneStatus = "open")
        } requiringPermissions { minimumPermissions } andCheckString { token ->
            val oneTimeURL = "/onetime_link/$token/"
            val schema = CSVSchema("MergedCoverageData")
            val requestHelper = RequestHelper()
            val response = requestHelper.get(oneTimeURL)
            schema.validate(response.text)

            val badResponse = requestHelper.get(oneTimeURL)
            JSONValidator().validateError(badResponse.text, expectedErrorCode = "invalid-token-used")
        }
    }

    @Test
    fun `can get pure CSV coverage data via csv endpoint`()
    {
        val schema = CSVSchema("MergedCoverageData")
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open")
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("${url}csv", minimumPermissions)
        schema.validate(response.text)
    }

    @Test
    fun `only touchstone preparer can get coverage data for in-preparation responsibility`()
    {
        val permission = "*/touchstones.prepare"
        val checker = PermissionChecker(url, minimumPermissions + permission, SplitValidator())
        checker.checkPermissionIsRequired(permission,
                given = { addCoverageData(it, touchstoneStatus = "in-preparation") },
                expectedProblem = ExpectedProblem("unknown-touchstone-version", touchstoneVersionId))
    }

    private fun addCoverageData(db: JooqContext, touchstoneStatus: String,
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

    private fun createUnorderedCoverageData(db: JooqContext)
    {
        db.addGroup(groupId, "description")
        db.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
        db.addScenarioDescription(scenarioId, "Blue Fever Scenario", "BF", addDisease = true)
        db.addVaccine("BF", "Blue Fever")
        db.addVaccine("AF", "Alpha Fever")

        val setId = db.addResponsibilitySet(groupId, touchstoneVersionId, "incomplete")
        db.addResponsibility(setId, touchstoneVersionId, scenarioId)
        db.addCoverageSet(touchstoneVersionId, "First", "AF", "without", "routine", id = 1)
        db.addCoverageSet(touchstoneVersionId, "Second", "BF", "without", "campaign", id = 2)
        db.addCoverageSet(touchstoneVersionId, "Third", "BF", "without", "routine", id = 3)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, coverageSetId = 1, order = 0)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, coverageSetId = 2, order = 1)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, coverageSetId = 3, order = 2)

        db.addCountries(listOf("AAA", "BBB"))

        // adding these in jumbled up order
        // values are null because we are just testing the order these rows appear in
        db.addCoverageRow(3, "BBB", 2001, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(2, "AAA", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(1, "AAA", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null)
        db.addCoverageRow(3, "BBB", 2001, 2.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(3, "AAA", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(3, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(3, "BBB", 2000, 2.toDecimal(), 4.toDecimal(), null, null, null)
        db.addCoverageRow(3, "BBB", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null)
        db.addCoverageRow(3, "BBB", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(2, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(1, "AAA", 2000, 2.toDecimal(), 4.toDecimal(), null, null, null)
        db.addCoverageRow(3, "BBB", 2000, 2.toDecimal(), 2.toDecimal(), null, null, null)


    }
}
