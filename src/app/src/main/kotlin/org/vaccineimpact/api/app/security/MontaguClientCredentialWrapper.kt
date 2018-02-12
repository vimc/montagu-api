package org.vaccineimpact.api.app.security

import org.pac4j.core.client.DirectClient
import org.pac4j.core.credentials.TokenCredentials
import org.pac4j.core.profile.CommonProfile
import org.vaccineimpact.api.models.ErrorInfo

interface MontaguSecurityClientWrapper
{
    val client: DirectClient<TokenCredentials, CommonProfile>
    val authorizationError: ErrorInfo
}
