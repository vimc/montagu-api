package org.vaccineimpact.api.app

object HTMLFormHelpers
{
    val requiredContentType = "application/x-www-form-urlencoded"

    fun checkForm(context: ActionContext, expectedContents: Map<String, String>): HTMLForm
    {
        if (!isHTMLForm(context))
        {
            return HTMLForm.InvalidForm("Content-Type must be '$requiredContentType'")
        }
        for ((key, value) in expectedContents)
        {
            if (context.queryParams(key) != value)
            {
                return HTMLForm.InvalidForm("Expected form content include $key=$value")
            }
        }
        return HTMLForm.ValidForm()
    }

    fun isHTMLForm(context: ActionContext) = context.contentType() == requiredContentType
}

sealed class HTMLForm
{
    class ValidForm : HTMLForm()
    class InvalidForm(val problem: String) : HTMLForm()

    override fun equals(other: Any?) = when (other) {
        is ValidForm -> this is ValidForm
        is InvalidForm -> this is InvalidForm && this.problem == other.problem
        else -> false
    }
}