package org.vaccineimpact.api.databaseTests

import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.vaccineimpact.api.databaseTests.tests.*
import org.vaccineimpact.api.databaseTests.tests.burdenEstimateRepository.*
import org.vaccineimpact.api.databaseTests.tests.modellingGroupRepository.GetModellingGroupTests
import org.vaccineimpact.api.databaseTests.tests.modellingGroupRepository.GetResponsibilitiesTests
import org.vaccineimpact.api.databaseTests.tests.modellingGroupRepository.GetResponsibilityCoverageSetsTests
import org.vaccineimpact.api.databaseTests.tests.modellingGroupRepository.GetModellingGroupTouchstoneTests
import org.vaccineimpact.api.databaseTests.tests.touchstoneRepository.GetDemographicsTests
import org.vaccineimpact.api.databaseTests.tests.touchstoneRepository.GetScenarioTests
import org.vaccineimpact.api.databaseTests.tests.touchstoneRepository.GetTouchstonesTests
import org.vaccineimpact.api.test_helpers.DatabaseCreationHelper

// Keep these sorted alphabetically, for consistency
@RunWith(Suite::class)
@Suite.SuiteClasses(
        AnnexTests::class,
        BurdenEstimateWriterTests::class,
        ClearBurdenEstimateSetTests::class,
        CloseBurdenEstimateSetTests::class,
        CreateBurdenEstimateSetTests::class,
        DiseaseTests::class,
        GetDemographicsTests::class,
        GetModellingGroupTests::class,
        GetResponsibilitiesTests::class,
        GetResponsibilityCoverageSetsTests::class,
        GetScenarioTests::class,
        GetModellingGroupTouchstoneTests::class,
        GetTouchstonesTests::class,
        ModelParameterTests::class,
        ModelTests::class,
        OneTimeTokenCheckerTests::class,
        PopulateBurdenEstimateSetTests::class,
        RetrieveBurdenEstimatesTests::class,
        ScenarioTests::class,
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
