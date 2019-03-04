package org.vaccineimpact.api.app.logic

import org.vaccineimpact.api.app.controllers.helpers.ResponsibilityPath
import org.vaccineimpact.api.app.errors.InvalidOperationError
import org.vaccineimpact.api.app.errors.MissingRowsError
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.models.BurdenEstimateOutcome
import org.vaccineimpact.api.app.repositories.*
import org.vaccineimpact.api.app.repositories.jooq.ResponsibilityInfo
import org.vaccineimpact.api.app.validate
import org.vaccineimpact.api.app.validateStochastic
import org.vaccineimpact.api.db.Tables
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.models.responsibilities.ResponsibilitySetStatus
import org.vaccineimpact.api.serialization.FlexibleDataTable
import java.time.Instant

interface BurdenEstimateLogic
{
    fun populateBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String,
                                  estimates: Sequence<BurdenEstimateWithRunId>)

    @Throws(MissingRowsError::class)
    fun closeBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String)

    fun getEstimates(setId: Int,
                     groupId: String,
                     touchstoneVersionId: String,
                     scenarioId: String,
                     outcome: String,
                     burdenEstimateGrouping: BurdenEstimateGrouping = BurdenEstimateGrouping.AGE):
            BurdenEstimateDataSeries

    fun getBurdenEstimateSets(groupId: String, touchstoneVersionId: String, scenarioId: String): List<BurdenEstimateSet>

    fun getBurdenEstimateSet(groupId: String, touchstoneVersionId: String, scenarioId: String, setId: Int): BurdenEstimateSet

    fun getBurdenEstimateData(setId: Int, groupId: String, touchstoneVersionId: String,
                              scenarioId: String): FlexibleDataTable<BurdenEstimate>

    fun validateResponsibilityPath(path: ResponsibilityPath, validTouchstoneStatusList: List<TouchstoneStatus>)
    @Throws(UnknownObjectError::class)
    fun validateGroupAndTouchstone(groupId: String, touchstoneVersionId: String, validTouchstoneStatusList: List<TouchstoneStatus>)
    fun createBurdenEstimateSet(groupId: String, touchstoneVersionId: String, scenarioId: String, properties: CreateBurdenEstimateSet, uploader: String, timestamp: Instant): Int
    fun clearBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String)
}

class RepositoriesBurdenEstimateLogic(private val modellingGroupRepository: ModellingGroupRepository,
                                      private val burdenEstimateRepository: BurdenEstimateRepository,
                                      private val expectationsRepository: ExpectationsRepository,
                                      private val scenarioRepository: ScenarioRepository,
                                      private val touchstoneRepository: TouchstoneRepository) : BurdenEstimateLogic
{
    override fun getBurdenEstimateSets(groupId: String, touchstoneVersionId: String, scenarioId: String): List<BurdenEstimateSet>
    {
        scenarioRepository.checkScenarioDescriptionExists(scenarioId)
        return burdenEstimateRepository.getBurdenEstimateSets(groupId, touchstoneVersionId, scenarioId)
    }

    override fun getBurdenEstimateSet(groupId: String, touchstoneVersionId: String, scenarioId: String, setId: Int): BurdenEstimateSet
    {
        scenarioRepository.checkScenarioDescriptionExists(scenarioId)
        return burdenEstimateRepository.getBurdenEstimateSet(groupId, touchstoneVersionId, scenarioId, setId)
    }

    override fun getEstimates(setId: Int, groupId: String, touchstoneVersionId: String,
                              scenarioId: String,
                              outcome: String,
                              burdenEstimateGrouping: BurdenEstimateGrouping)
            : BurdenEstimateDataSeries
    {
        val group = modellingGroupRepository.getModellingGroup(groupId)
        val responsibilityInfo = getResponsibilityInfo(group.id, touchstoneVersionId, scenarioId)
        val outcomeIds = burdenEstimateRepository.getBurdenOutcomeIds(outcome)
        return burdenEstimateRepository.getEstimates(setId, responsibilityInfo.id, outcomeIds,
                burdenEstimateGrouping)
    }

    override fun getBurdenEstimateData(setId: Int, groupId: String, touchstoneVersionId: String,
                                       scenarioId: String): FlexibleDataTable<BurdenEstimate>
    {
        val data = burdenEstimateRepository.getBurdenEstimateOutcomesSequence(groupId,
                touchstoneVersionId, scenarioId, setId)

        //first, group the outcome rows by disease, year, age, country code and country name
        val groupedRows = data
                .groupBy {
                    hashSetOf(it.disease, it.year, it.age,
                            it.country, it.countryName)
                }

        //get the expected outcomes for this burden estimate set
        val expectedOutcomes = burdenEstimateRepository.getExpectedOutcomesForBurdenEstimateSet(setId)

        //next, map to BurdenEstimate objects, including extracting the cohort size outcome
        val rows = groupedRows.values
                .map {
                    mapBurdenEstimate(it)
                }

        return FlexibleDataTable.new(rows.asSequence(), expectedOutcomes)

    }

    private val COHORT_SIZE_CODE = "cohort_size"
    private fun mapBurdenEstimate(records: List<BurdenEstimateOutcome>)
            : BurdenEstimate
    {
        // all records in thr parameter group should have same disease, year, age, country code and country name
        // so use value from the first row
        val reference = records.first()

        //We should find cohort size as one of the burden outcomes

        val cohortSize = records.firstOrNull { it.burden_outcome_code == COHORT_SIZE_CODE }?.value
        val burdenOutcomeRows = records.filter { it.burden_outcome_code != COHORT_SIZE_CODE }

        val burdenOutcomeValues =
                burdenOutcomeRows.associateBy({ it.burden_outcome_code }, { it.value })

        return BurdenEstimate(
                reference.disease,
                reference.year,
                reference.age,
                reference.country,
                reference.countryName,
                cohortSize ?: 0f,
                burdenOutcomeValues
        )
    }

    override fun closeBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String)
    {
        // Dereference modelling group IDs
        val modellingGroup = modellingGroupRepository.getModellingGroup(groupId)

        // Check all the IDs match up
        val responsibilityInfo = getResponsibilityInfo(modellingGroup.id, touchstoneVersionId, scenarioId)
        val set = burdenEstimateRepository.getBurdenEstimateSetForResponsibility(setId, responsibilityInfo.id)
        if (burdenEstimateRepository.getEstimateWriter(set).isSetEmpty(setId))
        {
            throw InvalidOperationError("This burden estimate set does not have any burden estimate data. " +
                    "It cannot be marked as complete")
        }
        else
        {
            val expectedRows = expectationsRepository.getExpectationsForResponsibility(responsibilityInfo.id)
                    .expectation.expectedRowHashMap()
            val validatedRowMap = burdenEstimateRepository.validateEstimates(set, expectedRows)
            val missingRows = validatedRowMap.filter(::missingRows)
            if (missingRows.any())
            {
                burdenEstimateRepository.changeBurdenEstimateStatus(setId, BurdenEstimateSetStatus.INVALID)
                throw MissingRowsError(rowErrorMessage(missingRows))
            }
            burdenEstimateRepository.changeBurdenEstimateStatus(setId, BurdenEstimateSetStatus.COMPLETE)
        }
    }

    override fun validateResponsibilityPath(
            path: ResponsibilityPath,
            validTouchstoneStatusList: List<TouchstoneStatus>
    )
    {
        validateTouchstone(path.touchstoneVersionId, validTouchstoneStatusList)
        val modellingGroup = modellingGroupRepository.getModellingGroup(path.groupId)
        burdenEstimateRepository.getResponsibilityInfo(modellingGroup.id,
                path.touchstoneVersionId, path.scenarioId)

    }

    override fun validateGroupAndTouchstone(groupId: String, touchstoneVersionId: String,
                                            validTouchstoneStatusList: List<TouchstoneStatus>)
    {
        modellingGroupRepository.getModellingGroup(groupId)
        validateTouchstone(touchstoneVersionId, validTouchstoneStatusList)
    }

    private fun validateTouchstone(touchstoneVersionId: String, validTouchstoneStatusList: List<TouchstoneStatus>) {
        val touchstoneVersion = touchstoneRepository.touchstoneVersions.get(touchstoneVersionId)
        if (!validTouchstoneStatusList.contains(touchstoneVersion.status))
        {
            throw UnknownObjectError(touchstoneVersion.id, TouchstoneVersion::class)
        }
    }

    override fun createBurdenEstimateSet(groupId: String, touchstoneVersionId: String, scenarioId: String,
                                         properties: CreateBurdenEstimateSet,
                                         uploader: String, timestamp: Instant): Int
    {
        // Dereference modelling group IDs
        val modellingGroup = modellingGroupRepository.getModellingGroup(groupId)

        val responsibilityInfo = getResponsibilityInfo(modellingGroup.id, touchstoneVersionId, scenarioId)
        val status = responsibilityInfo.setStatus.toLowerCase()
        if (status == ResponsibilitySetStatus.SUBMITTED.name.toLowerCase())
        {
            throw InvalidOperationError("The burden estimates uploaded for this touchstone have been submitted " +
                    "for review. You cannot upload any new estimates.")
        }

        if (status == ResponsibilitySetStatus.APPROVED.name.toLowerCase())
        {
            throw InvalidOperationError("The burden estimates uploaded for this touchstone have been reviewed" +
                    " and approved. You cannot upload any new estimates.")
        }

        val modelRunParameterSetId = properties.modelRunParameterSet
        if (modelRunParameterSetId != null)
        {
           burdenEstimateRepository.checkModelRunParameterSetExists(modelRunParameterSetId, groupId, touchstoneVersionId)
        }

        val latestModelVersion = modellingGroupRepository.getlatestModelVersion(modellingGroup.id, responsibilityInfo.disease)

        val setId = burdenEstimateRepository.addBurdenEstimateSet(responsibilityInfo.id, uploader, timestamp, latestModelVersion, properties)
        burdenEstimateRepository.updateCurrentBurdenEstimateSet(responsibilityInfo.id, setId, properties.type)

        return setId
    }

    override fun populateBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String,
                                           estimates: Sequence<BurdenEstimateWithRunId>)
    {
        val group = modellingGroupRepository.getModellingGroup(groupId)
        val responsibilityInfo = getResponsibilityInfo(group.id, touchstoneVersionId, scenarioId)
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


    override fun clearBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String)
    {
        // Dereference modelling group IDs
        val modellingGroup = modellingGroupRepository.getModellingGroup(groupId)

        // make sure set belongs to responsibility
        val responsibilityInfo = getResponsibilityInfo(modellingGroup.id, touchstoneVersionId, scenarioId)
        val set = burdenEstimateRepository.getBurdenEstimateSetForResponsibility(setId, responsibilityInfo.id)

        if (set.status == BurdenEstimateSetStatus.COMPLETE)
        {
            throw InvalidOperationError("You cannot clear a burden estimate set which is marked as 'complete'.")
        }

        // We do this first, as this change will be rolled back if the annex
        // changes fail, but the reverse is not true
        burdenEstimateRepository.changeBurdenEstimateStatus(setId, BurdenEstimateSetStatus.EMPTY)
        burdenEstimateRepository.getEstimateWriter(set).clearEstimateSet(setId)
    }

    private fun getResponsibilityInfo(groupId: String, touchstoneVersionId: String, scenarioId: String):
            ResponsibilityInfo
    {
        return burdenEstimateRepository.getResponsibilityInfo(groupId, touchstoneVersionId, scenarioId)?:
                findMissingObjects(touchstoneVersionId, scenarioId)
    }

    private fun <T> findMissingObjects(touchstoneVersionId: String, scenarioId: String): T
    {
        touchstoneRepository.touchstoneVersions.get(touchstoneVersionId)
        scenarioRepository.checkScenarioDescriptionExists(scenarioId)
        // Note this is where the scenario_description *does* exist, but
        // the group is not responsible for it in this touchstoneVersion
        throw UnknownObjectError(scenarioId, "responsibility")
    }

    private fun missingRows(countryMapEntry: Map.Entry<String, HashMap<Short, HashMap<Short, Boolean>>>): Boolean
    {
        return countryMapEntry.value.any { a ->
            a.value.any { y -> !y.value }
        }
    }

    private fun rowErrorMessage(missingRows: Map<String, HashMap<Short, HashMap<Short, Boolean>>>): String
    {
        val countries = missingRows.keys
        val message = "Missing rows for ${countries.joinToString(", ")}"
        val firstRowAges = missingRows[countries.first()]
        val firstMissingAge = firstRowAges!!.keys.first()
        val exampleRows = "For example:\n${countries.first()}, age $firstMissingAge," +
                " year ${firstRowAges[firstMissingAge]!!.keys.first()}"
        return "$message\n$exampleRows"
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