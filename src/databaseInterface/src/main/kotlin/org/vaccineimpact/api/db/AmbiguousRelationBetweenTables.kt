package org.vaccineimpact.api.db

import org.jooq.Table

class AmbiguousRelationBetweenTables(from: Table<*>, to: Table<*>) : Exception(
        "Attempted to construct join from ${from.name} to ${to.name}, but there is more than one key relating those tables."
)