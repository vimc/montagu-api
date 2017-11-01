package org.vaccineimpact.api.generateTestData

import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.*


class DemographicTestData(val db: JooqContext)
{
    val variants = listOf("unwpp_estimates")
    val countries = db.fetchCountries(96)
    val sourceNames = listOf("unwpp2017")
    val variantIds = db.generateDemographicVariants(variants)
    val unit = db.fetchDemographicUnitIds().first()
    val genderIds = db.fetchGenders()
    val statisticTypes = addStatisticTypes(listOf(
            "tot-pop" to "Quinquennial population"
    ))

    fun generate(touchstoneId: String, diseases: List<String>)
    {
        val sources = sourceNames.map { Pair<String, Int>(it, db.generateDemographicSource(it)) }

        for (disease in diseases)
        {
            db.addTouchstoneCountries(touchstoneId, countries, disease)
        }

        for ((typeCode, typeId) in statisticTypes)
        {
            for ((_, sourceId) in sources.filter { (code, _) -> sourceIsRelevantToType(code, typeCode) })
            {
                for (gender in genderIds)
                {
                    for (variant in variantIds)
                    {
                        println("Generating demographic data for $touchstoneId/type:$typeCode/source:$sourceId/gender:$gender/variant:$variant")
                        db.generateDemographicData(sourceId, typeId,
                                genderId = gender,
                                variantId = variant,
                                countries = countries,
                                ageRange = 0..100,
                                yearRange = 1950..2100
                        )
                    }
                }

                db.addDemographicDatasetsToTouchstone(touchstoneId, sourceId, typeId)
            }

        }
    }

    private fun sourceIsRelevantToType(source: String, type: String) =
            type == "tot-pop" || source == "unwpp2017"

    fun addStatisticTypes(definitions: List<Pair<String, String>>): List<Pair<String, Int>>
    {
        return definitions.withIndex().map { (index, definition) ->
            val (code, name) = definition
            val id = db.addDemographicStatisticType(
                    code,
                    variantIds,
                    unit,
                    name = name,
                    genderIsApplicable = index % 2 == 0
            )
            code to id
        }
    }
}