package org.vaccineimpact.api.tests.serialization

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.json
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.models.responsibilities.ResponsibilitySetStatus
import org.vaccineimpact.api.models.responsibilities.ResponsibilityStatus
import org.vaccineimpact.api.serialization.MontaguSerializer
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.math.BigDecimal
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId

class MontaguSerializerTests : MontaguTests()
{
    private val serializer = MontaguSerializer.instance

    @Test
    fun `convertFieldName handles empty string`()
    {
        assertThat(serializer.convertFieldName("")).isEqualTo("")
    }

    @Test
    fun `convertFieldName handles lowercase field name`()
    {
        assertThat(serializer.convertFieldName("field")).isEqualTo("field")
    }

    @Test
    fun `convertFieldName handles camelCase field name`()
    {
        assertThat(serializer.convertFieldName("camelCase")).isEqualTo("camel_case")
    }

    enum class TestEnum { SIMPLE, COMPLEX_VALUE }

    @Test
    fun `serializeEnum serializes any enum correctly`()
    {
        assertThat(serializer.serializeEnum(TestEnum.SIMPLE)).isEqualTo("simple")
        assertThat(serializer.serializeEnum(TestEnum.COMPLEX_VALUE)).isEqualTo("complex-value")
    }

    @Test
    fun `can serialize LocalDate`()
    {
        val actual = LocalDate.of(1988, Month.MARCH, 19)
        val expected = "\"1988-03-19\""
        checkSerializedForm(expected, actual)
    }

    @Test
    fun `can serialize Instant`()
    {
        val actual = LocalDate.of(1, Month.DECEMBER, 25)
                .atStartOfDay(ZoneId.of("UTC"))
                .toInstant()
        val expected = "\"0001-12-25T00:00:00Z\""
        checkSerializedForm(expected, actual)
    }

    @Test
    fun `can serialize IntRange`()
    {
        val actual = 1..6
        val expected = json {
            obj(
                    "minimum_inclusive" to 1,
                    "maximum_inclusive" to 6
            )
        }
        checkSerializedForm(expected, actual)
    }

    @Test
    fun `can serialize ResultStatus`()
    {
        val actual = ResultStatus.SUCCESS
        val expected = "\"success\""
        checkSerializedForm(expected, actual)
    }

    @Test
    fun `can serialize ResponsibilitySetStatus`()
    {
        val actual = ResponsibilitySetStatus.APPROVED
        val expected = "\"approved\""
        checkSerializedForm(expected, actual)
    }

    @Test
    fun `can serialize ResponsibilityStatus`()
    {
        val actual = ResponsibilityStatus.INVALID
        val expected = "\"invalid\""
        checkSerializedForm(expected, actual)
    }

    @Test
    fun `can serialize TouchstoneStatus`() {
        val actual = TouchstoneStatus.IN_PREPARATION
        val expected = "\"in-preparation\""
        checkSerializedForm(expected, actual)
    }

    @Test
    fun `can serialize GAVISupportLevel`()
    {
        checkSerializedForm("\"no vaccine\"", GAVISupportLevel.NONE)
        checkSerializedForm("\"no gavi\"", GAVISupportLevel.WITHOUT)
        checkSerializedForm("\"total\"", GAVISupportLevel.WITH)

        checkSerializedForm("\"high\"", GAVISupportLevel.HIGH)
        checkSerializedForm("\"low\"", GAVISupportLevel.LOW)
        checkSerializedForm("\"bestcase\"", GAVISupportLevel.BESTCASE)
    }

    @Test
    fun `can serialize ActivityType`()
    {
        val actual = ActivityType.CAMPAIGN
        val expected = "\"campaign\""
        checkSerializedForm(expected, actual)
    }

    @Test
    fun `toResult wraps object in successful Result`()
    {
        val data = 31415
        val actual = serializer.toResult(data)
        val expected = json {
            obj(
                    "data" to 31415,
                    "errors" to array(),
                    "status" to "success"
            )
        }
        assertThat(parse(actual)).isEqualTo(expected)
    }

    @Test
    fun `toJson serializes Result correctly`()
    {
        val result = Result(
                ResultStatus.FAILURE,
                31415,
                listOf(ErrorInfo("code", "message"))
        )
        val actual = serializer.toJson(result)
        val expected = json {
            obj(
                    "data" to 31415,
                    "errors" to array(obj("code" to "code", "message" to "message")),
                    "status" to "failure"
            )
        }
        assertThat(parse(actual)).isEqualTo(expected)
    }

    @Test
    fun `serializes decimal for CSV with correct decimal places`()
    {
        assertThat(serializer.serializeValueForCSV(BigDecimal(123))).isEqualTo("123")
        assertThat(serializer.serializeValueForCSV(BigDecimal(123.4))).isEqualTo("123.4")
        assertThat(serializer.serializeValueForCSV(BigDecimal(123.45))).isEqualTo("123.45")
        assertThat(serializer.serializeValueForCSV(BigDecimal(123.456))).isEqualTo("123.46")
        assertThat(serializer.serializeValueForCSV(BigDecimal(123.5000000))).isEqualTo("123.5")
    }

    @Test
    fun `serializes decimal for CSV with no grouping`()
    {
        assertThat(serializer.serializeValueForCSV((BigDecimal(123456.78)))).isEqualTo("123456.78")
    }

    fun checkSerializedForm(expected: JsonObject, actual: Any): Unit
    {
        val actualInKlaxon = parse(serializer.gson.toJson(actual))
        assertThat(actualInKlaxon).isEqualTo(expected)
    }

    fun checkSerializedForm(expected: String, actual: Any): Unit
    {
        val actualAsString = serializer.gson.toJson(actual)
        assertThat(actualAsString).isEqualTo(expected)
    }

    fun parse(string: String): JsonObject = Parser().parse(StringBuilder(string)) as JsonObject
}