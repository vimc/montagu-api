package org.vaccineimpact.api.security

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JOSEObjectType
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.RSASSASigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.pac4j.core.exception.TechnicalException
import org.pac4j.core.util.CommonHelper
import org.pac4j.jwt.config.signature.RSASignatureConfiguration
import java.security.KeyPair

class MontaguRSASignatureConfiguration(keyPair: KeyPair) : RSASignatureConfiguration(keyPair)
{
    override fun sign(claims: JWTClaimsSet): SignedJWT
    {
        init()
        CommonHelper.assertNotNull("privateKey", privateKey)

        try
        {
            val signer = RSASSASigner(this.privateKey)
            val builder = JWSHeader.Builder(algorithm)
                    .type(JOSEObjectType.JWT)
            val header = builder.build()
            val signedJWT = SignedJWT(header, claims)
            signedJWT.sign(signer)
            return signedJWT
        }
        catch (e: JOSEException)
        {
            throw TechnicalException(e)
        }

    }

}