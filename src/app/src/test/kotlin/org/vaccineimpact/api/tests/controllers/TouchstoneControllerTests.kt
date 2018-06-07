package org.vaccineimpact.api.tests.controllers

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.controllers.TouchstoneController
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.repositories.TouchstoneRepository
import org.vaccineimpact.api.app.repositories.inmemory.InMemoryDataSet
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.models.permissions.ReifiedPermission
import org.vaccineimpact.api.serialization.DataTable
import org.vaccineimpact.api.serialization.SplitData
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.math.BigDecimal

class TouchstoneControllerTests : MontaguTests()
{
    private val openTouchstoneVersion = TouchstoneVersion("t-1", "t", 1, "description", TouchstoneStatus.OPEN)
    private val inPrepTouchstoneVersion = TouchstoneVersion("t-2", "t", 2, "description", TouchstoneStatus.IN_PREPARATION)
    private val touchstone = Touchstone(
            id = "t",
            description = "touchstone description",
            comment = "comment",
            versions = listOf(openTouchstoneVersion, inPrepTouchstoneVersion)
    )
    private val source = "test-source"
    private val type = "test-type"
    private val scenario = Scenario("id", "desc", "disease", listOf("t1, t2"))

    @Test
    fun `getTouchstones returns touchstones`()
    {
        val touchstones = listOf(Touchstone(
                id = "t",
                description = "touchstone description",
                comment = "comment",
                versions = listOf(openTouchstoneVersion)
        ))
        val repo = mock<TouchstoneRepository> {
            on { this.getTouchstones() } doReturn listOf(touchstone)
        }
        val context = mock<ActionContext> {
            on { hasPermission(any()) } doReturn true
        }

        val controller = TouchstoneController(context, repo)
        assertThat(controller.getTouchstones()).hasSameElementsAs(touchstones)
    }

    @Test
    fun `getTouchstones filters out in-preparation touchstones if the user doesn't have the permissions`()
    {
        val touchstones = listOf(
                openTouchstoneVersion, inPrepTouchstoneVersion
        )
        val repo = mock<TouchstoneRepository> {
            on { this.touchstoneVersions } doReturn InMemoryDataSet(touchstones)
        }

        val permissiveContext = mock<ActionContext> {
            on { hasPermission(any()) } doReturn true
        }
        assertThat(TouchstoneController(permissiveContext, repo).getTouchstones()).hasSize(2)

        val limitedContext = mock<ActionContext> {
            on { hasPermission(any()) } doReturn false
        }
        assertThat(TouchstoneController(limitedContext, repo).getTouchstones()).hasSize(1)
    }

    @Test
    fun `getScenario fetches from repository`()
    {
        val coverageSets = listOf(CoverageSet(1, "t1", "name", "vaccine", GAVISupportLevel.WITH, ActivityType.CAMPAIGN))
        val result = ScenarioAndCoverageSets(scenario, coverageSets)

        val repo = mock<TouchstoneRepository> {
            on { getScenario(any(), any()) } doReturn result
            on { touchstoneVersions } doReturn InMemoryDataSet(listOf(openTouchstoneVersion))
        }

        val context = mock<ActionContext> {
            on { hasPermission(any()) } doReturn true
            on { params(":touchstone-version-id") } doReturn openTouchstoneVersion.id
            on { params(":scenario-id") } doReturn scenario.id
        }

        val data = TouchstoneController(context, repo).getScenario()

        verify(repo).getScenario(eq(openTouchstoneVersion.id), eq(scenario.id))
        assertThat(data.touchstone).isEqualTo(openTouchstoneVersion)
        assertThat(data.scenario).isEqualTo(scenario)
        assertThat(data.coverageSets).hasSameElementsAs(coverageSets)
    }

    @Test
    fun `getScenario requires touchstones prepare permission for in-preparation touchstone`()
    {
        val repo = mock<TouchstoneRepository> {
            on { touchstoneVersions } doReturn InMemoryDataSet(listOf(inPrepTouchstoneVersion))
            on { getScenario(any(), any()) } doReturn ScenarioAndCoverageSets(scenario, emptyList())
        }
        val context = mock<ActionContext> {
            on { params(":touchstone-version-id") } doReturn inPrepTouchstoneVersion.id
            on { params(":scenario-id") } doReturn scenario.id
        }
        TouchstoneController(context, repo).getScenario()
        verify(context).requirePermission(ReifiedPermission("touchstones.prepare", Scope.Global()))
    }


    @Test
    fun `getDemographicData fetches from repository`()
    {
        val demographicMetadata = DemographicDataForTouchstone(openTouchstoneVersion,
                DemographicMetadata("id", "name", null, listOf(), "people", "age", source))

        val repo = mock<TouchstoneRepository> {
            on { getDemographicData(type, source, openTouchstoneVersion.id) } doReturn
                    SplitData(demographicMetadata, DataTable.new(emptySequence()))
            on { touchstoneVersions } doReturn InMemoryDataSet(listOf(openTouchstoneVersion))
        }

        val context = mock<ActionContext> {
            on { hasPermission(any()) } doReturn true
            on { params(":touchstone-version-id") } doReturn openTouchstoneVersion.id
            on { params(":type-code") } doReturn type
            on { params(":source-code") } doReturn source
        }

        val data = TouchstoneController(context, repo).getDemographicDataAndMetadata()
        assertThat(data.structuredMetadata).isEqualTo(demographicMetadata)
    }

    @Test
    fun `gets wide demographic data`()
    {
        val repo = getRepoWithDemographicData()

        val context = mock<ActionContext> {
            on { hasPermission(any()) } doReturn true
            on { params(":touchstone-version-id") } doReturn openTouchstoneVersion.id
            on { params(":type-code") } doReturn type
            on { params(":source-code") } doReturn source
            on { queryParams("format") } doReturn "wide"
        }

        val data = TouchstoneController(context, repo)
                .getDemographicDataAndMetadata()
                .data.toList()

        val first = data[0] as WideDemographicRow
        val second = data[1] as WideDemographicRow
        val third = data[1] as WideDemographicRow

        assertThat(data.count() == 3)
        assertThat(first.valuesPerYear.keys.count() == 3
                && first.valuesPerYear.containsKey(1980) && first.valuesPerYear.containsKey(1985)
                && first.valuesPerYear.containsKey(1980))

        assertThat(second.valuesPerYear.keys.count() == 1)
        assertThat(third.valuesPerYear.keys.count() == 2)

    }

    @Test
    fun `gets long demographic data if format=long`()
    {
        val repo = getRepoWithDemographicData()

        val context = mock<ActionContext> {
            on { hasPermission(any()) } doReturn true
            on { params(":touchstone-version-id") } doReturn openTouchstoneVersion.id
            on { params(":type-code") } doReturn type
            on { params(":source-code") } doReturn source
            on { queryParams("format") } doReturn "long"
        }

        val data = TouchstoneController(context, repo)
                .getDemographicDataAndMetadata().data

        Assertions.assertThat(data.first() is LongDemographicRow).isTrue()
    }

    @Test
    fun `throws error if supplied format is invalid`()
    {
        val repo = getRepoWithDemographicData()

        val context = mock<ActionContext> {
            on { hasPermission(any()) } doReturn true
            on { params(":touchstone-version-id") } doReturn openTouchstoneVersion.id
            on { params(":type-code") } doReturn type
            on { params(":source-code") } doReturn source
            on { queryParams("format") } doReturn "678hj"
        }

        Assertions.assertThatThrownBy {
            TouchstoneController(context, repo).getDemographicDataAndMetadata()
        }.isInstanceOf(BadRequest::class.java)
    }

    private fun getRepoWithDemographicData(): TouchstoneRepository
    {
        val demographicMetadata = DemographicDataForTouchstone(openTouchstoneVersion,
                DemographicMetadata("id", "name", null, listOf(), "people", "age", source))

        val fakeRows = sequenceOf(
                LongDemographicRow(123, "ABC", "ABC-country", 0, 5, 1980, "F", BigDecimal(1200)),
                LongDemographicRow(123, "ABC", "ABC-country", 0, 5, 1985, "F", BigDecimal(1300)),
                LongDemographicRow(123, "ABC", "ABC-country", 0, 5, 1990, "F", BigDecimal(1400)),
                LongDemographicRow(123, "ABC", "ABC-country", 5, 10, 1980, "F", BigDecimal(1500)),
                LongDemographicRow(456, "DEF", "DEF-country", 0, 5, 1980, "F", BigDecimal(2200)),
                LongDemographicRow(456, "DEF", "DEF-country", 0, 5, 1985, "F", BigDecimal(2300))
        )

        return mock<TouchstoneRepository> {
            on { getDemographicData(type, source, openTouchstoneVersion.id) } doReturn
                    SplitData(demographicMetadata, DataTable.new(fakeRows))
            on { touchstoneVersions } doReturn InMemoryDataSet(listOf(openTouchstoneVersion))
        }
    }
}
