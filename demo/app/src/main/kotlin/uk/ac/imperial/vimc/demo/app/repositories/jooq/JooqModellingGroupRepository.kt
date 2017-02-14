package uk.ac.imperial.vimc.demo.app.repositories.jooq

import org.jooq.Record
import org.jooq.SelectConditionStep
import uk.ac.imperial.vimc.demo.app.errors.UnknownObject
import uk.ac.imperial.vimc.demo.app.extensions.fetchInto
import uk.ac.imperial.vimc.demo.app.extensions.fieldsAsList
import uk.ac.imperial.vimc.demo.app.filters.ScenarioFilterParameters
import uk.ac.imperial.vimc.demo.app.filters.whereMatchesFilter
import uk.ac.imperial.vimc.demo.app.models.*
import uk.ac.imperial.vimc.demo.app.models.jooq.Tables.*
import uk.ac.imperial.vimc.demo.app.models.jooq.tables.records.CoverageScenarioDescriptionRecord
import uk.ac.imperial.vimc.demo.app.models.jooq.tables.records.ModelRecord
import uk.ac.imperial.vimc.demo.app.models.jooq.tables.records.ModellingGroupRecord
import uk.ac.imperial.vimc.demo.app.repositories.DataSet
import uk.ac.imperial.vimc.demo.app.repositories.ModellingGroupRepository

class JooqModellingGroupRepository : JooqRepository(), ModellingGroupRepository
{
    override val modellingGroups: DataSet<ModellingGroup, Int>
        get() = JooqDataSet.new(dsl, MODELLING_GROUP, { it.ID }, this::mapModellingGroup)

    private fun groupHasCode(groupCode: String) = MODELLING_GROUP.CODE.eq(groupCode)

    override fun getModellingGroupByCode(groupCode: String): ModellingGroup
    {
        val record = dsl.fetchAny(MODELLING_GROUP, groupHasCode(groupCode))
                ?: throw UnknownObject(groupCode, ModellingGroup::class.simpleName!!)
        return mapModellingGroup(record)
    }

    override fun getModels(groupCode: String): List<VaccineModel>
    {
        return dsl.select()
                .from(MODEL)
                .join(MODELLING_GROUP).onKey()
                .where(groupHasCode(groupCode))
                .fetchInto<ModelRecord>()
                .map { VaccineModel(it.id, it.name, it.citation, it.description) }
    }

    override fun getResponsibilities(groupCode: String, scenarioFilterParameters: ScenarioFilterParameters): Responsibilities
    {
        val group = getModellingGroupByCode(groupCode)
        val responsibility_set = dsl.fetchAny(RESPONSIBILITY_SET,
                RESPONSIBILITY_SET.MODELLING_GROUP.eq(group.id))
        val scenarios = dsl
                .select(COVERAGE_SCENARIO_DESCRIPTION.fieldsAsList())
                .fromJoinPath(RESPONSIBILITY, COVERAGE_SCENARIO, COVERAGE_SCENARIO_DESCRIPTION)
                .where(RESPONSIBILITY.RESPONSIBILITY_SET.eq(responsibility_set.id))
                .whereMatchesFilter(JooqScenarioFilter(), scenarioFilterParameters)
                .fetchInto<CoverageScenarioDescriptionRecord>()
                .map { JooqScenarioRepository.scenarioMapper(it) }
                .map(::Responsibility)
        return Responsibilities(group, scenarios, responsibility_set.complete)
    }

    override fun getEstimateListing(groupCode: String, scenarioFilterParameters: ScenarioFilterParameters): ModellingGroupEstimateListing
    {
        val group = getModellingGroupByCode(groupCode)
        val impactEstimates = getImpactEstimateDescriptions(groupCode).map(this::mapImpactEstimateSet)
        return ModellingGroupEstimateListing(group, impactEstimates)
    }

    override fun getEstimate(groupCode: String, estimateId: Int): ImpactEstimateDataAndGroup
    {
        val group = getModellingGroupByCode(groupCode)
        val estimateDescription = getImpactEstimateDescriptions(groupCode)
                .and(IMPACT_ESTIMATE_SET.ID.eq(estimateId))
                .map(this::mapImpactEstimateSet)
                .singleOrNull() ?: throw UnknownObject(estimateId, "ImpactEstimateSet")
        val outcomes = dsl.select()
                .fromJoinPath(IMPACT_ESTIMATE, OUTCOME)
                .where(IMPACT_ESTIMATE.IMPACT_ESTIMATE_SET.eq(estimateId))
                .and(OUTCOME.CODE.eq("Deaths"))
                .groupBy { it[IMPACT_ESTIMATE.COUNTRY] }
                .map(this::mapCountryOutcomes)
        return ImpactEstimateDataAndGroup(group, estimateDescription, outcomes)
    }

    override fun createEstimate(groupCode: String, data: NewImpactEstimate): ImpactEstimateDataAndGroup
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getImpactEstimateDescriptions(groupCode: String): SelectConditionStep<Record> = dsl
            .select(IMPACT_ESTIMATE_SET.ID, IMPACT_ESTIMATE_SET.UPLOADED_TIMESTAMP)
            .select(MODEL_VERSION.VERSION, MODEL.NAME)
            .select(COVERAGE_SCENARIO_DESCRIPTION.fieldsAsList())
            .fromJoinPath(MODELLING_GROUP, RESPONSIBILITY_SET, RESPONSIBILITY)
            .joinPath(RESPONSIBILITY, IMPACT_ESTIMATE_SET, MODEL_VERSION, MODEL)
            .joinPath(RESPONSIBILITY, COVERAGE_SCENARIO, COVERAGE_SCENARIO_DESCRIPTION)
            .where(groupHasCode(groupCode))

    private fun mapModellingGroup(x: ModellingGroupRecord) = ModellingGroup(x.id, x.code, x.description)

    private fun mapImpactEstimateSet(record: Record): ImpactEstimateDescription = ImpactEstimateDescription(
            record[IMPACT_ESTIMATE_SET.ID],
            JooqScenarioRepository.scenarioMapper(record),
            ModelIdentifier(record[MODEL.NAME], record[MODEL_VERSION.VERSION]),
            record[IMPACT_ESTIMATE_SET.UPLOADED_TIMESTAMP].toInstant()
    )

    private fun mapCountryOutcomes(groupedData: Map.Entry<String, Iterable<Record>>): CountryOutcomes {
        val (country, data) = groupedData
        return CountryOutcomes(country, data.map { Outcome(
                year = it[IMPACT_ESTIMATE.YEAR],
                numberOfDeaths = it[IMPACT_ESTIMATE.VALUE]?.toInt()
        )})
    }
}