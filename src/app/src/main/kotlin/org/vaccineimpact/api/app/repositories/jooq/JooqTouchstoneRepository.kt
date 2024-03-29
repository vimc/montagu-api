package org.vaccineimpact.api.app.repositories.jooq

import org.jooq.*
import org.jooq.Result
import org.jooq.impl.DSL.*
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.filters.ScenarioFilterParameters
import org.vaccineimpact.api.app.filters.whereMatchesFilter
import org.vaccineimpact.api.app.getLongCoverageRowDataTable
import org.vaccineimpact.api.app.repositories.ScenarioRepository
import org.vaccineimpact.api.app.repositories.SimpleDataSet
import org.vaccineimpact.api.app.repositories.TouchstoneRepository
import org.vaccineimpact.api.app.repositories.jooq.mapping.MappingHelper
import org.vaccineimpact.api.db.*
import org.vaccineimpact.api.db.Tables.*
import org.vaccineimpact.api.db.tables.records.CoverageRecord
import org.vaccineimpact.api.db.tables.records.DemographicStatisticTypeRecord
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.serialization.DataTable
import org.vaccineimpact.api.serialization.SplitData
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.Instant
import kotlin.sequences.Sequence

class JooqTouchstoneRepository(
        dsl: DSLContext,
        private val scenarioRepository: ScenarioRepository,
        private val mapper: MappingHelper = MappingHelper()
) : JooqRepository(dsl), TouchstoneRepository
{
    override fun getTouchstones(): List<Touchstone>
    {
        return dsl.select(TOUCHSTONE_NAME.fieldsAsList())
                .select(TOUCHSTONE.fieldsAsList())
                .fromJoinPath(TOUCHSTONE_NAME, TOUCHSTONE)
                .asSequence()
                .groupBy { it[TOUCHSTONE_NAME.ID] }
                .map { mapTouchstone(it.value) }
    }

    override val touchstoneVersions: SimpleDataSet<TouchstoneVersion, String>
        get() = JooqSimpleDataSet.new(dsl, TOUCHSTONE, { it.ID }, { mapTouchstoneVersion(it) })

    override fun getDemographicData(statisticTypeCode: String,
                                    source: String,
                                    touchstoneVersionId: String,
                                    gender: String): SplitData<DemographicDataForTouchstone, LongDemographicRow>
    {
        val metadata = getDemographicMetadata(statisticTypeCode, source, touchstoneVersionId, gender)
        val data = getDemographicStatistics(touchstoneVersionId, statisticTypeCode, source, gender)
                .orderBy(DEMOGRAPHIC_STATISTIC.COUNTRY, DEMOGRAPHIC_STATISTIC.YEAR, DEMOGRAPHIC_STATISTIC.AGE_FROM)
                .fetchSequence()
                .map { mapDemographicRow(it) }
        return SplitData(metadata, DataTable.new(data))
    }

    private fun getDemographicMetadata(
            statisticTypeCode: String,
            source: String,
            touchstoneVersionId: String,
            gender: String
    ): DemographicDataForTouchstone
    {
        val statisticType = getDemographicStatisticType(statisticTypeCode)
                .fetchAny() ?: throw UnknownObjectError(statisticTypeCode, "demographic-statistic-type")
        val touchstoneVersion = touchstoneVersions.get(touchstoneVersionId)
        val countries = dsl.selectDistinct(TOUCHSTONE_COUNTRY.COUNTRY)
                .from(TOUCHSTONE_COUNTRY)
                .where(TOUCHSTONE_COUNTRY.TOUCHSTONE.eq(touchstoneVersionId))
                .fetch()
                .map { it.value1() }

        val type = DEMOGRAPHIC_STATISTIC_TYPE
        val metadata = DemographicMetadata(
                statisticType[type.CODE],
                statisticType[type.NAME],
                Gender.ifApplicable(gender, statisticType[type.GENDER_IS_APPLICABLE]),
                countries,
                statisticType[DEMOGRAPHIC_VALUE_UNIT.NAME],
                statisticType[type.AGE_INTERPRETATION],
                source
        )
        return DemographicDataForTouchstone(touchstoneVersion, metadata)
    }

    override fun getDemographicDatasets(touchstoneVersionId: String): List<DemographicDataset>
    {
        val records = getDemographicDatasetsForTouchstone(touchstoneVersionId)
                .fetch()

        return records.map {
            mapDemographicDataset(it)
        }.sortedBy { it.name }
    }

    override fun getScenariosAndCoverageSets(touchstoneVersionId: String, filterParams: ScenarioFilterParameters): List<ScenarioAndCoverageSets>
    {
        val records = getScenariosAndCoverageSets(touchstoneVersionId)
                .whereMatchesFilter(JooqScenarioFilter(), filterParams)
                // first by scenario, then by coverage set order within the scenario
                .orderBy(SCENARIO_DESCRIPTION.ID, SCENARIO_COVERAGE_SET.ORDER)
                .fetch()

        return getScenariosFromRecords(records).map {
            ScenarioAndCoverageSets(it, getCoverageSetsFromRecord(records, it))
        }
    }

    override fun getScenarioAndCoverageSets(touchstoneVersionId: String, scenarioDescId: String): ScenarioAndCoverageSets
    {
        val records = getScenariosAndCoverageSets(touchstoneVersionId)
                .and(SCENARIO_DESCRIPTION.ID.eq(scenarioDescId))
                .orderBy(SCENARIO_COVERAGE_SET.ORDER)
                .fetch()

        val scenario = getScenariosFromRecords(records).singleOrNull()
                ?: throw UnknownObjectError(scenarioDescId, "scenario")
        return ScenarioAndCoverageSets(scenario, getCoverageSetsFromRecord(records, scenario))
    }

    private fun getScenariosAndCoverageSets(touchstoneVersionId: String): SelectConditionStep<Record>
    {
        return dsl
                .select(SCENARIO_DESCRIPTION.fieldsAsList())
                .select(COVERAGE_SET.fieldsAsList())
                .select(TOUCHSTONE.ID)
                .select(SCENARIO_COVERAGE_SET.ORDER)
                .fromJoinPath(TOUCHSTONE, SCENARIO, SCENARIO_DESCRIPTION)
                // We don't mind if there are no coverage sets, so do a left join
                .joinPath(SCENARIO, SCENARIO_COVERAGE_SET, COVERAGE_SET, joinType = JoinType.LEFT_OUTER_JOIN)
                .where(TOUCHSTONE.ID.eq(touchstoneVersionId))

    }

    private fun getCoverageSets(touchstoneVersionId: String):
            Result<Record4<Int, String, String, String>>
    {
        return dsl
                .select(COVERAGE_SET.ID,
                        COVERAGE_SET.ACTIVITY_TYPE,
                        COVERAGE_SET.GAVI_SUPPORT_LEVEL,
                        COVERAGE_SET.VACCINE)
                .fromJoinPath(COVERAGE_SET, TOUCHSTONE)
                .where(TOUCHSTONE.ID.eq(touchstoneVersionId))
                .fetch()
    }

    override fun getScenarioAndCoverageData(
            touchstoneVersionId: String,
            scenarioDescId: String
    ): SplitData<ScenarioAndCoverageSets, LongCoverageRow>
    {
        val scenario = scenarioRepository.getScenarioForTouchstone(touchstoneVersionId, scenarioDescId)

        val coverageSets = getCoverageSetsForScenario(touchstoneVersionId, scenarioDescId)
        val coverageData = getCoverageDataForScenario(touchstoneVersionId, scenarioDescId)
        val metadata = ScenarioAndCoverageSets(scenario, coverageSets)

        return SplitData(metadata, getLongCoverageRowDataTable(coverageData.asSequence()))
    }

    override fun getCoverageDataForScenario(
            touchstoneVersionId: String,
            scenarioDescriptionId: String
    ): Sequence<LongCoverageRow>
    {
        val coverageRecords = getCoverageRowsForScenario(touchstoneVersionId, scenarioDescriptionId)
                .filter { it[COVERAGE_SET.ID] != null }

        val diseaseId = getDiseaseIdForScenarioDescription(scenarioDescriptionId)

        return coverageRecords
                .filter { it[COUNTRY.NAME] != null }
                .map { mapCoverageRow(it, scenarioDescriptionId, touchstoneVersionId, diseaseId) }.asSequence()
    }

    override fun getCoverageDataForResponsibility(
            touchstoneVersionId: String,
            responsibilityId: Int,
            scenarioDescriptionId: String
    ): Sequence<LongCoverageRow>
    {
        val coverageRecords = getCoverageRowsForResponsibility(responsibilityId, touchstoneVersionId)
                .filter { it[COVERAGE_SET.ID] != null }

        val diseaseId = getDiseaseIdForScenarioDescription(scenarioDescriptionId)

        return coverageRecords
                .filter { it[COUNTRY.NAME] != null }
                .map { mapCoverageRow(it, scenarioDescriptionId, touchstoneVersionId, diseaseId) }.asSequence()
    }

    override fun getCoverageSetsForScenario(
            touchstoneVersionId: String,
            scenarioDescriptionId: String)
            : List<CoverageSet>
    {
        val records = dsl
                .select(COVERAGE_SET.fieldsAsList())
                .select(TOUCHSTONE.ID)
                .fromJoinPath(TOUCHSTONE, SCENARIO)
                .joinPath(SCENARIO, SCENARIO_COVERAGE_SET, COVERAGE_SET)
                .where(TOUCHSTONE.ID.eq(touchstoneVersionId))
                .and(SCENARIO.SCENARIO_DESCRIPTION.eq(scenarioDescriptionId))
                .orderBy(SCENARIO_COVERAGE_SET.ORDER)
                .fetch()

        return records.map { mapCoverageSet(it) }
    }

    override fun createCoverageSetMetadata(description: String,
                                           uploader: String,
                                           timestamp: Instant): Int
    {
        val record = this.dsl.newRecord(COVERAGE_SET_UPLOAD_METADATA).apply {
            this.description = description
            this.uploadedBy = uploader
            this.uploadedOn = Timestamp.from(timestamp)
        }
        record.store()
        return record.id
    }

    override fun createCoverageSet(touchstoneVersionId: String,
                                   vaccine: String,
                                   activityType: ActivityType,
                                   supportLevel: GAVISupportLevel,
                                   metadataId: Int): Int
    {
        val support = supportLevel.toString().lowercase()
        val activity = activityType.toString().lowercase().replace('_', '-')
        val record = this.dsl.newRecord(COVERAGE_SET).apply {
            this.touchstone = touchstoneVersionId
            this.name = "$vaccine: $vaccine, $support, $activity"
            this.vaccine = vaccine
            this.gaviSupportLevel = support
            this.activityType = activity
            this.coverageSetUploadMetadata = metadataId
        }
        record.store()
        return record.id
    }

    override fun getCoverageUploadMetadata(touchstoneVersionId: String): List<CoverageUploadMetadata>
    {
        return dsl.select(COVERAGE_SET.VACCINE,
                ACTIVITY_TYPE.NAME.`as`("activityType"),
                COVERAGE_SET_UPLOAD_METADATA.UPLOADED_BY,
                COVERAGE_SET_UPLOAD_METADATA.UPLOADED_ON)
                .fromJoinPath(COVERAGE_SET, COVERAGE_SET_UPLOAD_METADATA)
                .joinPath(COVERAGE_SET, ACTIVITY_TYPE)
                .where(COVERAGE_SET.TOUCHSTONE.eq(touchstoneVersionId))
                .orderBy(COVERAGE_SET.VACCINE, COVERAGE_SET.ACTIVITY_TYPE)
                .fetchInto(CoverageUploadMetadata::class.java)
                .groupBy { Pair(it.vaccine, it.activityType) }
                .map { g ->
                    g.value.maxByOrNull { it.uploadedOn }!!
                }
    }

    override fun saveCoverageForTouchstone(touchstoneVersionId: String, records: List<CoverageRecord>)
    {
        dsl.batchStore(records).execute()
    }

    override fun newCoverageRowRecord(coverageSetId: Int,
                                      country: String,
                                      year: Int,
                                      ageFrom: BigDecimal,
                                      ageTo: BigDecimal,
                                      gender: Int,
                                      gaviSupport: Boolean,
                                      target: BigDecimal?,
                                      coverage: BigDecimal?,
                                      subnational: Boolean) = this.dsl.newRecord(COVERAGE).apply {
        this.coverageSet = coverageSetId
        this.country = country
        this.year = year
        this.ageFrom = ageFrom
        this.ageTo = ageTo
        this.target = target
        this.coverage = coverage
        this.gender = gender
        this.gaviSupport = gaviSupport
        this.subnational = subnational
    }

    private fun coverageDimensions(): Array<Field<*>>
    {
        //The columns in the Coverage table which the target and coverage values are grouped by
        return arrayOf(COVERAGE.COVERAGE_SET,
                COVERAGE.YEAR,
                COVERAGE.COUNTRY,
                COVERAGE.AGE_FROM,
                COVERAGE.AGE_TO,
                COVERAGE.AGE_RANGE_VERBATIM,
                COVERAGE.GAVI_SUPPORT,
                COVERAGE.GENDER,
                COVERAGE.PROPORTION_RISK)
    }

    private fun aggregatedValues(): List<Field<*>>
    {
        return arrayOf(
                aggregatedCoverage().`as`("coverage"),
                aggregatedTarget().`as`("target")
        ).toList()
    }


    private fun aggregatedCoverage(): Field<BigDecimal?>
    {
        //Aggregated coverage - the sum of each row's coverage * target (to get total no of fvp's across campaign)
        //divided by the the total target population
        //If only one row in group, pass that row's value through unchanged
        //Danger of divide by zero error here - treat as NULL if summed target is 0
        return `when`(count(COVERAGE.COVERAGE_SET).eq(1), max(COVERAGE.COVERAGE_)) //If only one row in group
                .otherwise(sum(validTargetOrNull().mul(validCoverageOrNull()))
                        .div(nullif(sum(validTargetOrNull()), 0.toDecimal())))

    }

    private fun aggregatedTarget(): Field<BigDecimal?>
    {
        //If only one row in group, pass that row's value through unchanged
        return `when`(count(COVERAGE.COVERAGE_SET).eq(1), max(COVERAGE.TARGET)) //If only one row in group
                .otherwise(sum(validTargetOrNull()))
    }

    private fun validTargetOrNull(): Field<BigDecimal?>
    {
        //Coverage and target values only make sense if both are provided. Return the target value of a row providing
        //coverage is not null, else return null
        return `when`(COVERAGE.COVERAGE_.isNull(), COVERAGE.COVERAGE_) //This is actually the easiest way to get a known NULL here!
                .otherwise(COVERAGE.TARGET)
    }

    private fun validCoverageOrNull(): Field<BigDecimal?>
    {
        //Coverage and target values only make sense if both are provided. Return the coverage value of a row providing
        //target is not null, else return null
        return `when`(COVERAGE.TARGET.isNull(), COVERAGE.TARGET) //This is actually the easiest way to get a known NULL here!
                .otherwise(COVERAGE.COVERAGE_)
    }

    private fun getCoverageRowsForScenario(
            touchstoneVersionId: String,
            scenarioDescriptionId: String)
            : Result<Record>
    {
        //This query is now grouped to support sub-national campaigns
        return dsl
                .select(COVERAGE_SET.fieldsAsList())
                .select(coverageDimensions().toList())
                .select(aggregatedValues())
                .select(COUNTRY.NAME)
                .select(GENDER.NAME)
                .fromJoinPath(TOUCHSTONE, SCENARIO)
                // We don't mind if there are no coverage sets, so do a left join
                .joinPath(SCENARIO, SCENARIO_COVERAGE_SET, COVERAGE_SET, joinType = JoinType.LEFT_OUTER_JOIN)
                .joinPath(COVERAGE_SET, COVERAGE, joinType = JoinType.LEFT_OUTER_JOIN)
                .joinPath(COVERAGE, COUNTRY, joinType = JoinType.LEFT_OUTER_JOIN)
                .joinPath(COVERAGE, GENDER, joinType = JoinType.LEFT_OUTER_JOIN)
                .where(TOUCHSTONE.ID.eq(touchstoneVersionId))
                .and(SCENARIO.SCENARIO_DESCRIPTION.eq(scenarioDescriptionId))
                .groupBy(*COVERAGE_SET.fields(),
                        *coverageDimensions(),
                        COUNTRY.NAME,
                        GENDER.NAME)
                .orderBy(COVERAGE_SET.VACCINE, COVERAGE_SET.ACTIVITY_TYPE,
                        COVERAGE.COUNTRY, COVERAGE.YEAR, COVERAGE.AGE_FROM, COVERAGE.AGE_TO,
                        COVERAGE.AGE_RANGE_VERBATIM,
                        GENDER.NAME).fetch()


    }

    private fun getCoverageRowsForResponsibility(
            responsibilityId: Int,
            touchstoneVersionId: String)
            : Result<Record>
    {
        val expectedCountries = dsl.select(COUNTRY.ID)
                .fromJoinPath(COUNTRY, BURDEN_ESTIMATE_COUNTRY_EXPECTATION, BURDEN_ESTIMATE_EXPECTATION,
                        RESPONSIBILITY)
                .where(RESPONSIBILITY.ID.eq(responsibilityId))

        //This query is now grouped to support sub-national campaigns
        return dsl
                .select(COVERAGE_SET.fieldsAsList())
                .select(coverageDimensions().toList())
                .select(aggregatedValues())
                .select(COUNTRY.NAME)
                .select(GENDER.NAME)
                .fromJoinPath(TOUCHSTONE, SCENARIO, RESPONSIBILITY)
                // We don't mind if there are no coverage sets, so do a left join
                .joinPath(SCENARIO, SCENARIO_COVERAGE_SET, COVERAGE_SET, joinType = JoinType.LEFT_OUTER_JOIN)
                .joinPath(COVERAGE_SET, COVERAGE, joinType = JoinType.LEFT_OUTER_JOIN)
                .joinPath(COVERAGE, COUNTRY, joinType = JoinType.LEFT_OUTER_JOIN)
                .joinPath(COVERAGE, GENDER, joinType = JoinType.LEFT_OUTER_JOIN)
                .where(TOUCHSTONE.ID.eq(touchstoneVersionId))
                .and(RESPONSIBILITY.ID.eq(responsibilityId))
                .and(COUNTRY.ID.isNull.or(COUNTRY.ID.`in`(expectedCountries)))
                .groupBy(*COVERAGE_SET.fields(),
                        *coverageDimensions(),
                        COUNTRY.NAME,
                        GENDER.NAME)
                .orderBy(COVERAGE_SET.VACCINE, COVERAGE_SET.ACTIVITY_TYPE,
                        COVERAGE.COUNTRY, COVERAGE.YEAR, COVERAGE.AGE_FROM, COVERAGE.AGE_TO,
                        COVERAGE.AGE_RANGE_VERBATIM,
                        GENDER.NAME).fetch()
    }

    private val TOUCHSTONE_SOURCES = "touchstoneSources"
    private val TOUCHSTONE_COUNTRIES = "touchstoneCountries"

    private fun getDemographicDatasetsForTouchstone(touchstoneVersionId: String):
            SelectConditionStep<Record5<Int, String, String, Boolean, String>>
    {

        return dsl.select(DEMOGRAPHIC_STATISTIC_TYPE.ID,
                DEMOGRAPHIC_STATISTIC_TYPE.CODE,
                DEMOGRAPHIC_STATISTIC_TYPE.NAME,
                DEMOGRAPHIC_STATISTIC_TYPE.GENDER_IS_APPLICABLE,
                DEMOGRAPHIC_SOURCE.CODE)
                .fromJoinPath(DEMOGRAPHIC_STATISTIC_TYPE,
                        DEMOGRAPHIC_DATASET,
                        TOUCHSTONE_DEMOGRAPHIC_DATASET)
                .join(DEMOGRAPHIC_SOURCE)
                .on(DEMOGRAPHIC_SOURCE.ID.eq(DEMOGRAPHIC_DATASET.DEMOGRAPHIC_SOURCE))
                .where(TOUCHSTONE_DEMOGRAPHIC_DATASET.TOUCHSTONE.eq(touchstoneVersionId))

    }

    private fun getDemographicStatisticType(typeCode: String):
            Select<Record5<String, String, String, String, Boolean>>
    {
        return dsl.select(
                DEMOGRAPHIC_STATISTIC_TYPE.CODE,
                DEMOGRAPHIC_STATISTIC_TYPE.NAME,
                DEMOGRAPHIC_STATISTIC_TYPE.AGE_INTERPRETATION,
                DEMOGRAPHIC_VALUE_UNIT.NAME,
                DEMOGRAPHIC_STATISTIC_TYPE.GENDER_IS_APPLICABLE)
                .from(DEMOGRAPHIC_STATISTIC_TYPE)
                .join(DEMOGRAPHIC_VALUE_UNIT)
                .on(DEMOGRAPHIC_STATISTIC_TYPE.DEMOGRAPHIC_VALUE_UNIT.eq(DEMOGRAPHIC_VALUE_UNIT.ID))
                .where(DEMOGRAPHIC_STATISTIC_TYPE.CODE.eq(typeCode))

    }

    private fun getDemographicStatistics(touchstoneVersionId: String,
                                         typeCode: String,
                                         sourceCode: String,
                                         gender: String):
            SelectConditionStep<Record9<Int, Int, String, Int, String, Int, BigDecimal, String, String>>
    {
        // we are hard coding this here for now - need to revisit data model longer term
        val variantNames = listOf("unwpp_estimates", "unwpp_medium_variant", "wpp_cm_hybrid")

        val touchstoneSources = dsl.select(DEMOGRAPHIC_SOURCE.ID, DEMOGRAPHIC_SOURCE.CODE.`as`("sourceCode"))
                .fromJoinPath(DEMOGRAPHIC_SOURCE, DEMOGRAPHIC_DATASET, TOUCHSTONE_DEMOGRAPHIC_DATASET)
                .join(DEMOGRAPHIC_STATISTIC_TYPE)
                .on(DEMOGRAPHIC_STATISTIC_TYPE.ID.eq(DEMOGRAPHIC_DATASET.DEMOGRAPHIC_STATISTIC_TYPE))
                .where(TOUCHSTONE_DEMOGRAPHIC_DATASET.TOUCHSTONE.eq(touchstoneVersionId))
                .and(DEMOGRAPHIC_SOURCE.CODE.eq(sourceCode))
                .and(DEMOGRAPHIC_STATISTIC_TYPE.CODE.eq(typeCode))

        val variants = dsl.select(DEMOGRAPHIC_VARIANT.ID)
                .from(DEMOGRAPHIC_VARIANT)
                .where(DEMOGRAPHIC_VARIANT.CODE.`in`(variantNames))

        val statisticType = dsl.fetchOne(
                DEMOGRAPHIC_STATISTIC_TYPE,
                DEMOGRAPHIC_STATISTIC_TYPE.CODE.eq(typeCode)
        ) ?: throw UnknownObjectError(typeCode, "demographic-statistic-type")
        val genderId = getGenderId(statisticType, gender)

        val countries = dsl.selectDistinct(COUNTRY.ID, COUNTRY.NID, COUNTRY.NAME)
                .fromJoinPath(COUNTRY, TOUCHSTONE_COUNTRY)
                .where(TOUCHSTONE_COUNTRY.TOUCHSTONE.eq(touchstoneVersionId))

        return dsl
                .with(TOUCHSTONE_SOURCES).`as`(touchstoneSources)
                .with("v").`as`(variants)
                .with(TOUCHSTONE_COUNTRIES).`as`(countries)
                .select(DEMOGRAPHIC_STATISTIC.AGE_FROM,
                        DEMOGRAPHIC_STATISTIC.AGE_TO,
                        DEMOGRAPHIC_STATISTIC.COUNTRY,
                        field(name(TOUCHSTONE_COUNTRIES, "nid"), Int::class.java),
                        field(name(TOUCHSTONE_COUNTRIES, "name"), String::class.java),
                        DEMOGRAPHIC_STATISTIC.YEAR,
                        DEMOGRAPHIC_STATISTIC.VALUE,
                        field(name(TOUCHSTONE_SOURCES, "sourceCode"), String::class.java),
                        GENDER.CODE)
                .from(DEMOGRAPHIC_STATISTIC)
                .join(GENDER)
                .on(GENDER.ID.eq(DEMOGRAPHIC_STATISTIC.GENDER))
                .join(table(name(TOUCHSTONE_COUNTRIES)))
                .on(field(name(TOUCHSTONE_COUNTRIES, "id")).eq(DEMOGRAPHIC_STATISTIC.COUNTRY))
                .join(table(name(TOUCHSTONE_SOURCES)))
                .on(DEMOGRAPHIC_STATISTIC.DEMOGRAPHIC_SOURCE.eq(field(name(TOUCHSTONE_SOURCES, "id"), Int::class.java)))
                .join(table(name("v")))
                .on(DEMOGRAPHIC_STATISTIC.DEMOGRAPHIC_VARIANT.eq(field(name("v", "id"), Int::class.java)))
                .where(DEMOGRAPHIC_STATISTIC.DEMOGRAPHIC_STATISTIC_TYPE.eq(statisticType.id))
                .and(DEMOGRAPHIC_STATISTIC.GENDER.eq(genderId))
    }

    override fun getGenders(): Map<GenderEnum, Int>
    {
        return dsl.fetch(GENDER).associate {
            GenderEnum.valueOf(it.name.uppercase()) to it.id
        }
    }

    private fun getGenderId(statisticType: DemographicStatisticTypeRecord, gender: String): Int
    {
        val genderFilter = if (statisticType[DEMOGRAPHIC_STATISTIC_TYPE.GENDER_IS_APPLICABLE])
        {
            GENDER.CODE.eq(gender)
        }
        else
        {
            GENDER.CODE.eq("both")
        }
        return dsl.fetchOne(GENDER, genderFilter).id
    }

    private fun getScenariosFromRecords(records: Result<Record>): List<Scenario>
    {
        val scenarioIds = records.map { it[SCENARIO_DESCRIPTION.ID] }
        return scenarioRepository.getScenarios(scenarioIds)
    }

    private fun getCoverageSetsFromRecord(records: Result<Record>, scenario: Scenario) =
            records
                    .filter { it[SCENARIO_DESCRIPTION.ID] == scenario.id && it[COVERAGE_SET.ID] != null }
                    .distinctBy { it[COVERAGE_SET.ID] }
                    .map { mapCoverageSet(it) }

    /** Takes a list of TOUCHSTONE_NAME + TOUCHSTONE records.
     * The TOUCHSTONE_NAME fields should be the same for each record.
     * The TOUCHSTONE fields should be different for each record.
     */
    override fun mapTouchstone(records: List<Record>): Touchstone
    {
        val shared = records.first()
        return Touchstone(
                shared[TOUCHSTONE_NAME.ID],
                shared[TOUCHSTONE_NAME.DESCRIPTION],
                shared[TOUCHSTONE_NAME.COMMENT],
                versions = records.map(this::mapTouchstoneVersion).sortedByDescending { it.version }
        )
    }

    override fun mapTouchstoneVersion(record: Record) = TouchstoneVersion(
            record[TOUCHSTONE.ID],
            record[TOUCHSTONE.TOUCHSTONE_NAME],
            record[TOUCHSTONE.VERSION],
            record[TOUCHSTONE.DESCRIPTION],
            mapper.mapEnum(record[TOUCHSTONE.STATUS])
    )

    inline fun <reified T : Any?> Record.getField(name: Name): T = this.get(name, T::class.java)

    private fun mapDemographicDataset(record: Record) = DemographicDataset(
            record[DEMOGRAPHIC_STATISTIC_TYPE.CODE],
            record[DEMOGRAPHIC_STATISTIC_TYPE.NAME],
            record[DEMOGRAPHIC_STATISTIC_TYPE.GENDER_IS_APPLICABLE],
            record[DEMOGRAPHIC_SOURCE.CODE])

    private fun mapCoverageSet(record: Record) = CoverageSet(
            record[COVERAGE_SET.ID],
            record[TOUCHSTONE.ID],
            record[COVERAGE_SET.NAME],
            record[COVERAGE_SET.VACCINE],
            mapper.mapEnum(record[COVERAGE_SET.GAVI_SUPPORT_LEVEL]),
            mapper.mapEnum(record[COVERAGE_SET.ACTIVITY_TYPE])
    )

    private fun mapCoverageRow(record: Record, scenarioDescriptionId: String, touchstoneVersionId: String, diseaseId: String) =
            if (includeGenderInCoverage(touchstoneVersionId))
            {
                GenderedLongCoverageRow(
                        scenarioDescriptionId,
                        record[COVERAGE_SET.NAME],
                        record[COVERAGE_SET.VACCINE],
                        mapper.mapEnum(record[COVERAGE_SET.GAVI_SUPPORT_LEVEL]),
                        mapper.mapEnum(record[COVERAGE_SET.ACTIVITY_TYPE]),
                        record[COVERAGE.COUNTRY],
                        record[COUNTRY.NAME],
                        record[COVERAGE.YEAR],
                        record[COVERAGE.AGE_FROM],
                        record[COVERAGE.AGE_TO],
                        record[COVERAGE.AGE_RANGE_VERBATIM],
                        record[COVERAGE.TARGET],
                        record[COVERAGE.COVERAGE_],
                        (record[GENDER.NAME] ?: defaultGender(diseaseId)).lowercase(),
                        record[COVERAGE.PROPORTION_RISK]
                )
            }
            else
            {
                NoGenderLongCoverageRow(
                        scenarioDescriptionId,
                        record[COVERAGE_SET.NAME],
                        record[COVERAGE_SET.VACCINE],
                        mapper.mapEnum(record[COVERAGE_SET.GAVI_SUPPORT_LEVEL]),
                        mapper.mapEnum(record[COVERAGE_SET.ACTIVITY_TYPE]),
                        record[COVERAGE.COUNTRY],
                        record[COUNTRY.NAME],
                        record[COVERAGE.YEAR],
                        record[COVERAGE.AGE_FROM],
                        record[COVERAGE.AGE_TO],
                        record[COVERAGE.AGE_RANGE_VERBATIM],
                        record[COVERAGE.TARGET],
                        record[COVERAGE.COVERAGE_],
                        record[COVERAGE.PROPORTION_RISK]
                )
            }


    private fun mapDemographicRow(record: Record) = LongDemographicRow(
            record[field(name(TOUCHSTONE_COUNTRIES, "nid"), Int::class.java)],
            record[DEMOGRAPHIC_STATISTIC.COUNTRY],
            record[field(name(TOUCHSTONE_COUNTRIES, "name"), String::class.java)],
            record[DEMOGRAPHIC_STATISTIC.AGE_FROM],
            record[DEMOGRAPHIC_STATISTIC.AGE_TO],
            record[DEMOGRAPHIC_STATISTIC.YEAR],
            record[GENDER.CODE],
            record[DEMOGRAPHIC_STATISTIC.VALUE]
    )

    private fun getDiseaseIdForScenarioDescription(scenarioDescriptionId: String): String =
            dsl.select(SCENARIO_DESCRIPTION.DISEASE)
                    .from(SCENARIO_DESCRIPTION)
                    .where(SCENARIO_DESCRIPTION.ID.eq(scenarioDescriptionId))
                    .fetchOne()[SCENARIO_DESCRIPTION.DISEASE]

    private fun includeGenderInCoverage(touchstoneVersionId: String): Boolean = touchstoneVersionId > "2019"

    private fun defaultGender(diseaseId: String): String =
            if (diseaseId == "HPV")
            {
                "female"
            }
            else
            {
                "both"
            }
}
