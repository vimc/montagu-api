package org.vaccineimpact.api.app.repositories

import org.jooq.DSLContext
import org.jooq.TableField
import org.jooq.impl.TableImpl
import org.vaccineimpact.api.app.repositories.jooq.BurdenEstimateWriter
import org.vaccineimpact.api.db.Tables
import org.vaccineimpact.api.db.withoutCheckingForeignKeyConstraints
import org.vaccineimpact.api.models.BurdenEstimateWithRunId
import java.io.PipedInputStream
import java.io.PipedOutputStream

class CentralBurdenEstimateWriter(private val dsl: DSLContext,
                                  private val table: TableImpl<*>,
                                  private val fields: List<TableField<*, *>>,
                                  private val burdenEstimateWriter: BurdenEstimateWriter)
{

    fun addEstimatesToSet(setId: Int, estimates: Sequence<BurdenEstimateWithRunId>, expectedDisease: String)
    {
        // The only foreign keys are:
        // * burden_estimate_set, which is the same for every row, and it's the one we just created and know exists
        // * country, which we check below, per row of the CSV (and each row represents multiple rows in the database
        //   so this is an effort saving).
        // * burden_outcome, which we check below (currently we check for every row, but given these are set in the
        //   columns and don't vary by row this could be made more efficient)
        dsl.withoutCheckingForeignKeyConstraints(table) {

            PipedOutputStream().use { stream ->
                // First, let's set up a thread to read from the stream and send
                // it to the database. This will block if the thread is empty, and keep
                // going until it sees the Postgres EOF marker.
                val inputStream = PipedInputStream(stream).buffered()
                val writeToDatabaseThread = burdenEstimateWriter.writeStreamToDatabase(dsl, inputStream, table, fields)

                // In the main thread, write to piped stream, blocking if we get too far ahead of
                // the other thread ("too far ahead" meaning the buffer on the input stream is full)
                burdenEstimateWriter.writeCopyData(stream, estimates, expectedDisease, setId)

                // Wait for the worker thread to finished
                writeToDatabaseThread.join()
            }
        }
    }
}