package org.vaccineimpact.api.databaseTests.tests

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.app.errors.OperationNotAllowedError
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.repositories.BurdenEstimateWriter
import org.vaccineimpact.api.app.repositories.StochasticBurdenEstimateWriter
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.addBurdenEstimateSet

class PopulateBurdenEstimateSetTests : BurdenEstimateRepositoryTests()
{
    @Test
    fun `can populate burden estimate set`()
    {
        val returnedIds = withDatabase { db ->
            setupDatabase(db)
        }
        val setId = withRepo { repo ->
            val setId = repo.createBurdenEstimateSet(groupId, touchstoneId, scenarioId, defaultProperties, username, timestamp)
            repo.populateBurdenEstimateSet(setId, groupId, touchstoneId, scenarioId, data())
            setId
        }
        withDatabase { db ->
            checkBurdenEstimates(db, setId)
            checkBurdenEstimateSetMetadata(db, setId, returnedIds, "complete")
        }
    }

    @Test
    fun `can populate stochastic burden estimate set`()
    {
        val (setId, modelRunData, returnedIds) = withDatabase { db ->
            val returnedIds = setupDatabase(db)
            val modelRunData = addModelRuns(db, returnedIds.responsibilitySetId, returnedIds.modelVersion!!)
            val setId = db.addBurdenEstimateSet(returnedIds.responsibility, returnedIds.modelVersion,
                    username, setType = "stochastic", timestamp = timestamp, modelRunParameterSetId = modelRunData.runParameterSetId)
            Triple(setId, modelRunData, returnedIds)
        }

        withRepo { repo ->
            val data = data(modelRunData.externalIds)
            repo.populateBurdenEstimateSet(setId, groupId, touchstoneId, scenarioId, data)
        }
        withDatabase { db ->
            checkStochasticBurdenEstimates(db, setId)
            checkBurdenEstimateSetMetadata(db, setId, returnedIds, "complete")
            checkStochasticModelRuns(db, modelRunData)
        }
    }

    @Test
    fun `uses central estimate writer when set type is central`()
    {
        val mockCentralEstimateWriter = mock<BurdenEstimateWriter>()
        val mockStochasticEstimateWriter = mock<StochasticBurdenEstimateWriter>()
        withDatabase { db ->
            setupDatabase(db)
            val data = data()
            val sut = makeRepository(db, mockCentralEstimateWriter, mockStochasticEstimateWriter)
            val setId = sut.createBurdenEstimateSet(groupId, touchstoneId, scenarioId, defaultProperties, username, timestamp)
            sut.populateBurdenEstimateSet(setId, groupId, touchstoneId, scenarioId, data)

            verify(mockCentralEstimateWriter).addEstimatesToSet(setId, data, diseaseId)
            verify(mockStochasticEstimateWriter, times(0)).addEstimatesToSet(setId, data, diseaseId)
        }
    }

    @Test
    fun `uses stochastic estimate writer when set type is stochastic`()
    {
        val mockCentralEstimateWriter = mock<BurdenEstimateWriter>()
        val mockStochasticEstimateWriter = mock<StochasticBurdenEstimateWriter>()
        withDatabase { db ->
            setupDatabase(db)
            val data = data()
            val sut = makeRepository(db, mockCentralEstimateWriter, mockStochasticEstimateWriter)
            val setId = sut.createBurdenEstimateSet(groupId, touchstoneId, scenarioId, defaultStochasticProperties, username, timestamp)
            sut.populateBurdenEstimateSet(setId, groupId, touchstoneId, scenarioId, data)

            verify(mockCentralEstimateWriter, times(0)).addEstimatesToSet(setId, data, diseaseId)
            verify(mockStochasticEstimateWriter).addEstimatesToSet(setId, data, diseaseId)
        }
    }

    @Test
    fun `cannot populate a set if status is complete`()
    {
        JooqContext().use {
            val returnedIds = setupDatabase(it)
            val setId = it.addBurdenEstimateSet(returnedIds.responsibility, returnedIds.modelVersion!!, username, "complete")

            val repo = makeRepository(it)
            assertThatThrownBy {
                repo.populateBurdenEstimateSet(setId, groupId, touchstoneId, scenarioId, data())
            }.isInstanceOf(OperationNotAllowedError::class.java)
                    .hasMessage("the following problems occurred:\nThis burden estimate set already contains estimates." +
                            " You must create a new set if you want to upload any new estimates.")
        }
    }

    @Test
    fun `populate set throws unknown object error if set does not exist`()
    {
        JooqContext().use {
            setupDatabase(it)
            val repo = makeRepository(it)
            assertThatThrownBy {
                repo.populateBurdenEstimateSet(12, groupId, touchstoneId, scenarioId, data())
            }.isInstanceOf(UnknownObjectError::class.java)
        }
    }

}