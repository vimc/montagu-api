package org.vaccineimpact.api.blackboxTests.tests

import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.RequestHelper
import org.vaccineimpact.api.blackboxTests.schemas.JSONSchema
import org.vaccineimpact.api.test_helpers.DatabaseTest

class IndexTests : DatabaseTest()
{
    @Test
    fun `can read index`()
    {
        val response = RequestHelper().get("/")
        JSONSchema("Index").validateResponse(response.text, response.headers["Content-Type"])
    }
}