package org.vaccineimpact.api.blackboxTests.tests.BurdenEstimates

import org.assertj.core.api.Assertions
import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.*
import org.vaccineimpact.api.blackboxTests.schemas.CSVSchema
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.validateSchema.JSONValidator
import spark.route.HttpMethod

class PopulateBurdenEstimateTests : BurdenEstimateTests()
{
    override val usesAnnex = true

    @Test
    fun `can populate central burden estimate`()
    {
        val setId = JooqContext().use {
            setUpWithBurdenEstimateSet(it)
        }
        validate("$setUrl$setId/", method = HttpMethod.post) withRequestSchema {
            CSVSchema("BurdenEstimate")
        } sending {
            csvData
        } withPermissions {
            requiredWritePermissions
        } andCheckHasStatus 200..299
    }

    @Test
    fun `cannot provide stochastic data for central estimates`()
    {
        val setId = JooqContext().use {
            setUpWithBurdenEstimateSet(it)
        }
        val response = RequestHelper().post("$setUrl/$setId/",
                requiredWritePermissions + "*/can-login", stochasticCSVData)
        JSONValidator().validateError(response.text, "csv-unexpected-header",
                "Expected column header 'year'; found 'run_id' instead (column 1)")
    }

    @Test
    fun `can populate stochastic burden estimate`()
    {
        TestUserHelper.setupTestUser()
        val setId = JooqContext().use {
            setUpWithStochasticBurdenEstimateSet(it)
        }
        validate("$setUrl/$setId/", method = HttpMethod.post) withRequestSchema {
            CSVSchema("StochasticBurdenEstimate")
        } sending {
            stochasticCSVData
        } withPermissions {
            requiredWritePermissions
        } andCheckHasStatus 200..299
    }

    @Test
    fun `cannot provide central data for stochastic estimates`()
    {
        TestUserHelper.setupTestUser()
        val setId = JooqContext().use {
            setUpWithStochasticBurdenEstimateSet(it)
        }
        val response = RequestHelper().post("$setUrl/$setId/",
                requiredWritePermissions + "*/can-login", csvData)
        JSONValidator().validateError(response.text, "csv-unexpected-header",
                "Expected column header 'run_id'; found 'year' instead (column 1)")
    }

    @Test
    fun `bad CSV headers results in ValidationError`()
    {
        val setId = JooqContext().use {
            setUpWithBurdenEstimateSet(it)
        }
        val token = TestUserHelper.getToken(requiredWritePermissions, includeCanLogin = true)
        val helper = RequestHelper()
        val response = helper.post("$setUrl/$setId/", "bad_header,year,age,country,country_name,cohort_size", token = token)
        JSONValidator().validateError(response.text, "csv-unexpected-header")
    }

    @Test
    fun `bad CSV data results in ValidationError`()
    {
        val setId = JooqContext().use {
            setUpWithBurdenEstimateSet(it)
        }
        val token = TestUserHelper.getToken(requiredWritePermissions, includeCanLogin = true)
        val helper = RequestHelper()
        val response = helper.post("$setUrl/$setId/", badCSVData, token = token)
        JSONValidator().validateError(response.text, "csv-bad-data-type:1:cohort_size")
    }

    @Test
    fun `can populate burden estimate via onetime link`()
    {
        val requestHelper = RequestHelper()

        var setId = 0
        JooqContext().use {
            setId = setUpWithBurdenEstimateSet(it)
        }

        val token = TestUserHelper.getToken(requiredWritePermissions.plus(PermissionSet("*/can-login")))
        val onetimeTokenResult = requestHelper.get("$setUrl/$setId/get_onetime_link/", token)
        val onetimeToken = onetimeTokenResult.montaguData<String>()!!

        val oneTimeURL = "/onetime_link/$onetimeToken/"

        val response = requestHelper.postFile(oneTimeURL, csvData)

        Assertions.assertThat(response.statusCode).isEqualTo(200)
    }

    @Test
    fun `can populate burden estimate via onetime link and redirect`()
    {
        val requestHelper = RequestHelper()

        var setId = 0
        JooqContext().use {
            setId = setUpWithBurdenEstimateSet(it)
        }

        val url = "$setUrl/$setId/get_onetime_link/?redirectUrl=http://localhost/"
        val token = TestUserHelper.getToken(requiredWritePermissions.plus(PermissionSet("*/can-login")))
        val onetimeTokenResult = requestHelper.get(url, token)
        val onetimeToken = onetimeTokenResult.montaguData<String>()!!

        val oneTimeURL = "/onetime_link/$onetimeToken/"
        val response = requestHelper.postFile(oneTimeURL, csvData)
        val resultAsString = response.getResultFromRedirect(checkRedirectTarget = "http://localhost")
        JSONValidator().validateSuccess(resultAsString)
    }

    @Test
    fun `bad CSV headers results in ValidationError in redirect`()
    {
        val setId = JooqContext().use {
            setUpWithBurdenEstimateSet(it)
        }

        validate("$setUrl/$setId/get_onetime_link/?redirectUrl=http://localhost") against "Token" given { db ->
            //set up already done
        } requiringPermissions {
            requiredWritePermissions
        } andCheckString { token ->
            val oneTimeURL = "/onetime_link/$token/"
            val requestHelper = RequestHelper()

            val response = requestHelper.postFile(oneTimeURL, "bad_header,year,age,country,country_name,cohort_size")
            val resultAsString = response.getResultFromRedirect(checkRedirectTarget = "http://localhost")
            JSONValidator().validateError(resultAsString, expectedErrorCode = "csv-unexpected-header")
        }
    }
}