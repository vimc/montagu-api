package org.vaccineimpact.api.tests.logic.BurdenEstimateLogic

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.app.errors.InvalidOperationError
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.logic.RepositoriesBurdenEstimateLogic
import org.vaccineimpact.api.app.repositories.BurdenEstimateRepository
import org.vaccineimpact.api.app.repositories.ModellingGroupRepository
import org.vaccineimpact.api.app.repositories.jooq.ResponsibilityInfo
import org.vaccineimpact.api.models.BurdenEstimateSetType
import org.vaccineimpact.api.models.BurdenEstimateSetTypeCode
import org.vaccineimpact.api.models.ModellingGroup

class CreateBurdenEstimateSetTests : BurdenEstimateLogicTests()
{
    @Test
    fun `central estimates update current estimate set`()
    {
        val repo = mock<BurdenEstimateRepository> {
            on { getResponsibilityInfo(dereferencedGroupId, touchstoneVersionId, scenarioId) } doReturn
                    ResponsibilityInfo(1, disease, "empty", 1)
            on { addBurdenEstimateSet(1, username, timestamp, 12, defaultProperties) } doReturn 10
        }

        val groupRepo = mock<ModellingGroupRepository> {
            on { getModellingGroup(groupId) } doReturn ModellingGroup(dereferencedGroupId, "")
            on { getlatestModelVersion(dereferencedGroupId, disease) } doReturn 12
        }

        val sut = RepositoriesBurdenEstimateLogic(groupRepo, repo, mock(), mock(), mock())
        sut.createBurdenEstimateSet(groupId, touchstoneVersionId, scenarioId, defaultProperties, username, timestamp)

        verify(repo).updateCurrentBurdenEstimateSet(1, 10,
                BurdenEstimateSetType(BurdenEstimateSetTypeCode.CENTRAL_UNKNOWN))
    }

    @Test
    fun `stochastic estimates update current estimate set`()
    {
        val properties = defaultProperties.withType(BurdenEstimateSetTypeCode.STOCHASTIC)
        val repo = mock<BurdenEstimateRepository> {
            on { getResponsibilityInfo(dereferencedGroupId, touchstoneVersionId, scenarioId) } doReturn
                    ResponsibilityInfo(1, disease, "empty", 0)
            on { addBurdenEstimateSet(1, username, timestamp, 12, properties) } doReturn 10
        }

        val groupRepo = mock<ModellingGroupRepository> {
            on { getModellingGroup(groupId) } doReturn ModellingGroup(dereferencedGroupId, "")
            on { getlatestModelVersion(dereferencedGroupId, disease) } doReturn 12
        }

        val sut = RepositoriesBurdenEstimateLogic(groupRepo, repo, mock(), mock(), mock())
        sut.createBurdenEstimateSet(groupId, touchstoneVersionId, scenarioId, properties, username, timestamp)

        verify(repo).updateCurrentBurdenEstimateSet(1, 10,
                BurdenEstimateSetType(BurdenEstimateSetTypeCode.STOCHASTIC))
    }


    @Test
    fun `cannot create burden estimate set if group has no model`()
    {
        val repo = mock<BurdenEstimateRepository> {
            on { getResponsibilityInfo(dereferencedGroupId, touchstoneVersionId, scenarioId) } doReturn ResponsibilityInfo(1, disease, "empty", 1)
        }

        val groupRepo = mock<ModellingGroupRepository> {
            on { getModellingGroup(groupId) } doReturn ModellingGroup(dereferencedGroupId, "")
        }
        val sut = RepositoriesBurdenEstimateLogic(groupRepo, repo, mock(), mock(), mock())
        sut.createBurdenEstimateSet(groupId, touchstoneVersionId, scenarioId, defaultProperties, username, timestamp)

        verify(groupRepo).getlatestModelVersion(dereferencedGroupId, disease)
    }

    @Test
    fun `cannot create burden estimate set if responsibility set status is submitted`()
    {
        val repo = mock<BurdenEstimateRepository> {
            on { getResponsibilityInfo(dereferencedGroupId, touchstoneVersionId, scenarioId) } doReturn
                    ResponsibilityInfo(1, disease, "submitted", 1)
        }

        val groupRepo = mock<ModellingGroupRepository> {
            on { getModellingGroup(groupId) } doReturn ModellingGroup(dereferencedGroupId, "")
            on { getlatestModelVersion(dereferencedGroupId, disease) } doReturn 12
        }

        val sut = RepositoriesBurdenEstimateLogic(groupRepo, repo, mock(), mock(), mock())

        assertThatThrownBy {
            sut.createBurdenEstimateSet(groupId, touchstoneVersionId, scenarioId, defaultProperties, username, timestamp)
        }.isInstanceOf(InvalidOperationError::class.java)
                .hasMessage("the following problems occurred:\nThe burden estimates uploaded for this touchstone have been submitted for review." +
                        " You cannot upload any new estimates.")

    }

    @Test
    fun `cannot create burden estimate set if responsibility set status is approved`()
    {
        val repo = mock<BurdenEstimateRepository> {
            on { getResponsibilityInfo(dereferencedGroupId, touchstoneVersionId, scenarioId) } doReturn
                    ResponsibilityInfo(1, disease, "approved", 1)
        }

        val groupRepo = mock<ModellingGroupRepository> {
            on { getModellingGroup(groupId) } doReturn ModellingGroup(dereferencedGroupId, "")
            on { getlatestModelVersion(dereferencedGroupId, disease) } doReturn 12
        }

        val sut = RepositoriesBurdenEstimateLogic(groupRepo, repo, mock(), mock(), mock())

        assertThatThrownBy {
            sut.createBurdenEstimateSet(groupId, touchstoneVersionId, scenarioId, defaultProperties, username, timestamp)
        }.isInstanceOf(InvalidOperationError::class.java)
                .hasMessage("the following problems occurred:\nThe burden estimates uploaded for this touchstone have been reviewed and approved." +
                        " You cannot upload any new estimates.")
    }

    @Test
    fun `throws unknown create burden estimate set if model run parameter set does not exist`()
    {
        val repo = mock<BurdenEstimateRepository> {
            on { getResponsibilityInfo(dereferencedGroupId, touchstoneVersionId, scenarioId) } doReturn
                    ResponsibilityInfo(1, disease, "open", 1)
            on { checkModelRunParameterSetExists(any(), any(), any()) } doThrow UnknownObjectError("test", "test")

        }

        val groupRepo = mock<ModellingGroupRepository> {
            on { getModellingGroup(groupId) } doReturn ModellingGroup(dereferencedGroupId, "")
            on { getlatestModelVersion(dereferencedGroupId, disease) } doReturn 12
        }

        val sut = RepositoriesBurdenEstimateLogic(groupRepo, repo, mock(), mock(), mock())

        assertThatThrownBy {
            sut.createBurdenEstimateSet(groupId, touchstoneVersionId, scenarioId,
                    defaultProperties.copy(modelRunParameterSet = 20), username, timestamp)
        }.isInstanceOf(UnknownObjectError::class.java)
                .hasMessageContaining("test")
    }

    @Test
    fun `checks responsibility info is valid before creating set`()
    {
        val repo = mock<BurdenEstimateRepository> {
            on { getResponsibilityInfo(dereferencedGroupId, touchstoneVersionId, scenarioId) } doReturn
                    ResponsibilityInfo(1, disease, "open", 1)
        }

        val groupRepo = mock<ModellingGroupRepository> {
            on { getModellingGroup(groupId) } doReturn ModellingGroup(dereferencedGroupId, "")
            on { getlatestModelVersion(dereferencedGroupId, disease) } doReturn 12
        }

        val sut = RepositoriesBurdenEstimateLogic(groupRepo, repo, mock(), mock(), mock())

        sut.createBurdenEstimateSet(groupId, touchstoneVersionId, scenarioId,
                defaultProperties, username, timestamp)

        verify(repo).getResponsibilityInfo(dereferencedGroupId, touchstoneVersionId, scenarioId)
    }

    @Test
    fun `getResponsibilityInfo throws UnknownObjectError for touchstone`()
    {

    }

    @Test
    fun `getResponsibilityInfo throws UnknownObjectError for scenario`()
    {

    }

    @Test
    fun `getResponsibilityInfo throws UnknownObjectError for responsibility`()
    {

    }
}