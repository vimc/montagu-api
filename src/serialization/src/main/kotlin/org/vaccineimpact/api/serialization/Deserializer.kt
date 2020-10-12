package org.vaccineimpact.api.serialization

import org.vaccineimpact.api.models.ActivityType
import org.vaccineimpact.api.models.GAVISupportLevel
import java.lang.UnsupportedOperationException
import kotlin.reflect.KType
import kotlin.reflect.full.createType
import kotlin.reflect.full.withNullability

class Deserializer
{
    inline fun <reified T : Enum<T>> parseEnum(name: String): T
    {
        return enumValues<T>()
                .firstOrNull { name.replace('-', '_').equals(it.name, ignoreCase = true) }
                ?: throw UnknownEnumValue(name, T::class.simpleName ?: "[unknown]")
    }

    fun deserialize(raw: String, targetType: KType): Any? = if (targetType.isMarkedNullable)
    {
        if (raw == "NA")
        {
            null
        }
        else
        {
            deserialize(raw, targetType.withNullability(false))
        }
    }
    else
    {
        when (targetType)
        {
            String::class.createType() -> raw
            Int::class.createType() -> raw.toInt()
            Short::class.createType() -> raw.toShort()
            Float::class.createType() -> raw.toFloat()
            ActivityType::class.createType() -> ActivityType.valueOf(raw.toUpperCase())
            GAVISupportLevel::class.createType() -> GAVISupportLevel.valueOf(raw.toUpperCase())
            else -> throw UnsupportedOperationException("org.vaccineimpact.api.serialization.Deserializer does not support target type $targetType")
        }
    }
}