package org.vaccineimpact.api.db

import org.jooq.Cursor
import org.jooq.Record
import org.jooq.ResultQuery
import java.sql.ResultSet

fun <TRecord : Record> ResultQuery<TRecord>.fetchSequence(): Sequence<TRecord>
{
    val cursor = getCursor()
    return generateSequence { cursor.fetchNext() }
}

inline fun <TRecord : Record, reified TModel : Any> ResultQuery<TRecord>.fetchSequenceInto(): Sequence<TModel>
{
    val cursor = getCursor()
    return generateSequence { cursor.fetchNext().into(TModel::class.java) }
}

fun <TRecord : Record> ResultQuery<TRecord>.getCursor(): Cursor<TRecord> = this.fetchSize(100).fetchLazy()
