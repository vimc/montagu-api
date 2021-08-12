package org.vaccineimpact.api.app.logic

import org.vaccineimpact.api.app.controllers.helpers.ResponsibilityPath
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.repositories.*
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.models.responsibilities.ResponsibilityRow

interface ResponsibilitiesLogic
{
    fun validateResponsibilityPath(path: ResponsibilityPath, validTouchstoneStatusList: List<TouchstoneStatus>)
    fun getTouchstoneResponsibilitiesData(touchstoneVersionId: String): List<ResponsibilityRow>
}

class RepositoriesResponsibilitiesLogic(
        private val modellingGroupRepository: ModellingGroupRepository,
        private val scenarioRepository: ScenarioRepository,
        private val touchstoneRepository: TouchstoneRepository,
        private val responsibilitiesRepository: ResponsibilitiesRepository,
        private val burdenEstimateRepository: BurdenEstimateRepository,
        private val expectationsRepository: ExpectationsRepository
) : ResponsibilitiesLogic
{

    override fun validateResponsibilityPath(
            path: ResponsibilityPath,
            validTouchstoneStatusList: List<TouchstoneStatus>
    )
    {
        //Check that modelling group exists
        modellingGroupRepository.getModellingGroup(path.groupId)
        //Check touchstone and is accessible for this user
        val touchstoneVersion = touchstoneRepository.touchstoneVersions.get(path.touchstoneVersionId)
        if (!validTouchstoneStatusList.contains(touchstoneVersion.status))
        {
            throw UnknownObjectError(touchstoneVersion.id, TouchstoneVersion::class)
        }

        scenarioRepository.checkScenarioDescriptionExists(path.scenarioId)
    }

    override fun getTouchstoneResponsibilitiesData(touchstoneVersionId: String): List<ResponsibilityRow>
    {
        val comments = responsibilitiesRepository.getResponsibilitiesWithCommentsForTouchstone(touchstoneVersionId)
        val sets = responsibilitiesRepository.getResponsibilitiesForTouchstone(touchstoneVersionId)
        return sets.flatMap { set ->
            val responsibilitySetComment = comments.find { it.modellingGroupId == set.modellingGroupId}?.comment
            set.responsibilities.map { responsibility ->
                val currentEstimateSet = responsibility.currentEstimateSet
                val responsibilityComment = comments.find { it.modellingGroupId == set.modellingGroupId }?.responsibilities?.find { it.scenarioId == responsibility.scenario.id }?.comment
                // At this point we have a responsibility (a specific scenario within a responsibility set), but not its database identifier relating to a particular touchstone version
                val responsibilityInfo = burdenEstimateRepository.getResponsibilityInfo(set.modellingGroupId, touchstoneVersionId, responsibility.scenario.id)
                // Now that we have a concrete responsibility we can identify its corresponding expectations i.e. countries/ages/years
                val expectationMapping = expectationsRepository.getExpectationsForResponsibility(responsibilityInfo.id)
                // And from that we can establish which of the expectations have been fulfilled, giving a Map<Country, Map<Age, Map<Year, Boolean>>>
                val validatedRowMap = if (currentEstimateSet != null) burdenEstimateRepository.validateEstimates(currentEstimateSet, expectationMapping.expectation.expectedRowLookup()) else null
                ResponsibilityRow(
                        set.touchstoneVersion,
                        set.modellingGroupId,
                        set.responsibilities.size - set.responsibilities.count { it.currentEstimateSet?.status == BurdenEstimateSetStatus.COMPLETE },
                        responsibilitySetComment?.comment,
                        responsibilitySetComment?.addedOn,
                        responsibilitySetComment?.addedBy,
                        responsibility.scenario.description,
                        responsibility.scenario.disease,
                        currentEstimateSet?.id,
                        currentEstimateSet?.uploadedOn,
                        currentEstimateSet?.uploadedBy,
                        currentEstimateSet?.type?.type,
                        currentEstimateSet?.type?.details,
                        // Here we're effectively counting the number of false leaf nodes in the tree (nested map) of expectations
                        validatedRowMap?.values?.flatMap { age -> age.values.flatMap { year -> year.values } }?.count { !it },
                        responsibilityComment?.comment,
                        responsibilityComment?.addedOn,
                        responsibilityComment?.addedBy
                )
            }
        }
    }
}
