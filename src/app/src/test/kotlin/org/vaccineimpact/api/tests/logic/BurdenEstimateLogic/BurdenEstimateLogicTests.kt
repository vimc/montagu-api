package org.vaccineimpact.api.tests.logic.BurdenEstimateLogic

import com.nhaarman.mockito_kotlin.*
import com.opencsv.CSVReader
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.mockito.Mockito
import org.vaccineimpact.api.app.controllers.helpers.ResponsibilityPath
import org.vaccineimpact.api.app.errors.InconsistentDataError
import org.vaccineimpact.api.app.errors.InvalidOperationError
import org.vaccineimpact.api.app.errors.MissingRowsError
import org.vaccineimpact.api.app.errors.UnknownObjectError
import org.vaccineimpact.api.app.logic.RepositoriesBurdenEstimateLogic
import org.vaccineimpact.api.app.models.BurdenEstimateOutcome
import org.vaccineimpact.api.app.repositories.*
import org.vaccineimpact.api.app.repositories.burdenestimates.BurdenEstimateWriter
import org.vaccineimpact.api.app.repositories.jooq.ResponsibilityInfo
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.io.ByteArrayOutputStream
import java.io.StringReader
import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneOffset

abstract class BurdenEstimateLogicTests : MontaguTests()
{
    protected val defaultEstimateSet = BurdenEstimateSet(
            1, Instant.now(), "test.user",
            BurdenEstimateSetType(BurdenEstimateSetTypeCode.CENTRAL_AVERAGED, "mean"),
            BurdenEstimateSetStatus.EMPTY,
            emptyList()
    )

    protected val username = "some.user"
    protected val timestamp = LocalDateTime.of(2017, Month.JUNE, 13, 12, 30).toInstant(ZoneOffset.UTC)

    protected val defaultProperties = CreateBurdenEstimateSet(
            BurdenEstimateSetType(BurdenEstimateSetTypeCode.CENTRAL_UNKNOWN),
            modelRunParameterSet = null
    )

    protected val disease = "disease-1"
    protected val responsibilityId = 1
    protected val setId = 1
    protected val groupId = "group-1"
    protected val dereferencedGroupId = "d-group-1"
    protected val touchstoneVersionId = "touchstone-1"
    protected val scenarioId = "scenario-1"

    protected fun mockWriter(): BurdenEstimateWriter
    {
        return mock {
            on { addEstimatesToSet(any(), any(), any()) } doAnswer { args ->
                // Force evaluation of sequence
                args.getArgument<Sequence<BurdenEstimateWithRunId>>(1).toList()
                Unit
            }
        }
    }

    protected fun mockEstimatesRepository(mockEstimateWriter: BurdenEstimateWriter = mockWriter(),
                                        existingBurdenEstimateSet: BurdenEstimateSet = defaultEstimateSet
    ): BurdenEstimateRepository
    {
        return mock {
            on { getBurdenEstimateSetForResponsibility(any(), any()) } doReturn existingBurdenEstimateSet
            on { getResponsibilityInfo(any(), any(), any()) } doReturn
                    ResponsibilityInfo(responsibilityId, disease, "open", setId)
            on { getEstimateWriter(any()) } doReturn mockEstimateWriter
        }
    }

    protected val fakeExpectations = Expectations(1, "desc", 2000..2001, 1..2, CohortRestriction(),
            listOf(Country("AFG", "")),
            listOf())

    protected fun mockExpectationsRepository(): ExpectationsRepository = mock {
        on { getExpectationsForResponsibility(responsibilityId) } doReturn ExpectationMapping(fakeExpectations, listOf(), disease)
    }

    protected fun mockGroupRepository(): ModellingGroupRepository = mock {
        on { getModellingGroup(groupId) } doReturn ModellingGroup(groupId, "description")
    }

    protected val validData = sequenceOf(
            BurdenEstimateWithRunId("yf", null, 2000, 1, "AFG", "Afghanistan", 1000F, mapOf(
                    "deaths" to 10F,
                    "cases" to 100F
            )),
            BurdenEstimateWithRunId("yf", null, 2001, 1, "AFG", "Afghanistan", 1000F, mapOf(
                    "deaths" to 10F,
                    "cases" to 100F
            )),
            BurdenEstimateWithRunId("yf", null, 2000, 2, "AFG", "Afghanistan", 1000F, mapOf(
                    "deaths" to 10F,
                    "cases" to 100F
            )),
            BurdenEstimateWithRunId("yf", null, 2001, 2, "AFG", "Afghanistan", 1000F, mapOf(
                    "deaths" to 10F,
                    "cases" to 100F
            )))

}