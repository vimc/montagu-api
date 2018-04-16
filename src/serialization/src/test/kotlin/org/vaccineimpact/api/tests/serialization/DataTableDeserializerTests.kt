package org.vaccineimpact.api.tests.serialization

import org.assertj.core.api.Assertions
import org.junit.Test
import org.vaccineimpact.api.models.ModelRun
import org.vaccineimpact.api.models.helpers.FlexibleColumns
import org.vaccineimpact.api.serialization.DataTableDeserializer
import org.vaccineimpact.api.serialization.MontaguSerializer
import org.vaccineimpact.api.serialization.validation.ValidationException
import org.vaccineimpact.api.test_helpers.MontaguTests
import java.math.BigDecimal

class DataTableDeserializerTests : MontaguTests()
{
    data class ABC(val a: String, val b: String, val c: String)
    data class MixedTypes(val text: String?, val int: Int?, val dec: BigDecimal?)
    data class SomeRequiredColumns(val text: String?, val int: Int)
    @FlexibleColumns
    data class Flexible(val a: Int, val b: String, val extra: Map<String, Int>)

    @FlexibleColumns
    data class FlexibleWithStrings(val a: Int, val b: String, val extra: Map<String, String>)

    @Test
    fun `can deserialize CSV`()
    {
        val csv = """
            text,int,dec
            "joe",1,6.53
            "bob",2,2.0"""
        val rows = DataTableDeserializer.deserialize(csv, MixedTypes::class, MontaguSerializer.instance).toList()
        Assertions.assertThat(rows).containsExactlyElementsOf(listOf(
                MixedTypes("joe", 1, BigDecimal.valueOf(6.53)),
                MixedTypes("bob", 2, BigDecimal.valueOf(2.0))
        ))
    }

    @Test
    fun `empty CSV data causes an exception`()
    {
        val csv = ""
        Assertions.assertThatThrownBy {
            DataTableDeserializer.deserialize(csv, MixedTypes::class, MontaguSerializer.instance).toList()
        }.matches {
            val error = (it as ValidationException).errors.single()
            error.code == "csv-empty"
        }
    }

    @Test
    fun `csv headers are case insensitive`()
    {
        val csv = """
            Text,Int,dec
            "joe",1,6.53
            "bob",2,2.0"""
        val rows = DataTableDeserializer.deserialize(csv, MixedTypes::class, MontaguSerializer.instance).toList()
        Assertions.assertThat(rows).containsExactlyElementsOf(listOf(
                MixedTypes("joe", 1, BigDecimal.valueOf(6.53)),
                MixedTypes("bob", 2, BigDecimal.valueOf(2.0))
        ))
    }

    @Test
    fun `error if headers do not match expected`()
    {
        val csv = """
            a,b,c,d
            1,2,3,4"""
        checkValidationError("csv-unexpected-header") {
            DataTableDeserializer.deserialize(csv, ABC::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `error if row has too many columns`()
    {
        val csv = """
            a,b,c
            1,2,3
            1,2,3,4"""
        checkValidationError("csv-wrong-row-length:2") {
            DataTableDeserializer.deserialize(csv, ABC::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `error if row has too few columns`()
    {
        val csv = """
            a,b,c
            1,2,3
            1,2"""
        checkValidationError("csv-wrong-row-length:2") {
            DataTableDeserializer.deserialize(csv, ABC::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `error if row value in wrong data type`()
    {
        val csv = """
            text,int,dec
            "joe",1,3.14
            "sam",2.6,1"""
        checkValidationError("csv-bad-data-type:2:int", "Unable to parse '2.6' as Int? (Row 2, column int)") {
            DataTableDeserializer.deserialize(csv, MixedTypes::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `error if NA value for non-nullable type`()
    {
        val csv = """
            text,int
            "x",5
            "y",NA"""
        checkValidationError("csv-bad-data-type:2:int", "Unable to parse 'NA' as Int (Row 2, column int)") {
            DataTableDeserializer.deserialize(csv, SomeRequiredColumns::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `can deserialize CSV with flexible headers`()
    {
        val csv = """
            a,b,x,y,z
            1,"joe",1,2,3
            2,"bob",4,5,6"""
        val rows = DataTableDeserializer.deserialize(csv, Flexible::class, MontaguSerializer.instance).toList()
        Assertions.assertThat(rows).containsExactlyElementsOf(listOf(
                Flexible(1, "joe", mapOf("x" to 1, "y" to 2, "z" to 3)),
                Flexible(2, "bob", mapOf("x" to 4, "y" to 5, "z" to 6))
        ))
    }

    @Test
    fun `error if flexible CSV has missing required headers`()
    {
        val csv = """
            a,x,y
            1,2,3
            3,4,5"""
        checkValidationError("csv-unexpected-header") {
            DataTableDeserializer.deserialize(csv, Flexible::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `error if row in flexible CSV has too few values`()
    {
        val csv = """
            a,  b,x,y,z
            0,"p",1,2,3
            0,"q",1,2"""
        checkValidationError("csv-wrong-row-length:2") {
            DataTableDeserializer.deserialize(csv, Flexible::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `error if row in flexible CSV has too many values`()
    {
        val csv = """
            a,  b,x,y,z
            0,"p",1,2,3
            0,"q",1,2,3,4"""
        checkValidationError("csv-wrong-row-length:2") {
            DataTableDeserializer.deserialize(csv, Flexible::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `error if extra value in flexible CSV has wrong data type`()
    {
        val csv = """
            a,  b,x,y,z
            0,"p",1,2,3
            0,"q",1,2,3.5"""
        checkValidationError("csv-bad-data-type:2:z") {
            DataTableDeserializer.deserialize(csv, Flexible::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `error if empty values when all columns required`()
    {
        val csv = """
            run_id,p1,p2,p3
            1,14,15.2,4
            2,14,15.3,"""
        checkValidationError("csv-missing-data:2:p3") {
            DataTableDeserializer.deserialize(csv, ModelRun::class, MontaguSerializer.instance).toList()
        }
    }

    @Test
    fun `no error if empty values when not all columns required`()
    {
        val csv = """
            a,b,x,y
            1,14,15.2,4
            2,14,15.3,"""

        val result =
                DataTableDeserializer.deserialize(csv, FlexibleWithStrings::class, MontaguSerializer.instance).toList()

        Assertions.assertThat(result.last().extra["y"]).isEmpty()
    }

    private fun checkValidationError(code: String, message: String? = null, body: () -> Any?)
    {
        Assertions.assertThatThrownBy { body() }
                .isInstanceOf(ValidationException::class.java)
                .matches {
                    val error = it as ValidationException
                    Assertions.assertThat(error.errors.first().code).isEqualTo(code)
                    if (message != null)
                    {
                        Assertions.assertThat(error.errors.first().message).isEqualTo(message)
                    }
                    true
                }
    }
}