package org.vaccineimpact.api.app.logic

import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.errors.MissingRowsError
import org.vaccineimpact.api.app.getLongCoverageRowDataTable
import org.vaccineimpact.api.app.getWideCoverageRowDataTable
import org.vaccineimpact.api.app.repositories.*
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.models.expectations.*
import org.vaccineimpact.api.serialization.FlexibleDataTable
import org.vaccineimpact.api.serialization.SplitData
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDate

interface CoverageLogic
{
    fun getCoverageData(touchstoneVersionId: String, scenarioId: String, format: String?)
            : SplitData<ScenarioTouchstoneAndCoverageSets, CoverageRow>

    fun getCoverageDataForGroup(groupId: String, touchstoneVersionId: String, scenarioId: String,
                                allCountries: Boolean, format: String?)
            : SplitData<ScenarioTouchstoneAndCoverageSets, CoverageRow>

    fun getCoverageSetsForGroup(groupId: String, touchstoneVersionId: String, scenarioId: String):
            ScenarioTouchstoneAndCoverageSets

    // in practice will always validate rows but the flag is useful for testing
    fun saveCoverageForTouchstone(touchstoneVersionId: String,
                                  rows: Sequence<CoverageIngestionRow>,
                                  description: String,
                                  uploader: String,
                                  timestamp: Instant,
                                  validate: Boolean = true)
}

class RepositoriesCoverageLogic(private val modellingGroupRepository: ModellingGroupRepository,
                                private val responsibilitiesRepository: ResponsibilitiesRepository,
                                private val touchstoneRepository: TouchstoneRepository,
                                private val scenarioRepository: ScenarioRepository,
                                private val expectationsRepository: ExpectationsRepository) : CoverageLogic
{

    constructor(repositories: Repositories) : this(repositories.modellingGroup,
            repositories.responsibilities,
            repositories.touchstone,
            repositories.scenario,
            repositories.expectations)

    private val mandatoryGAVICoverageYears = 2021..2030
    private val allowedGAVICoverageYears = 2000..2100

    override fun getCoverageSetsForGroup(groupId: String, touchstoneVersionId: String, scenarioId: String):
            ScenarioTouchstoneAndCoverageSets
    {
        modellingGroupRepository.getModellingGroup(groupId)
        // We don't use the returned responsibility, but by using this method we check that the group exists
        // and that the group is responsible for the given scenario in the given touchstoneVersion
        val responsibilityAndTouchstone = responsibilitiesRepository.getResponsibility(groupId, touchstoneVersionId, scenarioId)
        val scenarioAndCoverageSets = touchstoneRepository.getScenarioAndCoverageSets(touchstoneVersionId, scenarioId)
        return ScenarioTouchstoneAndCoverageSets(
                responsibilityAndTouchstone.touchstoneVersion,
                scenarioAndCoverageSets.scenario,
                scenarioAndCoverageSets.coverageSets)
    }

    private fun generateCountryLookup(countries: List<String>): CountryLookup
    {
        val map = CountryLookup()
        for (country in countries)
        {
            map[country] = generateYearLookup()
        }
        return map
    }

    private fun addAllowedCountryToLookup(country: String, countryLookup: CountryLookup): YearLookup
    {
        val yearLookup = generateYearLookup()
        countryLookup[country] = yearLookup
        return yearLookup
    }

    private fun generateYearLookup(): YearLookup
    {
        val yearMap = YearLookup()
        for (year in mandatoryGAVICoverageYears)
        {
            yearMap[year.toShort()] = false
        }
        return yearMap
    }

    override fun saveCoverageForTouchstone(touchstoneVersionId: String,
                                           rows: Sequence<CoverageIngestionRow>,
                                           description: String,
                                           uploader: String,
                                           timestamp: Instant,
                                           validate: Boolean)
    {
        val genders = touchstoneRepository.getGenders()
        val setDeterminants = mutableListOf<Pair<ActivityType, String>>()
        val setIds = mutableListOf<Int>()
        var expectedRowLookup = mutableMapOf<String, CountryLookup>()
        val requiredCountries = expectationsRepository.getExpectedGAVICoverageCountries()
        val allowedCountries = expectationsRepository.getAllowedGAVICoverageCountries()
        val metadataId = touchstoneRepository.createCoverageSetMetadata(description, uploader, timestamp)
        val records = rows.flatMap { row ->

            val rowVaccines = mapVaccineForCoverageSet(row.vaccine)
            rowVaccines.map { vaccine ->

                val set = Pair(row.activityType, vaccine)
                var setIndex = setDeterminants.indexOf(set)
                if (setIndex == -1)
                {
                    // All coverage uploaded by GAVI should be put into a coverage set with
                    // gavi support level "WITH". Individual coverage rows may nevertheless have
                    // gaviSupport = false where e.g. countries are funding their own programs
                    val newId = touchstoneRepository.createCoverageSet(touchstoneVersionId,
                            vaccine, row.activityType, GAVISupportLevel.WITH, metadataId)
                    setIds.add(newId)
                    setDeterminants.add(set)
                    setIndex = setIds.count() - 1
                }
                if (validate)
                {
                    expectedRowLookup = validate(vaccine, row, requiredCountries, allowedCountries, expectedRowLookup)
                }
                val id = setIds[setIndex]
                touchstoneRepository.newCoverageRowRecord(
                        coverageSetId = id,
                        country = row.country,
                        year = row.year,
                        ageFrom = BigDecimal(row.ageFirst),
                        ageTo = BigDecimal(row.ageLast),
                        gender = genders[row.gender]!!,
                        gaviSupport = row.gaviSupport,
                        target = row.target.toBigDecimal(),
                        coverage = row.coverage.toBigDecimal(),
                        subnational = row.subnational
                )
            }.asSequence()
        }.toList()

        val setsWithMissingRows = expectedRowLookup.missingRows()
        if (setsWithMissingRows.any())
        {
            throw MissingRowsError(rowErrorMessage(setsWithMissingRows))
        }
        touchstoneRepository.saveCoverageForTouchstone(touchstoneVersionId, records)
    }

    private fun validate(vaccine: String,
                         row: CoverageIngestionRow,
                         requiredCountries: List<String>,
                         allowedCountries: List<String>,
                         expectedRowLookup: CoverageRowLookup): CoverageRowLookup
    {
        if (!allowedGAVICoverageYears.contains(row.year))
        {
            throw BadRequest("Unexpected year: ${row.year}")
        }
        if (!allowedCountries.contains(row.country))
        {
            throw BadRequest("Unrecognised or unexpected country: ${row.country}")
        }
        if (row.activityType == ActivityType.ROUTINE)
        {
            val countryLookup = expectedRowLookup[vaccine] ?: generateCountryLookup(requiredCountries)
            val year = row.year.toShort()
            val yearLookup = countryLookup[row.country] ?: addAllowedCountryToLookup(row.country, countryLookup)
            if (yearLookup[year] == true)
            {
                throw BadRequest("Duplicate row detected: ${row.year}, ${row.vaccine}, ${row.country}")
            }
            yearLookup[year] = true
            expectedRowLookup[row.vaccine] = countryLookup
        }

        return expectedRowLookup
    }

    private fun rowErrorMessage(setsWithMissingRows: Map<String, CountryLookup>): String
    {
        val totalMissingRows = setsWithMissingRows
                .flatMap {
                    it.value
                            .flatMap { it.value.missingYears() }
                }.count()

        val countries = setsWithMissingRows.entries
        val missingCountryYearPairs = countries.flatMap { it ->
            val vaccine = it.key
            val countryLookup = it.value
            countryLookup.missingRows().flatMap {
                it.value.missingYears().map { year ->
                    "$vaccine, ${it.key}, $year"
                }
            }
        }.take(5) // take 5 example rows

        val basicMessage = "Missing $totalMissingRows rows for vaccines ${setsWithMissingRows.keys.joinToString(", ")}:"
        val exampleRowMessage = "*${missingCountryYearPairs.joinToString("\n *")}"
        return "$basicMessage\n $exampleRowMessage\nand ${totalMissingRows - 5} others"
    }

    override fun getCoverageDataForGroup(groupId: String, touchstoneVersionId: String, scenarioId: String,
                                         allCountries: Boolean, format: String?)
            : SplitData<ScenarioTouchstoneAndCoverageSets, CoverageRow>
    {
        modellingGroupRepository.getModellingGroup(groupId)
        val responsibilityAndTouchstone =
                responsibilitiesRepository.getResponsibility(groupId, touchstoneVersionId, scenarioId)

        val scenario = scenarioRepository.getScenarioForTouchstone(touchstoneVersionId, scenarioId)
        val coverageSets = touchstoneRepository.getCoverageSetsForScenario(touchstoneVersionId, scenarioId)

        val data = if (allCountries)
        {
            touchstoneRepository.getCoverageDataForScenario(touchstoneVersionId, scenarioId)
        }
        else
        {
            touchstoneRepository.getCoverageDataForResponsibility(
                    touchstoneVersionId,
                    responsibilityAndTouchstone.responsibilityId,
                    scenario.id)
        }

        val dataTable = getLongCoverageRowDataTable(data)

        val splitData = SplitData(ScenarioTouchstoneAndCoverageSets(
                responsibilityAndTouchstone.touchstoneVersion,
                scenario,
                coverageSets
        ), dataTable)

        return getDatatable(splitData, format)
    }

    override fun getCoverageData(touchstoneVersionId: String, scenarioId: String, format: String?)
            : SplitData<ScenarioTouchstoneAndCoverageSets, CoverageRow>
    {
        val touchstoneVersion = touchstoneRepository.touchstoneVersions.get(touchstoneVersionId)
        val scenarioAndData = touchstoneRepository.getScenarioAndCoverageData(touchstoneVersionId, scenarioId)
        val splitData = SplitData(ScenarioTouchstoneAndCoverageSets(
                touchstoneVersion,
                scenarioAndData.structuredMetadata.scenario,
                scenarioAndData.structuredMetadata.coverageSets
        ), scenarioAndData.tableData)
        return getDatatable(splitData, format)
    }

    private fun getDatatable(splitData: SplitData<ScenarioTouchstoneAndCoverageSets, LongCoverageRow>,
                             format: String?)
            : SplitData<ScenarioTouchstoneAndCoverageSets, CoverageRow>
    {
        val tableData = when (format)
        {

            "wide" -> getWideDatatable(splitData.tableData.data)
            "long", null -> splitData.tableData
            else -> throw BadRequest("Format '$format' not a valid csv format. Available formats are 'long' " +
                    "and 'wide'.")
        }

        return SplitData(splitData.structuredMetadata, tableData)
    }

    private fun mapVaccineForCoverageSet(vaccine: String): List<String>
    {
        // GAVI may provide combination vaccines rather than individual vaccines in some coverage rows
        return when (vaccine.uppercase())
        {
            "PENTA", "PENTAVALENT" -> listOf("Hib3", "HepB", "DTP3")
            "MR1" -> listOf("MCV1", "Rubella")
            "MR2" -> listOf("MCV2", "RCV2")
            else -> listOf(vaccine)
        }
    }

    private fun getWideDatatable(data: Sequence<LongCoverageRow>):
            FlexibleDataTable<out WideCoverageRow>
    {
        val groupedRows = data
                .groupBy {
                    if (it is GenderedLongCoverageRow)
                    {
                        hashSetOf(
                                it.countryCode, it.setName,
                                it.ageFirst, it.ageLast, it.ageRangeVerbatim,
                                it.vaccine, it.gaviSupport, it.activityType,
                                it.gender
                        )
                    }
                    else
                    {
                        hashSetOf(
                                it.countryCode, it.setName,
                                it.ageFirst, it.ageLast, it.ageRangeVerbatim,
                                it.vaccine, it.gaviSupport, it.activityType
                        )
                    }
                }

        val rows = groupedRows.values
                .map {
                    mapWideCoverageRow(it)
                }


        // all the rows should have the same number of years, so we just look at the first row
        val years = if (rows.any())
        {
            rows.first().coverageAndTargetPerYear.keys.toList()
        }
        else
        {
            listOf()
        }

        return getWideCoverageRowDataTable(rows.asSequence(), years.sorted())

    }

    private fun mapWideCoverageRow(records: List<LongCoverageRow>)
            : WideCoverageRow
    {
        // all records have same country, gender, age_from and age_to, so can look at first one for these
        val reference = records.first()

        val coverageAndTargetPerYear =
                records.associateBy({ "coverage_${it.year}" }, { it.coverage }) +
                        records.associateBy({ "target_${it.year}" }, { it.target })

        if (reference is GenderedLongCoverageRow)
        {
            return GenderedWideCoverageRow(reference.scenario,
                    reference.setName,
                    reference.vaccine,
                    reference.gaviSupport,
                    reference.activityType,
                    reference.countryCode,
                    reference.country,
                    reference.ageFirst,
                    reference.ageLast,
                    reference.ageRangeVerbatim,
                    reference.gender,
                    coverageAndTargetPerYear)
        }
        else
        {
            return NoGenderWideCoverageRow(reference.scenario,
                    reference.setName,
                    reference.vaccine,
                    reference.gaviSupport,
                    reference.activityType,
                    reference.countryCode,
                    reference.country,
                    reference.ageFirst,
                    reference.ageLast,
                    reference.ageRangeVerbatim,
                    coverageAndTargetPerYear)
        }

    }

}
