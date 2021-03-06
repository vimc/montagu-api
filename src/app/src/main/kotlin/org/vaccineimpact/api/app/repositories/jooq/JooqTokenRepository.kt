package org.vaccineimpact.api.app.repositories.jooq

import org.jooq.DSLContext
import org.vaccineimpact.api.app.repositories.TokenRepository
import org.vaccineimpact.api.db.Tables.ONETIME_TOKEN
import org.vaccineimpact.api.models.HasKey
import org.vaccineimpact.api.security.inflate

class JooqTokenRepository(dsl: DSLContext) : JooqRepository(dsl), TokenRepository
{
    data class OneTimeToken(override val id: String) : HasKey<String>

    override fun storeToken(uncompressedToken: String)
    {
        dsl.newRecord(ONETIME_TOKEN).apply {
            this.token = uncompressedToken
        }.store()
    }

    override fun validateOneTimeToken(uncompressedToken: String): Boolean
    {
        val deletedCount = dsl.deleteFrom(ONETIME_TOKEN)
                .where(ONETIME_TOKEN.TOKEN.eq(uncompressedToken))
                .execute()
        return deletedCount == 1
    }

}