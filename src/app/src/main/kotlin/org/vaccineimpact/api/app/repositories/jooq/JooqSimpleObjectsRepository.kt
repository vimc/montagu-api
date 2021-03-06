package org.vaccineimpact.api.app.repositories.jooq

import org.jooq.Configuration
import org.jooq.DSLContext
import org.vaccineimpact.api.app.repositories.SimpleObjectsRepository
import org.vaccineimpact.api.db.JooqContext
import org.vaccineimpact.api.db.Tables.DISEASE
import org.vaccineimpact.api.db.tables.records.DiseaseRecord
import org.vaccineimpact.api.models.Disease


class JooqSimpleObjectsRepository(dsl: DSLContext) : JooqRepository(dsl), SimpleObjectsRepository
{
    override val diseases = JooqSimpleDataSet.new(dsl, DISEASE, { it.ID }, this::mapDisease)

    private fun mapDisease(record: DiseaseRecord): Disease = record.into(Disease::class.java)

}