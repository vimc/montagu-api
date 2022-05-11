package org.vaccineimpact.api.db

import org.jooq.Converter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class InstantConverter : Converter<LocalDateTime, Instant>
{
    override fun from(t: LocalDateTime?): Instant?
    {
        return t?.toInstant(ZoneOffset.UTC)
    }

    override fun to(u: Instant?): LocalDateTime?
    {
        return if (u == null) null else LocalDateTime.ofInstant(u, ZoneOffset.UTC)
    }

    override fun fromType(): Class<LocalDateTime>
    {
        return LocalDateTime::class.java
    }

    override fun toType(): Class<Instant>
    {
        return Instant::class.java
    }
}
