package org.vaccineimpact.api.databaseTests

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.vaccineimpact.api.security.tests.*
import org.vaccineimpact.api.test_helpers.DatabaseCreationHelper

// Keep these sorted alphabetically, for consistency
@RunWith(Suite::class)
@Suite.SuiteClasses(
        AddUserToGroupOptionsTests::class,
        AddUserToGroupTests::class,
        AddRolesToUserTests::class,
        GroupsTests::class,
        QuestionTests::class
)
class UserCLITestSuite
{
    companion object
    {
        @BeforeClass
        @JvmStatic
        fun createTemplateDatabase()
        {
            DatabaseCreationHelper.main.createTemplateFromDatabase()
            DatabaseCreationHelper.annex.createTemplateFromDatabase()
        }

        @AfterClass
        @JvmStatic
        fun restoreDatabaseFromTemplate()
        {
            DatabaseCreationHelper.main.restoreDatabaseFromTemplate()
            DatabaseCreationHelper.annex.restoreDatabaseFromTemplate()
        }
    }
}
