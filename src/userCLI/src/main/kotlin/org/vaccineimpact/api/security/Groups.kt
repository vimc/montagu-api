package org.vaccineimpact.api.security

import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.Tables.MODELLING_GROUP

sealed class Groups
{
    abstract fun getGroups(db: JooqContext): Iterable<String>

    data class GroupList(val groups: List<String>) : Groups()
    {
        override fun getGroups(db: JooqContext) = groups
    }

    class AllGroups : Groups()
    {
        override fun getGroups(db: JooqContext): Iterable<String>
        {
            return db.dsl.select(MODELLING_GROUP.ID)
                    .from(MODELLING_GROUP)
                    .where(MODELLING_GROUP.REPLACED_BY.isNull)
                    .fetch()
                    .map { it[MODELLING_GROUP.ID] }
        }

        override fun equals(other: Any?) = other is AllGroups
    }
}