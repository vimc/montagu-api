package org.vaccineimpact.api.emails

import org.vaccineimpact.api.db.Config
import org.vaccineimpact.api.models.Compressed
import org.vaccineimpact.api.security.BasicUserProperties

class NewUserEmail(
        val user: BasicUserProperties,
        val token: Compressed
) : MustacheEmail()
{
    override val subject = "Welcome to Montagu"

    override val textTemplate = "new-user.txt"
    override val htmlTemplate = "new-user.html"
    override val values = mapOf(
            "name" to user.name,
            "token" to token.raw,
            "support_address" to Config["contact.support"]
    )
}