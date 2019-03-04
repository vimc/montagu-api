package org.vaccineimpact.api.app.repositories

import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.models.BurdenEstimateOutcome
import org.vaccineimpact.api.app.repositories.burdenestimates.BurdenEstimateWriter
import org.vaccineimpact.api.app.repositories.jooq.ResponsibilityInfo
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.serialization.FlexibleDataTable
import java.time.Instant

interface BurdenEstimateRepository : Repository
{
    fun getBurdenEstimateSet(groupId: String, touchstoneVersionId: String, scenarioId: String,
                             burdenEstimateSetId: Int): BurdenEstimateSet

    fun getBurdenEstimateSets(groupId: String, touchstoneVersionId: String, scenarioId: String): List<BurdenEstimateSet>

    fun addModelRunParameterSet(groupId: String, touchstoneVersionId: String, disease: String,
                                modelRuns: List<ModelRun>,
                                uploader: String, timestamp: Instant): Int

    fun getModelRunParameterSets(groupId: String, touchstoneVersionId: String): List<ModelRunParameterSet>
    @Throws(UnknownObjectError::class)
    fun checkModelRunParameterSetExists(modelRunParameterSetId: Int, groupId: String, touchstoneVersionId: String)

    fun getModelRunParameterSet(groupId: String, touchstoneVersionId: String, setId: Int): FlexibleDataTable<ModelRun>
    fun changeBurdenEstimateStatus(setId: Int, newStatus: BurdenEstimateSetStatus)
    fun updateCurrentBurdenEstimateSet(responsibilityId: Int, setId: Int, type: BurdenEstimateSetType)
    fun getEstimateWriter(set: BurdenEstimateSet): BurdenEstimateWriter
    fun getBurdenEstimateSetForResponsibility(setId: Int, responsibilityId: Int): BurdenEstimateSet

    fun getResponsibilityInfo(groupId: String, touchstoneVersionId: String, scenarioId: String): ResponsibilityInfo?

    fun validateEstimates(set: BurdenEstimateSet,
                          expectedRowMap: HashMap<String, HashMap<Short, HashMap<Short, Boolean>>>)
            : HashMap<String, HashMap<Short, HashMap<Short, Boolean>>>
    fun getBurdenOutcomeIds(matching: String): List<Short>

    fun getEstimates(setId: Int, responsibilityId: Int, outcomeIds: List<Short>,
                     burdenEstimateGrouping: BurdenEstimateGrouping = BurdenEstimateGrouping.AGE):
            BurdenEstimateDataSeries

    fun getBurdenEstimateOutcomesSequence(groupId: String, touchstoneVersionId: String, scenarioId: String, burdenEstimateSetId: Int)
            : Sequence<BurdenEstimateOutcome>

    fun getExpectedOutcomesForBurdenEstimateSet(burdenEstimateSetId: Int) : List<String>
    fun addBurdenEstimateSet(responsibilityId: Int, uploader: String, timestamp: Instant, modelVersion: Int, properties: CreateBurdenEstimateSet): Int
}
