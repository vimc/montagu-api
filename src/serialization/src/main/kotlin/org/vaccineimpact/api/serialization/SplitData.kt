package org.vaccineimpact.api.serialization

import org.vaccineimpact.api.models.helpers.ContentTypes
import java.io.OutputStream

data class SplitData<out Metadata, out DataRow : Any>(
        val structuredMetadata: Metadata,
        val tableData: StreamSerializable<DataRow>,
        val serializer: Serializer = MontaguSerializer.instance
) : StreamSerializable<DataRow>
{
    override val contentType = ContentTypes.json

    override val data = tableData.data

    override fun serialize(stream: OutputStream)
    {
        val metadata = serializer.toResult(structuredMetadata)
        stream.writer().let {
            it.appendln(metadata)
            it.appendln("---")
            // We want to flush this writer, but we don't want to close the underlying stream, as there
            // be more to write to it
            it.flush()
        }
        tableData.serialize(stream)
    }
}
