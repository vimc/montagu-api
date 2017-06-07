package org.vaccineimpact.api.app

class OneTimeLinkActionContext(
        private val payload: Map<String, String>,
        context: ActionContext
) : ActionContext by context
{
    override fun params() = payload

    override fun params(key: String): String
    {
        var keyWithColon = key
        if (!keyWithColon.startsWith(":"))
        {
            keyWithColon = ":" + keyWithColon
        }
        return payload[keyWithColon]!!
    }
}