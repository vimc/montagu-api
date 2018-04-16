package org.vaccineimpact.api.tests.serialization

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Test
import org.vaccineimpact.api.models.TouchstoneStatus
import org.vaccineimpact.api.models.helpers.FlexibleColumns
import org.vaccineimpact.api.serialization.DataTable
import org.vaccineimpact.api.serialization.MontaguSerializer
import org.vaccineimpact.api.serialization.validation.ValidationException
import org.vaccineimpact.api.test_helpers.MontaguTests
import org.vaccineimpact.api.test_helpers.serializeToStreamAndGetAsString
import java.math.BigDecimal

class DataTableSerializerTests : MontaguTests()
{
    data class ABC(val a: String, val b: String, val c: String)
    data class MixedTypes(val text: String?, val int: Int?, val dec: BigDecimal?)
    data class WithEnums(val text: String, val enum: TouchstoneStatus)

    @Test
    fun `headers are written in order of constructor`()
    {
        val table = DataTable.new<ABC>(emptySequence())
        assertThat(serialize(table)).isEqualTo("""a,b,c""")
    }

    @Test
    fun `data is written out line by line`()
    {
        val table = DataTable.new(sequenceOf(
                ABC("g", "h", "i"),
                ABC("x", "y", "z")
        ))
        assertThat(serialize(table)).isEqualTo("""a,b,c
g,h,i
x,y,z""")
    }

    @Test
    fun `special characters are escaped`()
    {
        val table = DataTable.new(sequenceOf(
                ABC("g", "h", "i"),
                ABC("x", "y", "z"),
                ABC("with, commas", """with "quotes" and no commas""", """both "quotes" and ,commas,""")
        ))
        assertThat(serialize(table)).isEqualTo("""a,b,c
g,h,i
x,y,z
"with, commas","with ""quotes"" and no commas","both ""quotes"" and ,commas,"""")
    }

    @Test
    fun `mixed types are written out`()
    {
        val table = DataTable.new(sequenceOf(
                MixedTypes("text", 123, BigDecimal("3.1415"))
        ))
        assertThat(serialize(table)).isEqualTo("""text,int,dec
text,123,3.1415""")
    }

    @Test
    fun `null is converted to NA`()
    {
        val table = DataTable.new(sequenceOf(
                MixedTypes(null, null, null)
        ))
        assertThat(serialize(table)).isEqualTo("""text,int,dec
<NA>,<NA>,<NA>""")
    }

    @Test
    fun `enum is converted to lowercase with hyphens`()
    {
        val table = DataTable.new(sequenceOf(
                WithEnums("free text", TouchstoneStatus.IN_PREPARATION)
        ))
        assertThat(serialize(table)).isEqualTo("""text,enum
free text,in-preparation""")
    }

    private fun serialize(table: DataTable<*>) = serializeToStreamAndGetAsString {
        table.serialize(it, MontaguSerializer.instance)
    }.trim()
}