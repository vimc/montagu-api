package org.vaccineimpact.api.app.repositories

import org.vaccineimpact.api.models.*
import java.time.Instant

interface BurdenEstimateRepository : Repository
{
    val touchstoneRepository: TouchstoneRepository

    /** Returns the database ID of the newly created burden estimate set **/
    fun createBurdenEstimateSet(groupId: String, touchstoneId: String, scenarioId: String,
                                properties: CreateBurdenEstimateSet,
                                uploader: String, timestamp: Instant): Int

    fun getBurdenEstimateSet(setId: Int): BurdenEstimateSet
    fun getBurdenEstimateSets(groupId: String, touchstoneId: String, scenarioId: String): List<BurdenEstimateSet>

    @Deprecated("Instead use createBurdenEstimateSet and then populateBurdenEstimateSet")
    fun addBurdenEstimateSet(groupId: String, touchstoneId: String, scenarioId: String,
                             estimates: Sequence<BurdenEstimate>, uploader: String, timestamp: Instant): Int

    fun populateBurdenEstimateSet(setId: Int, groupId: String, touchstoneId: String, scenarioId: String,
                                  estimates: Sequence<BurdenEstimateWithRunId>)

    fun addModelRunParameterSet(groupId: String, touchstoneId: String, disease: String, description: String,
                                modelRuns: List<ModelRun>,
                                uploader: String, timestamp: Instant): Int

    fun getModelRunParameterSets(groupId: String, touchstoneId: String): List<ModelRunParameterSet>
}
