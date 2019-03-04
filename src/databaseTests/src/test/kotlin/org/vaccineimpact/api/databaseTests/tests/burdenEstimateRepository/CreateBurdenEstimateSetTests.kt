package org.vaccineimpact.api.databaseTests.tests.burdenEstimateRepository

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.models.BurdenEstimateSetType
import org.vaccineimpact.api.models.BurdenEstimateSetTypeCode
import org.vaccineimpact.api.models.CreateBurdenEstimateSet

class CreateBurdenEstimateSetTests : BurdenEstimateRepositoryTests()
{
    @Test
    fun `can create burden estimate set with empty status`()
    {
        val returnedIds = withDatabase { db ->
            setupDatabase(db)
        }

        val setId = withRepo { repo ->
            repo.addBurdenEstimateSet(returnedIds.responsibility, username, timestamp, returnedIds.modelVersion!!, defaultProperties)
        }

        withDatabase { db ->
            checkBurdenEstimateSetMetadata(db, setId, returnedIds, "empty")
        }
    }

    @Test
    fun `when creating burden estimate set, user supplied properties are persisted`()
    {
        val properties = CreateBurdenEstimateSet(
                BurdenEstimateSetType(
                        BurdenEstimateSetTypeCode.CENTRAL_AVERAGED,
                        "mean"
                ), 1
        )

        val returnedIds = withDatabase { db ->
            setupDatabase(db)
        }

        withRepo { repo ->
            repo.addBurdenEstimateSet(returnedIds.responsibility, username, timestamp,
                    returnedIds.modelVersion!!, properties)
        }

        withRepo { repo ->
            val set = repo.getBurdenEstimateSets(groupId, touchstoneVersionId, scenarioId).single()
            assertThat(set.type).isEqualTo(properties.type)
        }
    }
}