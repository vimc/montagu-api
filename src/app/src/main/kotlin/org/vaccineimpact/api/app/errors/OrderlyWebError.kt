package org.vaccineimpact.api.app.errors

import org.vaccineimpact.api.models.ErrorInfo

class OrderlyWebError(message: String) : MontaguError(500, listOf(
        ErrorInfo("orderly-web-error", message)
))