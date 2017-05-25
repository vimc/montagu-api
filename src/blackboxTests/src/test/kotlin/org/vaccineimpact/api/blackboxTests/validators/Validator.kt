package org.vaccineimpact.api.blackboxTests.validators

interface Validator
{
    fun validateError(response: String,
                      expectedErrorCode: String? = null,
                      expectedErrorText: String? = null,
                      assertionText: String? = null)

    fun validateSuccess(response: String, assertionText: String? = null)
}