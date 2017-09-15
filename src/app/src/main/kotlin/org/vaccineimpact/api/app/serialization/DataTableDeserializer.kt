package org.vaccineimpact.api.app.serialization

import com.opencsv.CSVReader
import org.vaccineimpact.api.Deserializer
import org.vaccineimpact.api.app.errors.ValidationError
import org.vaccineimpact.api.models.ErrorInfo
import org.vaccineimpact.api.models.helpers.FlexibleColumns
import java.io.StringReader
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.primaryConstructor

class HeaderDefinition(val name: String, val type: KType)

open class DataTableDeserializer<out T>(
        protected val headers: List<HeaderDefinition>,
        private val constructor: KFunction<T>
)
{
    private val headerCount = headers.size
    protected open val extraHeadersAllowed = false

    fun deserialize(body: String): Sequence<T>
    {
        val reader = CSVReader(StringReader(body.trim()))

        var row = reader.readNext()
        val headers = row.toList()
        checkHeaders(headers)
        val actualHeaders = getActualHeaderDefinitions(headers)
        val rows = generateSequence { reader.readNext() }
        return rows.withIndex().map { (i, row) ->
            deserializeRow(row.toList(), actualHeaders, i)
        }
    }

    private fun deserializeRow(
            row: List<String>,
            actualHeaders: List<HeaderDefinition>,
            rowIndex: Int
    ): T
    {
        val problems = mutableListOf<ErrorInfo>()
        if (row.size != actualHeaders.size)
        {
            problems.add(ErrorInfo("csv-wrong-row-length:$rowIndex", "Row $rowIndex has a different number of columns from the header row"))
        }
        val values = row.zip(actualHeaders).map { (raw, header) ->
            deserialize(raw, header.type, rowIndex, header.name, problems)
        }
        if (problems.any())
        {
            throw ValidationError(problems)
        }
        return constructor.call(*prepareValuesForConstructor(values, actualHeaders).toTypedArray())
    }

    protected open fun prepareValuesForConstructor(values: List<Any?>, actualHeaders: List<HeaderDefinition>): List<Any?>
    {
        return values
    }

    private fun deserialize(raw: String, targetType: KType,
                            row: Int, column: String,
                            problems: MutableList<ErrorInfo>): Any?
    {
        return try
        {
            val value = Deserializer().deserialize(raw.trim(), targetType)
            value
        }
        catch (e: Exception)
        {
            problems.add(ErrorInfo(
                    "csv-bad-data-type:$row:$column",
                    "Unable to parse '${raw.trim()}' as ${targetType.javaClass.simpleName} (Row $row, column $column)"
            ))
            null
        }
    }

    private fun checkHeaders(actualHeaders: List<String>): List<HeaderDefinition>
    {
        val problems = mutableListOf<ErrorInfo>()
        var index = 0
        while (index < maxOf(headerCount, actualHeaders.size))
        {
            val expected = headers.getOrNull(index)
            val actual = actualHeaders.getOrNull(index)?.trim()

            if (expected != null)
            {
                val expectedName = expected.name
                if (actual != null)
                {
                    if (expectedName != actual)
                    {
                        problems.add(ErrorInfo("csv-unexpected-header", "Expected column header '$expectedName'; found '$actual' instead (column $index)"))
                    }
                }
                else
                {
                    problems.add(ErrorInfo("csv-missing-header", "Not enough column headers were provided. Expected a '$expectedName' header."))
                }
            }
            else if (actual != null && !extraHeadersAllowed)
            {
                problems.add(ErrorInfo("csv-unexpected-header", "Too many column headers were provided. Unexpected '$actual' header."))
            }
            index += 1
        }

        if (problems.any())
        {
            throw ValidationError(problems)
        }
        return headers
    }

    protected open fun getActualHeaderDefinitions(actualHeaders: List<String>): List<HeaderDefinition>
    {
        return headers
    }

    companion object
    {
        fun <T : Any> deserialize(
                body: String,
                type: KClass<T>,
                serializer: Serializer = Serializer.instance
        ): Sequence<T>
        {
            return getDeserializer(type, serializer).deserialize(body)
        }

        private fun <T: Any> getDeserializer(type: KClass<T>, serializer: Serializer): DataTableDeserializer<T>
        {
            val constructor = type.primaryConstructor
                    ?: throw Exception("Cannot deserialize to type ${type.simpleName} - it has no primary constructor")
            val headers = constructor.parameters
                    .map { HeaderDefinition(serializer.convertFieldName(it.name!!), it.type) }

            return if (type.findAnnotation<FlexibleColumns>() != null)
            {
                // If the last argument is a map, get the type of the values the map stores
                val flexibleType = constructor.parameters.last().type.arguments.last().type!!
                FlexibleDataTableDeserializer(headers.dropLast(1), constructor, flexibleType)
            }
            else
            {
                DataTableDeserializer(headers, constructor)
            }
        }
    }
}