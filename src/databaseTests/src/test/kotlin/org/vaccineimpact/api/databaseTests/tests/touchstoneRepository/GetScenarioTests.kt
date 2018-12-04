package org.vaccineimpact.api.databaseTests.tests.touchstoneRepository

import java.math.BigDecimal
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.Test
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.direct.*
import org.vaccineimpact.api.db.toDecimal
import org.vaccineimpact.api.db.toDecimalOrNull
import org.vaccineimpact.api.models.*

class GetScenarioTests : TouchstoneRepositoryTests()
{
    val setA = 1
    val setB = 2
    val setC = 3
    val setD = 4
    val setE = 5
    val setF = 6

    val groupId = "group-1"

    @Test
    fun `getScenario throws exception if scenario doesn't exist`()
    {
        given {
            createTouchstoneAndScenarioDescriptions(it)
        } check {
            Assertions.assertThatThrownBy { it.getScenarioAndCoverageSets(touchstoneVersionId, scenarioId) }
                    .isInstanceOf(UnknownObjectError::class.java)
        }
    }

    @Test
    fun `can get scenario with coverage sets`()
    {
        val scenarioInTouchstoneId = 1
        val extraTouchstoneId = "extra-1"
        given {
            createTouchstoneAndScenarioDescriptions(it)
            it.addTouchstoneVersion("extra", 1, addTouchstone = true)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId, id = scenarioInTouchstoneId)
            it.addScenarioToTouchstone(touchstoneVersionId, "yf-2", id = scenarioInTouchstoneId + 1)
            it.addScenarioToTouchstone(extraTouchstoneId, scenarioId, id = scenarioInTouchstoneId + 2)
            giveScenarioCoverageSets(it, scenarioId, includeCoverageData = false)
        } check {
            val result = it.getScenarioAndCoverageSets(touchstoneVersionId, "yf-1")
            checkScenarioIsAsExpected(result, listOf(extraTouchstoneId))
        }
    }

    @Test
    fun `can get scenario with ordered coverage sets`()
    {
        given {
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            giveUnorderedCoverageSetsToScenario(it)
        } check {
            val result = it.getScenarioAndCoverageSets(touchstoneVersionId, scenarioId)
                    .coverageSets!!

            assertThat(result.count()).isEqualTo(6)

            assertThat(result[0].id).isEqualTo(setA)
            assertThat(result[1].id).isEqualTo(setB)
            assertThat(result[2].id).isEqualTo(setC)
            assertThat(result[3].id).isEqualTo(setD)
            assertThat(result[4].id).isEqualTo(setE)
            assertThat(result[5].id).isEqualTo(setF)
        }
    }

    @Test
    fun `can get ordered coverage sets`()
    {
        given {
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            giveUnorderedCoverageSetsToScenario(it)
        } check {
            val result = it.getCoverageSetsForScenario(touchstoneVersionId, scenarioId)

            assertThat(result.count()).isEqualTo(6)

            assertThat(result[0].id).isEqualTo(setA)
            assertThat(result[1].id).isEqualTo(setB)
            assertThat(result[2].id).isEqualTo(setC)
            assertThat(result[3].id).isEqualTo(setD)
            assertThat(result[4].id).isEqualTo(setE)
            assertThat(result[5].id).isEqualTo(setF)
        }
    }


    @Test
    fun `can get scenario with coverage data`()
    {
        given {
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            giveScenarioCoverageSets(it, scenarioId, includeCoverageData = true)
        } check {
            val result = it.getScenarioAndCoverageData(touchstoneVersionId, scenarioId)
            checkScenarioIsAsExpected(result.structuredMetadata)
            assertThat(result.tableData.data.toList()).containsExactlyElementsOf(listOf(
                    LongCoverageRow(scenarioId, "YF without", "YF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                            "AAA", "AAA-Name", 2000, 10.toDecimal(), 20.toDecimal(), "10-20", "100".toDecimalOrNull(), "50.5".toDecimalOrNull()),
                    LongCoverageRow(scenarioId, "YF with", "YF", GAVISupportLevel.WITH, ActivityType.CAMPAIGN,
                            "BBB", "BBB-Name", 2001, 11.toDecimal(), 21.toDecimal(), null, null, null)
            ))
        }
    }

    @Test
    fun `can get scenario with ordered coverage data`()
    {
        given {
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            giveUnorderedCoverageSetsAndDataToScenario(it)
        } check {
            val result = it.getScenarioAndCoverageData(touchstoneVersionId, scenarioId)

            assertThat(result.structuredMetadata.coverageSets!!.count()).isEqualTo(3)

            assertThat(result.tableData.data.toList()).containsExactlyElementsOf(listOf(
                    LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "AAA", "AAA-Name", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null),
                    // first order by vaccine
                    LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                            "AAA", "AAA-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by activity type
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "AAA", "AAA-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by country
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by year
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by age first
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 2.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by age last
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null)

            ))
        }
    }


    @Test
    fun `getScenarioAndCoverageData throws exception if scenario doesnt exist`()
    {
        given {
            createTouchstoneAndScenarioDescriptions(it)
        } check {
            Assertions.assertThatThrownBy { it.getScenarioAndCoverageData(touchstoneVersionId, scenarioId) }
                    .isInstanceOf(UnknownObjectError::class.java)
        }
    }

    @Test
    fun `can get scenario with empty coverage data`()
    {
        given {
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            giveScenarioCoverageSets(it, scenarioId, includeCoverageData = false)
        } check {
            val result = it.getScenarioAndCoverageData(touchstoneVersionId, scenarioId)
            checkScenarioIsAsExpected(result.structuredMetadata)
            assertThat(result.tableData.data.toList()).isEmpty()
        }
    }

    @Test
    fun `can get ordered coverage data for scenario`()
    {
        given {
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            giveUnorderedCoverageSetsAndDataToScenario(it)
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            assertThat(result.toList()).containsExactlyElementsOf(listOf(
                    LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "AAA", "AAA-Name", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null),
                    // first order by vaccine
                    LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                            "AAA", "AAA-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by activity type
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "AAA", "AAA-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by country
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by year
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by age first
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 2.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by age last
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null)

            ))
        }
    }

    @Test
    fun `can get grouped coverage data for scenario`()
    {

        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            giveUnorderedCoverageSetAndDataWithDuplicatesToScenario(it)
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)
            assertLongCoverageRowListEqualWithCoverageTolerance(result.toList(),
                    listOf(
                    LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", "600".toDecimalOrNull(), "0.63".toDecimalOrNull()),
                    LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", "1500".toDecimalOrNull(), "0.21".toDecimalOrNull()),
                    LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                            "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
                    , 0.001)
        }
    }

    @Test
    fun `can get expected grouped coverage data for scenario where all group rows have target and coverage nulls`()
    {
        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            //Just check those expected nulls
            assertThat(result.toList()[0].target).isNull()
            assertThat(result.toList()[0].coverage).isNull()

            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
                    )

        }
    }

    @Test
    fun `can get expected grouped coverage data for scenario where one group row has null target and coverage`()
    {
        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(200), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","300".toDecimalOrNull(), "0.47".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for scenario where one group row has null target`()
    {
        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(200), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, BigDecimal(0.8))

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            //Expect the coverage row which has coverage but no target to not contribute to either value in the aggregate
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","300".toDecimalOrNull(), "0.47".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for scenario where one group row has null coverage`()
    {
        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(200), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(500), null)

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            //Expect the coverage row which has target but no coverage to not contribute to either value in the aggregate
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","300".toDecimalOrNull(), "0.47".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for scenario where one group row has null coverage and target, another has null target`()
    {
        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, BigDecimal(5.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.3))

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            //Expect only the coverage rows which have both target and coverage values to contirbute to the aggregate values
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","200".toDecimalOrNull(), "0.25".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for scenario where group has zero target`()
    {
        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(0), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(0), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(500), null) //This should get ignored

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            //Expect the coverage row which has aggregate target zero to have aggregate coverage of null
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","0".toDecimalOrNull(), null),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for scenario where group has zero target, with another row with null coverage`()
    {
        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(0), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(0), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(500), null) //This should get ignored because of null coverage

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            //Expect the coverage row which has aggregate target zero to have aggregate coverage of null
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","0".toDecimalOrNull(), null),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get ordered coverage data for responsibility`()
    {
        var responsibilityId = 0
        given {
            val countries = listOf("AAA", "BBB")
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            it.addCountries(countries)
            giveUnorderedCoverageSetsAndDataToScenario(it, addCountries = false)
            it.addExpectations(responsibilityId, countries = countries)

            // add extra data
            it.addGroup("bad-group")
            it.addResponsibilityInNewSet("bad-group", touchstoneVersionId, scenarioId)
            it.addScenarioToTouchstone(touchstoneVersionId, "yf-2")
            it.addTouchstoneVersion(touchstoneName, 2, addTouchstone = false)
            it.addScenarioToTouchstone("$touchstoneName-2", scenarioId)

        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId,
                    responsibilityId,
                    scenarioId)

            assertThat(result.toList()).containsExactlyElementsOf(listOf(
                    LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "AAA", "AAA-Name", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null),
                    // first order by vaccine
                    LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                            "AAA", "AAA-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by activity type
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "AAA", "AAA-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by country
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by year
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 1.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by age first
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 2.toDecimal(), 2.toDecimal(), null, null, null),
                    // then by age last
                    LongCoverageRow(scenarioId, "Third", "BF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                            "BBB", "BBB-Name", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null)

            ))
        }
    }

    @Test
    fun `can get grouped coverage data for responsibility`()
    {
        var responsibilityId = 0
        given {
            val countries = listOf("AAA", "BBB")
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            it.addCountries(countries)
            giveUnorderedCoverageSetAndDataWithDuplicatesToScenario(it, addCountries = false)
            it.addExpectations(responsibilityId, countries = countries)

        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)
            assertLongCoverageRowListEqualWithCoverageTolerance(result.toList(),
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", "600".toDecimalOrNull(), "0.63".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "BBB", "BBB-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", "1500".toDecimalOrNull(), "0.21".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull())
                    ),
                    0.001)
        }

    }

    @Test
    fun `can get expected grouped coverage data for responsibility where all group rows have target and coverage nulls`()
    {
        var responsibilityId = 0
        given{
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)
            it.addExpectations(responsibilityId, countries = listOf("AAA", "BBB"))

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            //Just check those expected nulls
            assertThat(result.toList()[0].target).isNull()
            assertThat(result.toList()[0].coverage).isNull()

            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for responsibility where one group row has null target and coverage`()
    {
        var responsibilityId = 0
        given{
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)
            it.addExpectations(responsibilityId, countries = listOf("AAA", "BBB"))

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(200), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","300".toDecimalOrNull(), "0.47".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for responsibility where one group row has null target`()
    {
        var responsibilityId = 0
        given{
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)
            it.addExpectations(responsibilityId, countries = listOf("AAA", "BBB"))

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(200), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, BigDecimal(0.8))

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            //Expect the coverage row which has coverage but no target to not contribute to either value in the aggregate
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","300".toDecimalOrNull(), "0.47".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for responsibility where one group row has null coverage`()
    {
        var responsibilityId = 0
        given{
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)
            it.addExpectations(responsibilityId, countries = listOf("AAA", "BBB"))

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(200), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(500), null)

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            //Expect the coverage row which has target but no coverage to not contribute to either value in the aggregate
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","300".toDecimalOrNull(), "0.47".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for responsibility where one group row has null coverage and target, another has null target`()
    {
        var responsibilityId = 0
        given{
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)
            it.addExpectations(responsibilityId, countries = listOf("AAA", "BBB"))

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, null)
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", null, BigDecimal(5.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.3))

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            //Expect only the coverage rows which have both target and coverage values to contirbute to the aggregate values
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","200".toDecimalOrNull(), "0.25".toDecimalOrNull()),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for responsibility where group has zero target`()
    {
        var responsibilityId = 0
        given{
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)
            it.addExpectations(responsibilityId, countries = listOf("AAA", "BBB"))

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(0), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(0), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(500), null) //This should get ignored

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            //Expect the coverage row which has aggregate target zero to have aggregate coverage of null
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","0".toDecimalOrNull(), null),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }

    @Test
    fun `can get expected grouped coverage data for responsibility where group has zero target, with another row with null coverage`()
    {
        var responsibilityId = 0
        given{
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)
            it.addExpectations(responsibilityId, countries = listOf("AAA", "BBB"))

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(0), BigDecimal(0.2))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(0), BigDecimal(0.6))
            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(500), null) //This should get ignored because of null coverage

            //Make sure other groups aren't affected
            it.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            //Expect the coverage row which has aggregate target zero to have aggregate coverage of null
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2","0".toDecimalOrNull(), null),
                            LongCoverageRow(scenarioId, "Second", "BF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN,
                                    "BBB", "BBB-Name", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", "1000".toDecimalOrNull(), "0.5".toDecimalOrNull()))
            )

        }
    }


    @Test
    fun `get coverage data for responsibility only returns expectations countries`()
    {
        var responsibilityId = 0
        given {
            val countries = listOf("AAA", "BBB", "CCC", "DDD")
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)
            it.addCountries(countries)
            giveUnorderedCoverageSetsAndDataToScenario(it, addCountries = false)
            it.addExpectations(responsibilityId, countries = countries.subList(1, 3))
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            assertThat(result.toList().count()).isEqualTo(4)
        }
    }

    @Test
    fun `can get coverage sets for scenario with empty coverage data`()
    {
        given {
            val countries = listOf("AAA", "BBB", "CCC")
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            it.addCountries(countries)
            giveScenarioCoverageSets(it, scenarioId, includeCoverageData = false)
        } check {
            val result = it.getCoverageSetsForScenario(touchstoneVersionId,scenarioId)

            assertThat(result).hasSameElementsAs(listOf(
                    CoverageSet(setA, touchstoneVersionId, "YF without", "YF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN),
                    CoverageSet(setB, touchstoneVersionId, "YF with", "YF", GAVISupportLevel.WITH, ActivityType.CAMPAIGN)
            ))
        }
    }

    @Test
    fun `can get coverage data for responsibility with empty coverage data`()
    {
        var responsibilityId = 0
        given {
            val countries = listOf("AAA", "BBB", "CCC")
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)
            it.addCountries(countries)
            giveScenarioCoverageSets(it, scenarioId, includeCoverageData = false)
            it.addExpectations(responsibilityId, countries = countries)
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId,
                    scenarioId)

            assertThat(result.toList()).isEmpty()
        }
    }

    @Test
    fun `can get coverage data for responsibility with no expectations`()
    {
        var responsibilityId = 0
        given {
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)
            giveScenarioCoverageSets(it, scenarioId, includeCoverageData = true)
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId,responsibilityId, scenarioId)

            assertThat(result.toList()).isEmpty()
        }
    }

    //This set of tests confirm that new SQL grouping logic in TouchstoneRepo does not interfere with cases where only
    //a single row per group is present, and where target and coverage values for that row should be output unmolested
    //whether or not they include zeroes or nulls
    @Test
    fun `can get expected coverage data for responsibility single row, target and coverage are zero`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged("0".toDecimalOrNull(), "0".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for responsibility single row, target is zero, coverage is null`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged("0".toDecimalOrNull(), null)
    }

    @Test
    fun `can get expected coverage data for responsibility single row, target is null, coverage is zero`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged(null, "0".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for responsibility single row, target and coverage are null`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged(null, null)
    }

    @Test
    fun `can get expected coverage data for responsibility single row, target is null`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged(null, "0.5".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for responsibility single row, coverage is null`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged("50".toDecimalOrNull(), null)
    }

    @Test
    fun `can get expected coverage data for responsibility single row, target is zero`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged("0".toDecimalOrNull(), "0.5".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for responsibility single row, coverage is zero`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged("50".toDecimalOrNull(), "0".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for responsibility single row, target and coverage are non-zero`()
    {
        testSingleRowResponsibilityCoverageValuesAreUnchanged("50".toDecimalOrNull(), "0.5".toDecimalOrNull())
    }



    @Test
    fun `can get expected coverage data for scenario single row, target and coverage are zero`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged("0".toDecimalOrNull(), "0".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for scenario single row, target is zero, coverage is null`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged("0".toDecimalOrNull(), null)
    }

    @Test
    fun `can get expected coverage data for scenario single row, target is null, coverage is zero`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged(null, "0".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for scenario single row, target and coverage are null`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged(null, null)
    }

    @Test
    fun `can get expected coverage data for scenario single row, target is null`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged(null, "0.5".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for scenario single row, coverage is null`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged("50".toDecimalOrNull(), null)
    }

    @Test
    fun `can get expected coverage data for scenario single row, target is zero`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged("0".toDecimalOrNull(), "0.5".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for scenario single row, coverage is zero`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged("50".toDecimalOrNull(), "0".toDecimalOrNull())
    }

    @Test
    fun `can get expected coverage data for scenario single row, target and coverage are non-zero`()
    {
        testSingleRowScenarioCoverageValuesAreUnchanged("50".toDecimalOrNull(), "0.5".toDecimalOrNull())
    }

    private fun testSingleRowResponsibilityCoverageValuesAreUnchanged(target: BigDecimal?, coverage: BigDecimal?)
    {
        var responsibilityId = 0
        given{
            it.addGroup(groupId)
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)
            responsibilityId = it.addResponsibilityInNewSet(groupId, touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)
            it.addExpectations(responsibilityId, countries = listOf("AAA", "BBB"))

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", target, coverage)
        } check {
            val result = it.getCoverageDataForResponsibility(touchstoneVersionId, responsibilityId, scenarioId)

            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", target, coverage)
                    ))

        }
    }

    private fun testSingleRowScenarioCoverageValuesAreUnchanged(target: BigDecimal?, coverage: BigDecimal?)
    {
        given{
            createTouchstoneAndScenarioDescriptions(it)
            it.addScenarioToTouchstone(touchstoneVersionId, scenarioId)

            addABCoverageSets(it)
            addABCountries(it)

            it.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", target, coverage)

        } check {
            val result = it.getCoverageDataForScenario(touchstoneVersionId, scenarioId)

            //Expect the coverage row which has aggregate target zero to have aggregate coverage of null
            assertThat(result.toList()).containsExactlyElementsOf(
                    listOf(
                            LongCoverageRow(scenarioId, "First", "AF", GAVISupportLevel.WITHOUT, ActivityType.ROUTINE,
                                    "AAA", "AAA-Name", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", target, coverage)

            ))
        }
    }


    private fun checkScenarioIsAsExpected(result: ScenarioAndCoverageSets, extraTouchstones: List<String> = emptyList())
    {
        assertThat(result.scenario.description).isEqualTo("Yellow Fever 1")
        assertThat(result.scenario.id).isEqualTo("yf-1")
        assertThat(result.scenario.disease).isEqualTo("YF")
        assertThat(result.scenario.touchstones).hasSameElementsAs(listOf(touchstoneVersionId) + extraTouchstones)

        assertThat(result.coverageSets).hasSameElementsAs(listOf(
                CoverageSet(setA, touchstoneVersionId, "YF without", "YF", GAVISupportLevel.WITHOUT, ActivityType.CAMPAIGN),
                CoverageSet(setB, touchstoneVersionId, "YF with", "YF", GAVISupportLevel.WITH, ActivityType.CAMPAIGN)
        ))
    }

    private fun giveScenarioCoverageSets(db: JooqContext, scenarioId: String, includeCoverageData: Boolean)
    {
        db.addCoverageSet(touchstoneVersionId, "YF without", "YF", "without", "campaign", id = setA)
        db.addCoverageSet(touchstoneVersionId, "YF with", "YF", "with", "campaign", id = setB)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setB, 4)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setA, 0)
        if (includeCoverageData)
        {
            db.addCountries(listOf("AAA", "BBB"))
            db.addCoverageRow(setA, "AAA", 2000, 10.toDecimal(), 20.toDecimal(), "10-20", 100.toDecimal(), "50.5".toDecimalOrNull())
            db.addCoverageRow(setB, "BBB", 2001, 11.toDecimal(), 21.toDecimal(), null, null, null)
        }
    }

    private fun giveUnorderedCoverageSetsAndDataToScenario(db: JooqContext, addCountries: Boolean = true)
    {
        db.addCoverageSet(touchstoneVersionId, "First", "AF", "without", "routine", id = setA)
        db.addCoverageSet(touchstoneVersionId, "Second", "BF", "without", "campaign", id = setB)
        db.addCoverageSet(touchstoneVersionId, "Third", "BF", "without", "routine", id = setC)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setA, 0)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setB, 1)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setC, 2)

        if (addCountries)
        {
            db.addCountries(listOf("AAA", "BBB"))
        }

        // adding these in jumbled up order
        db.addCoverageRow(setC, "BBB", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null)
        db.addCoverageRow(setA, "AAA", 2001, 2.toDecimal(), 4.toDecimal(), null, null, null)
        db.addCoverageRow(setC, "AAA", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(setC, "BBB", 2001, 2.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(setC, "BBB", 2001, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(setB, "AAA", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null)
        db.addCoverageRow(setC, "BBB", 2000, 1.toDecimal(), 2.toDecimal(), null, null, null)

    }

    private fun addABCoverageSets(db: JooqContext)
    {
        db.addCoverageSet(touchstoneVersionId, "First", "AF", "without", "routine", id = setA)
        db.addCoverageSet(touchstoneVersionId, "Second", "BF", "without", "campaign", id = setB)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setA, 0)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setB, 1)
    }

    private fun addABCountries(db: JooqContext)
    {
        db.addCountries(listOf("AAA", "BBB"))
    }

    private fun giveUnorderedCoverageSetAndDataWithDuplicatesToScenario(db: JooqContext, addCountries: Boolean = true)
    {

        addABCoverageSets(db);

        if (addCountries)
        {
            addABCountries(db);
        }

        db.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(100), BigDecimal(0.2))
        db.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(200), BigDecimal(0.6))
        db.addCoverageRow(setA, "AAA", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(300), BigDecimal(0.8))

        db.addCoverageRow(setA, "BBB", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(400), BigDecimal(0.1))
        db.addCoverageRow(setA, "BBB", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(500), BigDecimal(0.2))
        db.addCoverageRow(setA, "BBB", 2001, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(600), BigDecimal(0.3))

        db.addCoverageRow(setB, "BBB", 2002, 1.toDecimal(), 2.toDecimal(), "1-2", BigDecimal(1000), BigDecimal(0.5))
    }

    private fun giveUnorderedCoverageSetsToScenario(db: JooqContext)
    {
        db.addCoverageSet(touchstoneVersionId, "First", "YF", "without", "campaign", id = setA)
        db.addCoverageSet(touchstoneVersionId, "Second", "YF", "with", "campaign", id = setB)
        db.addCoverageSet(touchstoneVersionId, "Third", "YF", "bestminus", "campaign", id = setC)
        db.addCoverageSet(touchstoneVersionId, "Fourth", "BF", "with", "campaign", id = setD)
        db.addCoverageSet(touchstoneVersionId, "Fifth", "BF", "without", "campaign", id = setE)
        db.addCoverageSet(touchstoneVersionId, "Sixth", "BF", "bestminus", "campaign", id = setF)

        // Deliberately out of order, to check ordering logic later
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setF, 5)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setB, 1)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setA, 0)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setC, 2)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setD, 3)
        db.addCoverageSetToScenario(scenarioId, touchstoneVersionId, setE, 4)

    }

    private fun assertLongCoverageRowListEqualWithCoverageTolerance(actual: List<LongCoverageRow>,
                                                                    expected: List<LongCoverageRow>,
                                                                    tolerance: Double)
    {
        //Do an 'almost exact' list comparison on expected LongCoverageRows, allowing for tolerance on floating point
        //coverage values
        assertThat(actual.count()).isEqualTo(expected.count())
        for (i in 0..expected.count()-1)
        {
            val e = expected[i]
            val a = actual[i]

            assertThat(a.scenario).isEqualTo(e.scenario)
            assertThat(a.setName).isEqualTo(e.setName)
            assertThat(a.vaccine).isEqualTo(e.vaccine)
            assertThat(a.activityType).isEqualTo(e.activityType)
            assertThat(a.countryCode).isEqualTo(e.countryCode)
            assertThat(a.country).isEqualTo(e.country)
            assertThat(a.year).isEqualTo(e.year)
            assertThat(a.ageFirst).isEqualTo(e.ageFirst)
            assertThat(a.ageLast).isEqualTo(e.ageLast)
            assertThat(a.ageRangeVerbatim).isEqualTo(e.ageRangeVerbatim)
            assertThat(a.target).isEqualTo(e.target)
            assertThat(a.coverage).isCloseTo(e.coverage, within(BigDecimal(tolerance)))
        }

    }
}