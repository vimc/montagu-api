package org.vaccineimpact.api.blackboxTests.tests

import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.RequestHelper
import org.vaccineimpact.api.blackboxTests.helpers.TestUserHelper
import org.vaccineimpact.api.blackboxTests.helpers.validate
import org.vaccineimpact.api.models.permissions.PermissionSet
import org.vaccineimpact.api.test_helpers.DatabaseTest
import org.vaccineimpact.api.validateSchema.JSONValidator

class OneTimeTokenTests : DatabaseTest()
{
    @Test
    fun `can get new style onetime token`()
    {
        validate("/onetime_token/?url=http://localhost/test") against "Token" given {
            //do nothing
        } requiringPermissions {
            PermissionSet("*/can-login")
        }
    }

    @Test
    fun `can use onetime token to access link`()
    {
        val desiredUrl = "/touchstones/"
        val permissions = PermissionSet("*/can-login", "*/touchstones.read")

        val bearerToken = TestUserHelper.setupTestUserAndGetToken(permissions)
        val requestHelper = RequestHelper()
        val oneTimeToken = requestHelper.getOneTimeToken(desiredUrl, bearerToken)
        val response = RequestHelper().get("$desiredUrl?access_token=$oneTimeToken")

        JSONValidator().validateSuccess(response.text)
    }
}
