package org.vaccineimpact.api.tests.logic.CoverageLogic

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.mockito.internal.verification.Times
import org.vaccineimpact.api.app.errors.BadRequest
import org.vaccineimpact.api.app.logic.RepositoriesCoverageLogic
import org.vaccineimpact.api.app.repositories.ExpectationsRepository
import org.vaccineimpact.api.app.repositories.TouchstoneRepository
import org.vaccineimpact.api.models.ActivityType
import org.vaccineimpact.api.models.CoverageIngestionRow
import org.vaccineimpact.api.models.GAVISupportLevel
import org.vaccineimpact.api.models.GenderEnum
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.math.BigDecimal

class SaveCoverageTests : MontaguTests()
{
    private val testSequence = sequenceOf(
            // each of these belongs to a new coverage set
            CoverageIngestionRow("HepB_BD", "AFG", ActivityType.CAMPAIGN, true, 2021, 1, 10, GenderEnum.BOTH, 100F, 78.8F),
            CoverageIngestionRow("HepB_BD", "AFG", ActivityType.ROUTINE, false, 2022, 1, 10, GenderEnum.BOTH, 100F, 65.5F),
            CoverageIngestionRow("HepB", "AFG", ActivityType.CAMPAIGN, true, 2025, 1, 10, GenderEnum.BOTH, 100F, 65.5F),
            CoverageIngestionRow("HepB", "AFG", ActivityType.ROUTINE, true, 2026, 1, 10, GenderEnum.BOTH, 100F, 65.5F),

            // belongs to same coverage set as the first row
            CoverageIngestionRow("HepB_BD", "AFG", ActivityType.CAMPAIGN, true, 2022, 1, 10, GenderEnum.BOTH, 100F, 78.8F)
    )

    private val mockExpectationsRepo = mock<ExpectationsRepository> {
        on { getExpectedGAVICoverageCountries() } doReturn listOf("AFG")
    }

    @Test
    fun `new coverage sets are created as needed`()
    {
        val mockRepo = mock<TouchstoneRepository>() {
            on { getGenders() } doReturn mapOf(GenderEnum.BOTH to 111)
        }
        val sut = RepositoriesCoverageLogic(mock(), mock(), mockRepo, mock(), mockExpectationsRepo)
        sut.saveCoverageForTouchstone("t1", testSequence, validate = false)
        verify(mockRepo, Times(1)).createCoverageSet("t1", "HepB_BD", ActivityType.CAMPAIGN, GAVISupportLevel.WITH)
        verify(mockRepo, Times(1)).createCoverageSet("t1", "HepB_BD", ActivityType.ROUTINE, GAVISupportLevel.WITH)
        verify(mockRepo, Times(1)).createCoverageSet("t1", "HepB", ActivityType.CAMPAIGN, GAVISupportLevel.WITH)
        verify(mockRepo, Times(1)).createCoverageSet("t1", "HepB", ActivityType.ROUTINE, GAVISupportLevel.WITH)
        verify(mockRepo, Times(5)).newCoverageRowRecord(any(), any(), any(), any(), any(), any(), any(), any(), any())
        verify(mockRepo).saveCoverageForTouchstone(any(), any())
        verify(mockRepo).getGenders()
        verifyNoMoreInteractions(mockRepo)
    }

    @Test
    fun `rows are created correctly`()
    {
        val mockRepo = mock<TouchstoneRepository> {
            on { getGenders() } doReturn mapOf(GenderEnum.BOTH to 111)
            on { createCoverageSet(any(), any(), any(), any()) } doReturn 1
        }
        val sut = RepositoriesCoverageLogic(mock(), mock(), mockRepo, mock(), mockExpectationsRepo)
        sut.saveCoverageForTouchstone("t1", testSequence, validate = false)
        verify(mockRepo, Times(1)).newCoverageRowRecord(1,
                "AFG",
                2020,
                BigDecimal(1),
                BigDecimal(10),
                111,
                true,
                100F.toBigDecimal(),
                78.8.toBigDecimal())
        verify(mockRepo, Times(1)).newCoverageRowRecord(1,
                "AFG",
                2022,
                BigDecimal(1),
                BigDecimal(10),
                111,
                false,
                100F.toBigDecimal(),
                65.5.toBigDecimal())
    }

    @Test
    fun `coverage records are saved to correct coverage set`()
    {
        val mockRepo = mock<TouchstoneRepository> {
            on { getGenders() } doReturn mapOf(GenderEnum.BOTH to 111)
            on { createCoverageSet("t1", "HepB_BD", ActivityType.CAMPAIGN, GAVISupportLevel.WITH) } doReturn 1
            on { createCoverageSet("t1", "HepB_BD", ActivityType.ROUTINE, GAVISupportLevel.WITH) } doReturn 2
            on { createCoverageSet("t1", "HepB", ActivityType.CAMPAIGN, GAVISupportLevel.WITH) } doReturn 3
            on { createCoverageSet("t1", "HepB", ActivityType.ROUTINE, GAVISupportLevel.WITH) } doReturn 4
        }
        val sut = RepositoriesCoverageLogic(mock(), mock(), mockRepo, mock(), mockExpectationsRepo)
        sut.saveCoverageForTouchstone("t1", testSequence, validate = false)
        verify(mockRepo, Times(1)).newCoverageRowRecord(eq(1), any(), eq(2020), any(), any(), any(), any(), any(), any())
        verify(mockRepo, Times(1)).newCoverageRowRecord(eq(1), any(), eq(2021), any(), any(), any(), any(), any(), any())
        verify(mockRepo, Times(1)).newCoverageRowRecord(eq(2), any(), eq(2022), any(), any(), any(), any(), any(), any())
        verify(mockRepo, Times(1)).newCoverageRowRecord(eq(3), any(), eq(2025), any(), any(), any(), any(), any(), any())
        verify(mockRepo, Times(1)).newCoverageRowRecord(eq(4), any(), eq(2026), any(), any(), any(), any(), any(), any())
    }

    @Test
    fun `validation detects missing rows for given routine diseases only`()
    {
        val mockRepo = mock<TouchstoneRepository> {
            on { getGenders() } doReturn mapOf(GenderEnum.BOTH to 111)
        }
        val testSequence = sequenceOf(
                CoverageIngestionRow("HepB", "AFG", ActivityType.ROUTINE, true, 2025, 1, 10, GenderEnum.BOTH, 100F, 65.5F),
                CoverageIngestionRow("YF", "AFG", ActivityType.CAMPAIGN, true, 2025, 1, 10, GenderEnum.BOTH, 100F, 65.5F),
                CoverageIngestionRow("Measles", "AFG", ActivityType.ROUTINE, true, 2025, 1, 10, GenderEnum.BOTH, 100F, 65.5F))
        val sut = RepositoriesCoverageLogic(mock(), mock(), mockRepo, mock(), mockExpectationsRepo)

        val expectedMessage = """Missing 18 rows for vaccines HepB, Measles:
 *HepB, AFG, 2021
 *HepB, AFG, 2022
 *HepB, AFG, 2023
 *HepB, AFG, 2024
 *HepB, AFG, 2026
and 13 others"""

        assertThatThrownBy {
            sut.saveCoverageForTouchstone("t1", testSequence)
        }.isInstanceOf(BadRequest::class.java)
                .hasMessageContaining(expectedMessage)
    }

    @Test
    fun `any number of rows fine for campaign`()
    {
        val mockRepo = mock<TouchstoneRepository> {
            on { getGenders() } doReturn mapOf(GenderEnum.BOTH to 111)
        }
        // missing rows and duplicates fine for campaign
        val testSequence = sequenceOf(
                CoverageIngestionRow("HepB", "AFG", ActivityType.CAMPAIGN, true, 2025, 1, 10, GenderEnum.BOTH, 100F, 65.5F),
                CoverageIngestionRow("YF", "AFG", ActivityType.CAMPAIGN, true, 2025, 1, 10, GenderEnum.BOTH, 100F, 65.5F),
                CoverageIngestionRow("YF", "AFG", ActivityType.CAMPAIGN, true, 2025, 1, 10, GenderEnum.BOTH, 100F, 65.5F))
        val sut = RepositoriesCoverageLogic(mock(), mock(), mockRepo, mock(), mockExpectationsRepo)
        sut.saveCoverageForTouchstone("t1", testSequence)
        verify(mockRepo, Times(3)).newCoverageRowRecord(any(), any(), any(), any(), any(), any(), any(), any(), any())
    }


    @Test
    fun `validation detects duplicate `()
    {
        val mockRepo = mock<TouchstoneRepository> {
            on { getGenders() } doReturn mapOf(GenderEnum.BOTH to 111)
        }
        val testSequence = sequenceOf(
                CoverageIngestionRow("HepB", "AFG", ActivityType.ROUTINE, true, 2022, 1, 10, GenderEnum.BOTH, 100F, 65.5F),
                CoverageIngestionRow("HepB", "AFG", ActivityType.ROUTINE, true, 2023, 1, 10, GenderEnum.BOTH, 100F, 65.5F),
                CoverageIngestionRow("HepB", "AFG", ActivityType.ROUTINE, true, 2023, 1, 10, GenderEnum.BOTH, 100F, 65.5F))
        val sut = RepositoriesCoverageLogic(mock(), mock(), mockRepo, mock(), mockExpectationsRepo)
        val expectedMessage = "Duplicate rows detected: 2023, HepB, AFG"

        assertThatThrownBy {
            sut.saveCoverageForTouchstone("t1", testSequence)
        }.isInstanceOf(BadRequest::class.java)
                .hasMessageContaining(expectedMessage)
    }

}
