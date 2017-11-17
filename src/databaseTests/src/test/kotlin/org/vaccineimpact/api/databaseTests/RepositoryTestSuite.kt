package org.vaccineimpact.api.databaseTests

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.vaccineimpact.api.databaseTests.tests.*
import org.vaccineimpact.api.databaseTests.tests.modellingGroupRepository.GetModellingGroupTests
import org.vaccineimpact.api.databaseTests.tests.modellingGroupRepository.GetResponsibilitiesTests
import org.vaccineimpact.api.databaseTests.tests.modellingGroupRepository.GetResponsibilityCoverageSetsTests
import org.vaccineimpact.api.databaseTests.tests.touchstoneRepository.GetDemographicsTests
import org.vaccineimpact.api.databaseTests.tests.touchstoneRepository.GetScenarioTests
import org.vaccineimpact.api.test_helpers.DatabaseCreationHelper

// Keep these sorted alphabetically, for consistency
@RunWith(Suite::class)
@Suite.SuiteClasses(
        BurdenEstimateRepositoryTests::class,
        BurdenEstimateRepositoryTestsDeprecated::class,
        DiseaseTests::class,
        GetDemographicsTests::class,
        GetModellingGroupTests::class,
        GetResponsibilitiesTests::class,
        GetResponsibilityCoverageSetsTests::class,
        GetScenarioTests::class,
        ModelTests::class,
        TokenRepositoryTests::class,
        TransactionalityTests::class,
        TriggerTests::class,
        UserTests::class
)
class RepositoryTestSuite
{
    companion object
    {
        @BeforeClass
        @JvmStatic
        fun createTemplateDatabase()
        {
            DatabaseCreationHelper.createTemplateFromDatabase()
        }

        @AfterClass
        @JvmStatic
        fun restoreDatabaseFromTemplate()
        {
            DatabaseCreationHelper.restoreDatabaseFromTemplate()
        }
    }
}
