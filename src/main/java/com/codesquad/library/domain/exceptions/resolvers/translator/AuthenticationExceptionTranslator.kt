package com.codesquad.library.domain.exceptions.resolvers.translator

import com.codesquad.library.domain.exceptions.security.NotAllowedException
import com.codesquad.library.dtos.exceptions.ExceptionsDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
class AuthenticationExceptionTranslator: WebResponseExceptionTranslator {

    private val log: Logger = LoggerFactory.getLogger(AuthenticationExceptionTranslator::class.java)

    override fun translate(e: Exception?): ResponseEntity<OAuth2Exception> {
        if(e is OAuth2Exception) {
            log.debug("there was an authentication error.")
            var oauthException: OAuth2Exception = e as OAuth2Exception
            return generateResponseEntity(NotAllowedException(oauthException))
        }
        throw e!!
    }

    fun generateResponseEntity(e: NotAllowedException): ResponseEntity<OAuth2Exception> {
        var document: ExceptionsDocument = e.generateDocument()

        return ResponseEntity
                .status(document.httpStatus)
                .body(e)
    }
}