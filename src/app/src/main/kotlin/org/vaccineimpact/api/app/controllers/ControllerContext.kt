package org.vaccineimpact.api.app.controllers

import org.vaccineimpact.api.security.WebTokenHelper

open class ControllerContext(
        open val urlBase: String,
        open val tokenHelper: WebTokenHelper
)