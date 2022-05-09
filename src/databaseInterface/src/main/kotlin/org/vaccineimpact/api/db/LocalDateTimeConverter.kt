package org.vaccineimpact.api.db

import org.jooq.Converter
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime

class InstantConverter : Converter<Timestamp, Instant>
{
    override fun from(t: Timestamp?): Instant?
    {
        return t?.toInstant()
    }

    override fun to(u: Instant?): Timestamp?
    {
        return if (u == null) null else Timestamp.from(u)
    }

    override fun fromType(): Class<Timestamp>
    {
        return Timestamp::class.java
    }

    override fun toType(): Class<Instant>
    {
        return Instant::class.java
    }
}
