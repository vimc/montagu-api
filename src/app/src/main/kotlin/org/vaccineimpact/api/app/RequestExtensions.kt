package org.vaccineimpact.api.app

import org.vaccineimpact.api.models.Result
import org.vaccineimpact.api.models.ResultStatus
import spark.Request

// Use this to make sure we have finished consuming the request
// before returning any response.
fun Request.consumeRemainder()
{
    val inputStream = this.raw()?.inputStream

    // This null check is just here to make unit testing easier - this way
    // we don't have to mock out .raw in so many tests
    if (inputStream != null)
    {
        val buffer = ByteArray(8096)
        while (inputStream.read(buffer) > 0)
        {
            //keep going
        }
    }
}

fun Any?.asResult() = if (this is Result) this else Result(ResultStatus.SUCCESS, this, emptyList())
