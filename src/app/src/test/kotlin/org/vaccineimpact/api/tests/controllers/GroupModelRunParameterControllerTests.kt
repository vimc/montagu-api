package org.vaccineimpact.api.tests.controllers

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.app.MultipartDataMap
import org.vaccineimpact.api.app.context.ActionContext
import org.vaccineimpact.api.app.controllers.GroupModelRunParametersController
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.ModellingGroupRepository
import org.vaccineimpact.api.app.repositories.SimpleDataSet
import org.vaccineimpact.api.app.repositories.TouchstoneRepository
import org.vaccineimpact.api.models.ModelRun
import org.vaccineimpact.api.models.ModelRunParameterSet
import org.vaccineimpact.api.models.Touchstone
import org.vaccineimpact.api.models.TouchstoneStatus
import org.vaccineimpact.api.serialization.FlexibleDataTable
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.io.StringReader
import java.time.Instant

class GroupModelRunParameterControllerTests : MontaguTests()
{
    @Test
    fun `can get model run params`()
    {
        val modelRunParameterSets = listOf(ModelRunParameterSet(1, "description", "model", "user", Instant.now(), "yf"))

        val mockContext = mock<ActionContext> {
            on { params(":group-id") } doReturn "group-1"
            on { params(":touchstone-id") } doReturn "touchstone-1"
            on { params(":scenario-id") } doReturn "scenario-1"
        }
        val repo = mockRepository(modelRunParameterSets = modelRunParameterSets)

        val controller = GroupModelRunParametersController(mockContext, repo, mock())
        assertThat(controller.getModelRunParameterSets()).isEqualTo(modelRunParameterSets)
    }

    @Test
    fun `can get model run params csv`()
    {
        val context = mock<ActionContext> {
            on { it.params(":group-id") } doReturn "gId"
            on { it.params(":touchstone-id") } doReturn "touchstone-1"
            on { it.params(":model-run-parameter-set-id") } doReturn "1"
            on { hasPermission(any()) } doReturn true
        }

        val data = GroupModelRunParametersController(context, mockRepository(), mockModellingGroupRepository())
                .getModelRunParameterSet()

        assertThat(data.contentType).isEqualTo("text/csv")
        assertThat(data.data.first().runId).isEqualTo("1")
    }

    @Test
    fun `throws UnknownObjectError if touchstone is in preparation when getting model run params`()
    {
        val mockContext = mock<ActionContext> {
            on { params(":group-id") } doReturn "group-1"
            on { params(":touchstone-id") } doReturn "touchstone-bad"
            on { params(":scenario-id") } doReturn "scenario-1"
        }

        val repo = mockRepository()
        val controller = GroupModelRunParametersController(mockContext, repo, mock())
        Assertions.assertThatThrownBy { controller.getModelRunParameterSets() }
                .isInstanceOf(UnknownObjectError::class.java)
    }

    @Test
    fun `can upload model run params`()
    {
        val params = mapOf("param1" to "value1", "param2" to "value2")
        @Suppress("RemoveExplicitTypeArguments")
        val modelRuns = listOf<ModelRun>(ModelRun("run1", params))

        val mockContext = mock<ActionContext> {
            on { csvData<ModelRun>(any(), any<String>()) } doReturn modelRuns.asSequence()
            on { username } doReturn "user.name"
            on { params(":group-id") } doReturn "group-1"
            on { params(":touchstone-id") } doReturn "touchstone-1"
            on { getParts(anyOrNull()) } doReturn MultipartDataMap(
                    "disease" to "disease-1",
                    "description" to "some description",
                    // This is passed to another mocked method, so its contents doesn't matter
                    "file" to ""
            )
        }
        val repo = mockRepository(modelRuns = modelRuns)

        val expectedPath = "/v1/modelling-groups/group-1/model-run-parameters/11/"
        val controller = GroupModelRunParametersController(mockContext, repo, mock())
        val objectCreationUrl = controller.addModelRunParameters()
        assertThat(objectCreationUrl).endsWith(expectedPath)
    }

    @Test
    fun `throws UnknownObjectError if touchstone is in preparation when adding model run params`()
    {
        val mockContext = mock<ActionContext> {
            on { params(":group-id") } doReturn "group-1"
            on { params(":touchstone-id") } doReturn "touchstone-bad"
            on { getPart(eq("disease"), anyOrNull()) } doReturn StringReader("disease-1")
        }
        val touchstoneSet = mockTouchstones()
        val repo = mockRepository(touchstoneSet)

        val controller = GroupModelRunParametersController(mockContext, repo, mock())
        Assertions.assertThatThrownBy {
            controller.addModelRunParameters()
        }.isInstanceOf(UnknownObjectError::class.java)
    }


    private fun mockRepository(touchstoneSet: SimpleDataSet<Touchstone, String> = mockTouchstones(),
                               modelRuns: List<ModelRun> = listOf(),
                               modelRunParameterSets: List<ModelRunParameterSet> = listOf()): BurdenEstimateRepository
    {
        val touchstoneRepo = mock<TouchstoneRepository> {
            on { touchstones } doReturn touchstoneSet
        }
        return mock {
            on { touchstoneRepository } doReturn touchstoneRepo
            on { it.getModelRunParameterSets(eq("group-1"), eq("touchstone-1")) } doReturn modelRunParameterSets
            on {
                it.addModelRunParameterSet(eq("group-1"), eq("touchstone-1"), eq("disease-1"),
                        eq("some description"),
                        eq(modelRuns), eq("user.name"), any())
            } doReturn 11
            on { it.getModelRunParametersData(any()) } doReturn FlexibleDataTable.new(
                    sequenceOf(ModelRun("1", mapOf("<param_1>" to "aa", "<param_2>" to "bb"))),
                    listOf("<param_1>", "<param_2>")
            )
        }
    }

    private fun mockTouchstones() = mock<SimpleDataSet<Touchstone, String>> {
        on { get("touchstone-1") } doReturn Touchstone("touchstone-1", "touchstone", 1, "Description", TouchstoneStatus.OPEN)
        on { get("touchstone-bad") } doReturn Touchstone("touchstone-bad", "touchstone", 1, "not open", TouchstoneStatus.IN_PREPARATION)
    }

    private fun mockModellingGroupRepository(): ModellingGroupRepository
    {
        return mock {
            on { getTouchstone("touchstone-1") } doReturn Touchstone("touchstone-1", "touchstone", 1, "Description", TouchstoneStatus.OPEN)
        }
    }

}