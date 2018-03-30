package com.codesquad.library.security

import com.codesquad.library.dtos.exceptions.ExceptionsDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class DefaultAccessDeniedHandler {

    private val log: Logger = LoggerFactory.getLogger(DefaultAccessDeniedHandler::class.java)

    @ExceptionHandler(value = AccessDeniedException::class)
    fun handleSecurityException(e: AccessDeniedException): ResponseEntity<ExceptionsDocument>? {
        log.debug("access denied exception!")
        log.debug(e.message)

        val document = ExceptionsDocument(e)

        return ResponseEntity
                .status(document.httpStatus)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(document)
    }
}