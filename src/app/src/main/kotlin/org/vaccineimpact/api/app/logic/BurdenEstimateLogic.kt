package org.vaccineimpact.api.app.logic

import org.vaccineimpact.api.app.errors.InvalidOperationError
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.ExpectationsRepository
import org.vaccineimpact.api.app.repositories.ModellingGroupRepository
import org.vaccineimpact.api.app.repositories.jooq.ResponsibilityInfo
import org.vaccineimpact.api.app.validate
import org.vaccineimpact.api.app.validateStochastic
import org.vaccineimpact.api.models.BurdenEstimateSet
import org.vaccineimpact.api.models.BurdenEstimateSetStatus
import org.vaccineimpact.api.models.BurdenEstimateWithRunId

interface BurdenEstimateLogic
{
    fun populateBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String,
                                  estimates: Sequence<BurdenEstimateWithRunId>)

    fun closeBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String)
}

class RepositoriesBurdenEstimateLogic(private val modellingGroupRepository: ModellingGroupRepository,
                                      private val burdenEstimateRepository: BurdenEstimateRepository,
                                      private val expectationsRepository: ExpectationsRepository) : BurdenEstimateLogic
{
    override fun closeBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String)
    {
        // Dereference modelling group IDs
        val modellingGroup = modellingGroupRepository.getModellingGroup(groupId)

        // Check all the IDs match up
        val responsibilityInfo = burdenEstimateRepository.getResponsibilityInfo(modellingGroup.id, touchstoneVersionId, scenarioId)
        val set = burdenEstimateRepository.getBurdenEstimateSetForResponsibility(setId, responsibilityInfo.id)
        if (burdenEstimateRepository.getEstimateWriter(set).isSetEmpty(setId))
        {
            throw InvalidOperationError("This burden estimate set does not have any burden estimate data. " +
                    "It cannot be marked as complete")
        }
        else
        {
            burdenEstimateRepository.changeBurdenEstimateStatus(setId, BurdenEstimateSetStatus.COMPLETE)
        }
    }

    override fun populateBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String,
                                           estimates: Sequence<BurdenEstimateWithRunId>)
    {
        val group = modellingGroupRepository.getModellingGroup(groupId)
        val responsibilityInfo = burdenEstimateRepository.getResponsibilityInfo(group.id, touchstoneVersionId, scenarioId)
        val set = burdenEstimateRepository.getBurdenEstimateSetForResponsibility(setId, responsibilityInfo.id)

        if (set.status == BurdenEstimateSetStatus.COMPLETE)
        {
            throw InvalidOperationError("This burden estimate set has been marked as complete." +
                    " You must create a new set if you want to upload any new estimates.")
        }

        if (set.isStochastic())
        {
            populateStochasticBurdenEstimateSet(set, responsibilityInfo, estimates)
        }
        else
        {
            populateCentralBurdenEstimateSet(set, responsibilityInfo, estimates)
        }

        burdenEstimateRepository.changeBurdenEstimateStatus(setId, BurdenEstimateSetStatus.PARTIAL)
        burdenEstimateRepository.updateCurrentBurdenEstimateSet(responsibilityInfo.id, setId, set.type)
    }

    private fun populateCentralBurdenEstimateSet(set: BurdenEstimateSet, responsibilityInfo: ResponsibilityInfo,
                                                 estimates: Sequence<BurdenEstimateWithRunId>)
    {
        val expectedRows = expectationsRepository.getExpectationsForResponsibility(responsibilityInfo.id)
                .expectation.expectedRowHashMap()


        val validatedEstimates = estimates.validate(expectedRows)

        burdenEstimateRepository.getEstimateWriter(set).addEstimatesToSet(set.id, validatedEstimates, responsibilityInfo.disease)
    }

    private fun populateStochasticBurdenEstimateSet(set: BurdenEstimateSet, responsibilityInfo: ResponsibilityInfo,
                                                    estimates: Sequence<BurdenEstimateWithRunId>)
    {
        val validatedEstimates = estimates.validateStochastic()
        burdenEstimateRepository.getEstimateWriter(set)
                .addEstimatesToSet(set.id, validatedEstimates, responsibilityInfo.disease)
    }
}