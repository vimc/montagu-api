package org.vaccineimpact.api.serialization

import com.github.salomonbrys.kotson.jsonSerializer
import com.github.salomonbrys.kotson.registerTypeAdapter
import com.google.gson.*
import org.vaccineimpact.api.models.*
import org.vaccineimpact.api.models.responsibilities.ResponsibilitySetStatus
import org.vaccineimpact.api.models.responsibilities.ResponsibilityStatus
import java.math.BigDecimal
import java.text.DecimalFormat
import java.time.Instant
import java.time.LocalDate

interface Serializer
{
    fun toResult(data: Any?): String
    fun toJson(result: Result): String
    fun <T> fromJson(json: String, klass: Class<T>): T
    fun convertFieldName(name: String): String

    fun serializeEnum(value: Any): String

    fun serializeValueForCSV(value: Any?): String

    val gson: Gson

    val serializeNullsTo: String
}

class NullToEmptyStringSerializer: MontaguSerializer() {

    override val serializeNullsTo = ""

    companion object
    {
        val instance = NullToEmptyStringSerializer()
    }
}

open class MontaguSerializer : Serializer
{
    private val toDateStringSerializer = jsonSerializer<Any> {
        JsonPrimitive(it.src.toString())
    }
    private val intRangeSerializer = jsonSerializer<IntRange> { range ->
        JsonObject().apply {
            addProperty("minimum_inclusive", range.src.start)
            addProperty("maximum_inclusive", range.src.endInclusive)
        }
    }

    companion object
    {
        val instance: Serializer = MontaguSerializer()
    }

    override final val gson: Gson

    init
    {
        val common = GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .setFieldNamingStrategy { convertFieldName(it.name) }
                .serializeNulls()

                .registerTypeAdapter<Instant>(toDateStringSerializer)
                .registerTypeAdapter<LocalDate>(toDateStringSerializer)
                .registerTypeAdapter(intRangeSerializer)

                .registerEnum<ActivityType>()
                .registerEnum<BurdenEstimateSetStatus>()
                .registerEnum<BurdenEstimateSetTypeCode>()
                .registerEnum<GAVISupportLevel>()
                .registerEnum<ResponsibilitySetStatus>()
                .registerEnum<ResponsibilityStatus>()
                .registerEnum<ResultStatus>()
                .registerEnum<TouchstoneStatus>()

        // Some serializers for complex objects need to recurse back to the default
        // serialization strategy. So we separate out a Gson object that has all the
        // primitive serializers, and then create one that extends it with the complex
        // serializers.
        val baseGson = common.create()
        gson = common
                .registerTypeAdapter<User>(ruleBasedSerializer(baseGson))
                .registerTypeAdapter<BurdenEstimateSet>(ruleBasedSerializer(baseGson))
                .registerTypeAdapter<ScenarioTouchstoneAndCoverageSets>(ruleBasedSerializer(baseGson))
                .registerTypeAdapter<ScenarioAndCoverageSets>(ruleBasedSerializer(baseGson))
                .registerTypeAdapter<BurdenEstimateDataSeries>(BurdenEstimateDataSeriesTypeAdaptor())
                .create()
    }

    override fun toResult(data: Any?): String = toJson(Result(ResultStatus.SUCCESS, data, emptyList()))
    override fun toJson(result: Result): String = gson.toJson(result)
    override fun <T> fromJson(json: String, klass: Class<T>): T = gson.fromJson(json, klass)

    override fun convertFieldName(name: String): String
    {
        val builder = StringBuilder()

        for (char in name)
        {
            if (char.isUpperCase())
            {
                builder.append("_" + char.lowercaseChar())
            }
            else
            {
                builder.append(char)
            }
        }
        return builder.toString().trim('_')
    }

    override fun serializeEnum(value: Any): String
    {
        val text = when (value)
        {
            is GAVISupportLevel -> mapGAVISupportLevel(value)
            else -> value.toString()
        }
        return text.lowercase().replace('_', '-')
    }

    override val serializeNullsTo = "<NA>"

    override fun serializeValueForCSV(value: Any?) = when (value)
    {
        null -> serializeNullsTo
        is Enum<*> -> serializeEnum(value)
        is BigDecimal -> value.stripTrailingZeros().toPlainString()
        else -> value.toString()
    }

    private fun mapGAVISupportLevel(value: GAVISupportLevel): String
    {
        return when (value)
        {
            GAVISupportLevel.NONE -> "no vaccine"
            GAVISupportLevel.WITHOUT -> "no gavi"
            GAVISupportLevel.WITH -> "total"
            GAVISupportLevel.HIGH -> "high"
            GAVISupportLevel.LOW -> "low"
            GAVISupportLevel.BESTCASE -> "bestcase"

            GAVISupportLevel.STATUS_QUO -> "status quo"
            GAVISupportLevel.CONTINUE -> "continue"
            GAVISupportLevel.GAVI_OPTIMISTIC -> "gavi optimistic"
            GAVISupportLevel.INTENSIFIED -> "intensified"
        // Legacy values
            GAVISupportLevel.BESTMINUS -> "best minus"
            GAVISupportLevel.HOLD2010 -> "hold 2010"
        }
    }

    private inline fun <reified T : Enum<T>> GsonBuilder.registerEnum(): GsonBuilder
    {
        return this.registerTypeAdapter<T> {
            serialize { JsonPrimitive(serializeEnum(it.src)) }
            deserialize { Deserializer().parseEnum<T>(it.json.asString) }
        }
    }
}