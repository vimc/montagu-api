package org.vaccineimpact.api.app.logic

import org.vaccineimpact.api.app.controllers.helpers.ResponsibilityPath
import org.vaccineimpact.api.app.errors.BadRequest
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
    fun createBurdenEstimateSet(groupId: String, touchstoneVersionId: String, scenarioId: String,
                                properties: CreateBurdenEstimateSet,
                                uploader: String, timestamp: Instant): Int

    fun populateBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String,
                                  estimates: Sequence<BurdenEstimateWithRunId>, filename: String?)

    @Throws(MissingRowsError::class)
    fun closeBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String)

    fun addModelRunParameterSet(groupId: String, touchstoneVersionId: String, disease: String,
                                modelRuns: List<ModelRun>,
                                uploader: String, timestamp: Instant): Int

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
}

class RepositoriesBurdenEstimateLogic(private val modellingGroupRepository: ModellingGroupRepository,
                                      private val burdenEstimateRepository: BurdenEstimateRepository,
                                      private val expectationsRepository: ExpectationsRepository,
                                      private val scenarioRepository: ScenarioRepository,
                                      private val touchstoneRepository: TouchstoneRepository,
                                      private val notifier: Notifier) : BurdenEstimateLogic
{

    constructor(repositories: Repositories, notifier: Notifier = SlackNotifier()) : this(
            repositories.modellingGroup,
            repositories.burdenEstimates,
            repositories.expectations,
            repositories.scenario,
            repositories.touchstone,
            notifier)

    override fun createBurdenEstimateSet(groupId: String, touchstoneVersionId: String, scenarioId: String,
                                         properties: CreateBurdenEstimateSet,
                                         uploader: String, timestamp: Instant): Int
    {
        // Dereference modelling group IDs
        val modellingGroup = modellingGroupRepository.getModellingGroup(groupId)

        val responsibilityInfo = burdenEstimateRepository.getResponsibilityInfo(modellingGroup.id, touchstoneVersionId, scenarioId)
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
            burdenEstimateRepository.checkModelRunParameterSetExists(modelRunParameterSetId, modellingGroup.id, touchstoneVersionId)
        }

        val latestModelVersion = modellingGroupRepository.getLatestModelVersionForGroup(modellingGroup.id, responsibilityInfo.disease)

        return burdenEstimateRepository.createBurdenEstimateSet(responsibilityInfo.id, latestModelVersion, properties, uploader, timestamp)
    }

    override fun addModelRunParameterSet(groupId: String, touchstoneVersionId: String, disease: String,
                                         modelRuns: List<ModelRun>,
                                         uploader: String, timestamp: Instant): Int
    {
        if (!modelRuns.any())
        {
            throw BadRequest("No model runs provided")
        }

        // Dereference modelling group IDs
        val modellingGroup = modellingGroupRepository.getModellingGroup(groupId)
        val modelVersion = modellingGroupRepository.getLatestModelVersionForGroup(modellingGroup.id, disease)

        return burdenEstimateRepository.addModelRunParameterSet(modellingGroup.id,
                touchstoneVersionId, modelVersion, modelRuns, uploader, timestamp)
    }

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
        val responsibilityInfo = burdenEstimateRepository.getResponsibilityInfo(group.id, touchstoneVersionId, scenarioId)
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
        val responsibilityInfo = burdenEstimateRepository.getResponsibilityInfo(modellingGroup.id, touchstoneVersionId, scenarioId)
        val set = burdenEstimateRepository.getBurdenEstimateSetForResponsibility(setId, responsibilityInfo.id)

        if (set.status == BurdenEstimateSetStatus.COMPLETE)
        {
            throw InvalidOperationError("This burden estimate set has already been closed.")
        }

        if (burdenEstimateRepository.getEstimateWriter(set).isSetEmpty(setId))
        {
            throw InvalidOperationError("This burden estimate set does not have any burden estimate data. " +
                    "It cannot be marked as complete")
        }
        else
        {
            val expectedRows = expectationsRepository.getExpectationsForResponsibility(responsibilityInfo.id)
                    .expectation.expectedRowLookup()
            val validatedRowMap = burdenEstimateRepository.validateEstimates(set, expectedRows)
            val countriesWithMissingRows = validatedRowMap.filter { it.hasMissingAges() }
            if (countriesWithMissingRows.any())
            {
                burdenEstimateRepository.changeBurdenEstimateStatus(setId, BurdenEstimateSetStatus.INVALID)
                throw MissingRowsError(rowErrorMessage(countriesWithMissingRows))
            }
            burdenEstimateRepository.changeBurdenEstimateStatus(setId, BurdenEstimateSetStatus.COMPLETE)
        }
    }

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

    private fun rowErrorMessage(countriesWithMissingRows: Map<String, AgeLookup>): String
    {
        val countries = countriesWithMissingRows.keys
        val exampleRowLookup = countriesWithMissingRows.values.first()
        val firstAgeWithMissingYears = exampleRowLookup.firstAgeWithMissingRows()
        val firstMissingYear = exampleRowLookup.getValue(firstAgeWithMissingYears).firstMissingYear()

        val basicMessage = "Missing rows for ${countries.joinToString(", ")}"
        val exampleRowMessage = "For example:\n${countries.first()}, age $firstAgeWithMissingYears," +
                " year $firstMissingYear"
        return "$basicMessage\n$exampleRowMessage"
    }

    override fun populateBurdenEstimateSet(setId: Int, groupId: String, touchstoneVersionId: String, scenarioId: String,
                                           estimates: Sequence<BurdenEstimateWithRunId>, filename: String?)
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

        if (filename != null)
        {
            burdenEstimateRepository.updateBurdenEstimateSetFilename(setId, filename)
        }

        notifier.notify("A burden estimate set has just been uploaded for ${group.id}")
    }

    private fun populateCentralBurdenEstimateSet(set: BurdenEstimateSet, responsibilityInfo: ResponsibilityInfo,
                                                 estimates: Sequence<BurdenEstimateWithRunId>)
    {
        val expectedRows = expectationsRepository.getExpectationsForResponsibility(responsibilityInfo.id)
                .expectation.expectedRowLookup()

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