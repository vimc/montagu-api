package org.vaccineimpact.api.blackboxTests.tests.Coverage

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
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.serialization.DataTableDeserializer
import java.io.StringReader
import java.math.BigDecimal

class GroupCoverageTests : CoverageTests()
{
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
    fun `can get coverage data for responsibility with subnational rows`()
    {
        val schema = SplitSchema(json = "ScenarioAndCoverageSets", csv = "MergedCoverageData")
        val test = validate(url) against (schema) given {
            addCoverageData(it, touchstoneStatus = "open", includeSubnationalCoverage=true)
        } requiringPermissions { minimumPermissions }
        test.run()
    }

    @Test
    fun `coverage data for responsibility returns gender`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", gender = 2)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get(url, minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first()
        Assertions.assertThat(headers[13]).isEqualTo("gender")

        val firstRow = csv.drop(1).first().toList()

        Assertions.assertThat(firstRow[13]).isEqualTo("male")
    }

    @Test
    fun `coverage data for scenario for responsibility with null gender defaults to 'both'`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", gender = null)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get(url, minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first()
        Assertions.assertThat(headers[13]).isEqualTo("gender")

        val firstRow = csv.drop(1).first().toList()

        Assertions.assertThat(firstRow[13]).isEqualTo("both")
    }

    @Test
    fun `coverage data for responsibility for coverage with null gender defaults to 'female' when disease is 'HPV'`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", gender = null, disease="HPV")
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get(url, minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first()
        Assertions.assertThat(headers[13]).isEqualTo("gender")


        val firstRow = csv.drop(1).first().toList()

        Assertions.assertThat(firstRow[13]).isEqualTo("female")
    }

    @Test
    fun `coverage data for responsibility has no gender column when touchstone is pre-2019`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980

        JooqContext().use {
            addCoverageData(it, testYear = testYear, touchstoneStatus = "open", gender = 2, touchstoneName = "201810-test")
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("/modelling-groups/$groupId/responsibilities/201810-test-1/$scenarioId/coverage/",
                minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first()
        Assertions.assertThat(headers.count()).isEqualTo(14)
    }

    @Test
    fun `coverage data for responsibility with subnational rows with different age_range_verbatim keeps rows separate`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980
        val testTarget = 1000.toDecimal()
        val testCoverage = 0.9.toDecimal()

        val age_range_1 = "age_range_1"
        val age_range_2 = "age_range_2"

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage, includeSubnationalCoverage=false,
                    ageRangeVerbatim = age_range_1)
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage, includeSubnationalCoverage=false,
                    ageRangeVerbatim = age_range_2, useExistingCoverageSetId = true)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        Assertions.assertThat(csv.count()).isEqualTo(101)

        //Headers:
        //0: "scenario", 1: "set_name", 2: "vaccine", 3: "gavi_support", 4: "activity_type",
        //5: "country_code", 6: "country", 7: "year" 8: "age_first", 9: "age_last", 10: "age_range_verbatim",
        //11: "target", 12: "coverage" 13: "gender"
        val firstRow = csv.drop(1).first().toList()
        val expectedAggregatedTarget = "1000"
        val expectedAggregatedCoverage = "0.9"

        Assertions.assertThat(firstRow[10]).isEqualTo(age_range_1)
        Assertions.assertThat(firstRow[11]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[12]).isEqualTo(expectedAggregatedCoverage)

        //Check not just the first row with values
        val secondRow = csv.drop(2).first().toList()
        Assertions.assertThat(secondRow[10]).isEqualTo(age_range_2)
        Assertions.assertThat(secondRow[11]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(secondRow[12]).isEqualTo(expectedAggregatedCoverage)
    }

    @Test
    fun `coverage data for responsibility with subnational rows have expected target and coverage values`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980
        val testTarget = 1000.toDecimal()
        val testCoverage = 0.9.toDecimal()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage, includeSubnationalCoverage=true)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get(url, minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        //Headers:
        //0: "scenario", 1: "set_name", 2: "vaccine", 3: "gavi_support", 4: "activity_type",
        //5: "country_code", 6: "country", 7: "year" 8: "age_first", 9: "age_last", 10: "age_range_verbatim",
        //11: "target", 12: "coverage" 13: "gender"
        val firstRow = csv.drop(1).first().toList()
        val expectedAggregatedTarget = "1500"
        val expectedAggregatedCoverage = "0.7"

        Assertions.assertThat(firstRow[11]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[12]).isEqualTo(expectedAggregatedCoverage)

        //Check not just the first row with values
        val secondRow = csv.drop(2).first().toList()
        Assertions.assertThat(secondRow[11]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(secondRow[12]).isEqualTo(expectedAggregatedCoverage)
    }

    @Test
    fun `only returns coverage data for expected countries`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            createUnorderedCoverageData(it)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get(url, minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val rows = csv.drop(1) // drop headers
        Assertions.assertThat(rows.count()).isEqualTo(6)
    }

    @Test
    fun `can get coverage data for group, for all countries`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            createUnorderedCoverageData(it)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?all-countries=true", minimumPermissions, acceptsContentType = "text/csv")
        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val rows = csv.drop(1) // drop headers
        Assertions.assertThat(rows.count()).isEqualTo(12)
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
    fun `can get wide coverage data for responsibility with subnational rows`()
    {
        val schema = SplitSchema(json = "ScenarioAndCoverageSets", csv = "MergedWideCoverageData")
        val test = validate("$url?format=wide") against (schema) given {
            addCoverageData(it, touchstoneStatus = "open", includeSubnationalCoverage=true)
        } requiringPermissions { minimumPermissions }
        test.run()
    }

    @Test
    fun `coverage data for responsibility rows have expected target and coverage values`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980
        val testTarget = BigDecimal(1000)
        val testCoverage = 0.9.toDecimal()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage, uniformData=true)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get(url, minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first().toList()
        val expectedHeaders = listOf("scenario", "set_name", "vaccine", "gavi_support", "activity_type",
                "country_code", "country", "year", "age_first", "age_last", "age_range_verbatim", "target",
                "coverage", "gender", "proportion_risk")
        headers.forEachIndexed { index, h ->
            Assertions.assertThat(h).isEqualTo(expectedHeaders[index])
        }

        val firstRow = csv.drop(1).first().toList()
        val expectedTarget = "1000"
        val expectedCoverage = "0.9"

        //test all target values
        Assertions.assertThat(firstRow[11]).isEqualTo(expectedTarget)
        Assertions.assertThat(firstRow[12]).isEqualTo(expectedCoverage)

        //gender
        Assertions.assertThat(firstRow[13]).isEqualTo("both")
    }

    @Test
    fun `wide coverage data for responsibility rows have expected target and coverage values`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980
        val testTarget = 1000.toDecimal()
        val testCoverage = 0.9.toDecimal()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage, uniformData=true)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?format=wide", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        //Headers:
        //0: "scenario", 1: "set_name", 2: "vaccine", 3: "gavi_support", 4: "activity_type",
        //5: "country_code", 6: "country", 7: "age_first", 8: "age_last", 9: "age_range_verbatim",
        //10: "gender"
        //11: "coverage_$testYear",
        //12: "coverage_1985", 13: "coverage_1990", 14: "coverage_1995", 15: "coverage_2000",
        //16: "target_$testYear", 17: "target_1985", 18: "target_1990", 19: "target_1995", 20: "target_2000"

        val firstRow = csv.drop(1).first().toList()
        val expectedAggregatedTarget = "1000"
        val expectedAggregatedCoverage = "0.9"

        //test all target values
        Assertions.assertThat(firstRow[16]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[17]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[18]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[19]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[20]).isEqualTo(expectedAggregatedTarget)

        //test all coverage values
        Assertions.assertThat(firstRow[11]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(firstRow[12]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(firstRow[13]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(firstRow[14]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(firstRow[15]).isEqualTo(expectedAggregatedCoverage)

    }

    @Test
    fun `wide coverage data for responsibility with subnational rows have expected target and coverage values`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980
        val testTarget = 1000.toDecimal()
        val testCoverage = 0.9.toDecimal()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage, includeSubnationalCoverage=true, uniformData=true)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?format=wide", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        Assertions.assertThat(csv.count()).isEqualTo(11)

        val firstRow = csv.drop(1).first().toList()
        val expectedAggregatedTarget = "1500"
        val expectedAggregatedCoverage = "0.7"

        //test all target values
        Assertions.assertThat(firstRow[16]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[17]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[18]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[19]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(firstRow[20]).isEqualTo(expectedAggregatedTarget)

        //test all coverage values
        Assertions.assertThat(firstRow[11]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(firstRow[12]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(firstRow[13]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(firstRow[14]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(firstRow[15]).isEqualTo(expectedAggregatedCoverage)


    }

    @Test
    fun `wide coverage data for responsibility returns gender`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", gender = 2)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?format=wide", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first()
        Assertions.assertThat(headers[10]).isEqualTo("gender")

        val firstRow = csv.drop(1).first().toList()

        Assertions.assertThat(firstRow[10]).isEqualTo("male")
    }

    @Test
    fun `wide coverage data for responsibility for coverage with null gender defaults to 'both'`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", gender = null)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?format=wide", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first()
        Assertions.assertThat(headers[10]).isEqualTo("gender")

        val firstRow = csv.drop(1).first().toList()

        Assertions.assertThat(firstRow[10]).isEqualTo("both")
    }

    @Test
    fun `wide coverage data for responsibility for coverage with null gender defaults to 'female' when disease is 'HPV'`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", gender = null, disease="HPV")
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?format=wide", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first()
        Assertions.assertThat(headers[10]).isEqualTo("gender")


        val firstRow = csv.drop(1).first().toList()

        Assertions.assertThat(firstRow[10]).isEqualTo("female")
    }

    @Test
    fun `wide coverage data for responsibility has no gender column when touchstone is pre-2019`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980

        JooqContext().use {
            addCoverageData(it, testYear = testYear, touchstoneStatus = "open", gender = 2, touchstoneName = "201810-test")
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("/modelling-groups/$groupId/responsibilities/201810-test-1/$scenarioId/coverage/?format=wide", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        val headers = csv.first()
        Assertions.assertThat(headers.count()).isEqualTo(20)
        Assertions.assertThat(headers[10]).isEqualTo("coverage_$testYear")
    }

    @Test
    fun `wide coverage data for responsibility with subnational rows with different age_range_verbatim keeps rows separate`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980
        val testTarget = 1000.toDecimal()
        val testCoverage = 0.9.toDecimal()

        val age_range_1 = "age_range_1"
        val age_range_2 = "age_range_2"

        JooqContext().use {
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage, includeSubnationalCoverage=false, uniformData=true,
                    ageRangeVerbatim = age_range_1)
            addCoverageData(it, touchstoneStatus = "open", testYear = testYear,
                    target = testTarget, coverage = testCoverage, includeSubnationalCoverage=false, uniformData=true,
                    ageRangeVerbatim = age_range_2, useExistingCoverageSetId = true)
            userHelper.setupTestUser(it)
        }

        val response = requestHelper.get("$url?format=wide", minimumPermissions, acceptsContentType = "text/csv")

        val csv = StringReader(response.text)
                .use { CSVReader(it).readAll() }

        Assertions.assertThat(csv.count()).isEqualTo(21)

        csv.sortBy { it[9] } //Row order for wide format is not defined - sort by age_range_verbatim

        //Headers:
        //0: "scenario", 1: "set_name", 2: "vaccine", 3: "gavi_support", 4: "activity_type",
        //5: "country_code", 6: "country", 7: "age_first", 8: "age_last", 9: "age_range_verbatim",
        //10: gender, 11: "coverage_$testYear",
        //12: "coverage_1985", 13: "coverage_1990", 14: "coverage_1995", 15: "coverage_2000",
        //16: "target_$testYear", 17: "target_1985", 18: "target_1990", 19: "target_1995", 20: "target_2000"

        val range1Row = csv.drop(1).first().toList()
        val expectedAggregatedTarget = "1000"
        val expectedAggregatedCoverage = "0.9"

        Assertions.assertThat(range1Row[9]).isEqualTo(age_range_1)

        //test all target values
        Assertions.assertThat(range1Row[16]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(range1Row[17]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(range1Row[18]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(range1Row[19]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(range1Row[20]).isEqualTo(expectedAggregatedTarget)

        //test all coverage values
        Assertions.assertThat(range1Row[11]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(range1Row[12]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(range1Row[13]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(range1Row[14]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(range1Row[15]).isEqualTo(expectedAggregatedCoverage)

        val range2Row = csv.drop(11).first().toList() //First row with the second age_range_verbatim
        Assertions.assertThat(range2Row[9]).isEqualTo(age_range_2)

        //test all target values
        Assertions.assertThat(range2Row[16]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(range2Row[17]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(range2Row[18]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(range2Row[19]).isEqualTo(expectedAggregatedTarget)
        Assertions.assertThat(range2Row[20]).isEqualTo(expectedAggregatedTarget)

        //test all coverage values
        Assertions.assertThat(range2Row[11]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(range2Row[12]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(range2Row[13]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(range2Row[14]).isEqualTo(expectedAggregatedCoverage)
        Assertions.assertThat(range2Row[15]).isEqualTo(expectedAggregatedCoverage)
    }

    @Test
    fun `wide format coverage year columns are sorted`()
    {
        val userHelper = TestUserHelper()
        val requestHelper = RequestHelper()

        val testYear = 1980
        val testTarget = 123.123.toDecimal()
        val testCoverage = 456.461.toDecimal()

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
                "country_code", "country", "age_first", "age_last", "age_range_verbatim",
                "gender", "coverage_$testYear",
                "coverage_1985", "coverage_1990", "coverage_1995", "coverage_2000",
                "target_$testYear",
                "target_1985", "target_1990", "target_1995", "target_2000")

        headers.forEachIndexed { index, h ->
            Assertions.assertThat(h).isEqualTo(expectedHeaders[index])
        }

        Assertions.assertThat(firstRow[11]).isEqualTo("456.461")
        Assertions.assertThat(firstRow[16]).isEqualTo("123.123")

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

        val response = requestHelper.get("$url?format=wide&all-countries=true", minimumPermissions, acceptsContentType = "text/csv")
        val yearMap = mapOf("coverage_2000" to "<NA>",
                "coverage_2001" to "<NA>", "target_2000" to "<NA>", "target_2001" to "<NA>")

        val expected = listOf(
                TestWideCoverageRow(scenarioId, "First", "AF", "no gavi", "routine",
                        "AAA", "AAA-Name", 2, 4, "<NA>", "both", yearMap),
                // first order by vaccine
                TestWideCoverageRow(scenarioId, "Second", "BF", "no gavi", "campaign",
                        "AAA", "AAA-Name", 1, 2, "<NA>", "both", yearMap),
                // then by activity type
                TestWideCoverageRow(scenarioId, "Third", "BF", "no gavi", "routine",
                        "AAA", "AAA-Name", 1, 2, "<NA>", "both", yearMap),
                // then by country
                TestWideCoverageRow(scenarioId, "Third", "BF", "no gavi", "routine",
                        "BBB", "BBB-Name", 1, 2, "<NA>", "both", yearMap),
                // then by age first
                TestWideCoverageRow(scenarioId, "Third", "BF", "no gavi", "routine",
                        "BBB", "BBB-Name", 2, 2, "<NA>", "both", yearMap),
                // then by age last
                TestWideCoverageRow(scenarioId, "Third", "BF", "no gavi", "routine",
                        "BBB", "BBB-Name", 2, 4, "<NA>", "both", yearMap)
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
    fun `can get pure CSV coverage data via csv endpoint`()
    {
        val userHelper = TestUserHelper()
        val schema = CSVSchema("MergedCoverageData")
        val requestHelper = RequestHelper()

        JooqContext().use {
            userHelper.setupTestUser(it)
            addCoverageData(it, touchstoneStatus = "open")
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

    @Test
    fun `getting coverage data for nonexistent group with global permissions returns 404`()
    {
        JooqContext().use {

            addCoverageData(it, touchstoneStatus = "open")
            TestUserHelper().setupTestUser(it)
        }

        val coverageUrl ="/modelling-groups/bad-group-id/responsibilities/$touchstoneVersionId/$scenarioId/coverage/"

        val globalPermissions = PermissionSet("*/can-login", "*/scenarios.read",
                "*/responsibilities.read", "*/coverage.read")


        val response = RequestHelper().get(coverageUrl, globalPermissions, acceptsContentType = "text/csv")

        Assertions.assertThat(response.statusCode).isEqualTo(404)
    }

    @Test
    fun `getting coverage data for nonexistent group with group-scoped permission returns 403`()
    {
        JooqContext().use {

            addCoverageData(it, touchstoneStatus = "open")
            TestUserHelper().setupTestUser(it)
        }

        val coverageUrl ="/modelling-groups/bad-group-id/responsibilities/$touchstoneVersionId/$scenarioId/coverage/"

        val response = RequestHelper().get(coverageUrl, minimumPermissions, acceptsContentType = "text/csv")

        Assertions.assertThat(response.statusCode).isEqualTo(403)
    }

    @Test
    fun `getting coverage data for different group with group-scoped permission returns 403`()
    {

        JooqContext().use {

            addCoverageData(it, touchstoneStatus = "open")
            TestUserHelper().setupTestUser(it)

            //create a second group which our user does not have access to
            it.addGroup("group-2", "description")
        }

        val coverageUrl ="/modelling-groups/group-2/responsibilities/$touchstoneVersionId/$scenarioId/coverage/"

        val response = RequestHelper().get(coverageUrl, minimumPermissions, acceptsContentType = "text/csv")

        Assertions.assertThat(response.statusCode).isEqualTo(403)
    }

    private fun createUnorderedCoverageData(db: JooqContext)
    {
        db.addGroup(groupId, "description")
        db.addTouchstoneVersion("touchstone", 1, "description", "open", addTouchstone = true)
        db.addScenarioDescription(scenarioId, "Blue Fever Scenario", "BF", addDisease = true)
        db.addVaccine("BF", "Blue Fever")
        db.addVaccine("AF", "Alpha Fever")

        val setId = db.addResponsibilitySet(groupId, touchstoneVersionId, "incomplete")
        val responsibilityId = db.addResponsibility(setId, touchstoneVersionId, scenarioId)
        db.addCoverageSet(touchstoneVersionId, "First", "AF", "without", "routine", id = 1)
        db.addCoverageSet(touchstoneVersionId, "Second", "BF", "without", "campaign", id = 2)
        db.addCoverageSet(touchstoneVersionId, "Third", "BF", "without", "routine", id = 3)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, coverageSetId = 1, order = 0)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, coverageSetId = 2, order = 1)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, coverageSetId = 3, order = 2)

        db.addCountries(listOf("AAA", "BBB"))
        db.addExpectations(responsibilityId, countries = listOf("AAA"))

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
