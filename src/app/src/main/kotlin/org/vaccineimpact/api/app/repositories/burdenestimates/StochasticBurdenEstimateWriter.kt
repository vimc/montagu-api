package org.vaccineimpact.api.app.repositories.burdenestimates

import org.jooq.DSLContext
import org.jooq.TableField
import org.jooq.impl.TableImpl
import org.vaccineimpact.api.db.AnnexJooqContext
import org.vaccineimpact.api.db.CloseableContext
import org.vaccineimpact.api.db.ShortlivedAnnexContext
import org.vaccineimpact.api.db.Tables

open class StochasticBurdenEstimateWriter(
        readDatabaseDSL: DSLContext,
        writeDatabaseDSLSource: CloseableContext = ShortlivedAnnexContext()
) : BurdenEstimateWriter(readDatabaseDSL, writeDatabaseDSLSource)
{
    override val table: TableImpl<*> = Tables.BURDEN_ESTIMATE_STOCHASTIC
    override val fields by lazy {
        val t = Tables.BURDEN_ESTIMATE_STOCHASTIC
        listOf(
                t.BURDEN_ESTIMATE_SET,
                t.MODEL_RUN,
                t.COUNTRY,
                t.YEAR,
                t.AGE,
                t.BURDEN_OUTCOME,
                t.VALUE
        )
    }
}