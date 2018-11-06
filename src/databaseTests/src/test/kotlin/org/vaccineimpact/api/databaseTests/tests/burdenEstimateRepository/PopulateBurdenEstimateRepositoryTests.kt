package org.vaccineimpact.api.databaseTests.tests.burdenEstimateRepository

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.app.repositories.burdenestimates.CentralBurdenEstimateWriter
import org.vaccineimpact.api.app.repositories.burdenestimates.StochasticBurdenEstimateWriter
import org.vaccineimpact.api.models.BurdenEstimateSet
import org.vaccineimpact.api.models.BurdenEstimateSetStatus
import org.vaccineimpact.api.models.BurdenEstimateSetType
import org.vaccineimpact.api.models.BurdenEstimateSetTypeCode
import java.time.Instant

class PopulateBurdenEstimateRepositoryTests : BurdenEstimateRepositoryTests()
{

    @Test
    fun `can update set status`()
    {
        val (returnedIds, setId) = withDatabase { db ->
            setupDatabaseWithBurdenEstimateSetAndReturnIds(db, type = "central-averaged")
        }
        withRepo {
            it.changeBurdenEstimateStatus(setId, BurdenEstimateSetStatus.PARTIAL)
        }

        withDatabase { db ->
            checkBurdenEstimateSetMetadata(db, setId, returnedIds, "partial")
        }
    }

    @Test
    fun `gets central estimate writer when set type is stochastic`()
    {
        val centralEstimateSet = BurdenEstimateSet(
                1, Instant.now(), "test.user",
                BurdenEstimateSetType(BurdenEstimateSetTypeCode.CENTRAL_AVERAGED, "mean"),
                BurdenEstimateSetStatus.EMPTY,
                emptyList()
        )
        withRepo {
            val result = it.getEstimateWriter(centralEstimateSet)
            assertThat(result is CentralBurdenEstimateWriter).isTrue()
        }
    }

    @Test
    fun `gets stochastic estimate writer when set type is stochastic`()
    {
        val stochasticEstimateSet = BurdenEstimateSet(
                1, Instant.now(), "test.user",
                BurdenEstimateSetType(BurdenEstimateSetTypeCode.STOCHASTIC, "mean"),
                BurdenEstimateSetStatus.EMPTY,
                emptyList()
        )
        withRepo {
            val result = it.getEstimateWriter(stochasticEstimateSet)
            assertThat(result is StochasticBurdenEstimateWriter).isTrue()
        }

    }

    //
//    @Test
//    fun `populate set throws unknown object error if set does not exist`()
//    {
//        JooqContext().use {
//            setupDatabase(it)
//            val repo = makeRepository(it)
//            Assertions.assertThatThrownBy {
//                repo.populateBurdenEstimateSet(12, groupId, touchstoneVersionId, scenarioId, data())
//            }.isInstanceOf(UnknownObjectError::class.java)
//        }
//    }
}