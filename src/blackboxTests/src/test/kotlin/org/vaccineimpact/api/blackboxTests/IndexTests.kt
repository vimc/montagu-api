package org.vaccineimpact.api.blackboxTests

import org.junit.Test
import org.vaccineimpact.api.blackboxTests.helpers.RequestHelper
import org.vaccineimpact.api.blackboxTests.helpers.SchemaValidator
import org.vaccineimpact.api.test_helpers.MontaguTests

class IndexTests : MontaguTests()
{
    @Test
    fun `can read index`()
    {
        val response = RequestHelper().get("/")
        SchemaValidator().validate("Index", response.text)
    }
}