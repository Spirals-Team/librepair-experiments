package com.codesquad.library.security.filters

import com.codesquad.library.domain.exceptions.security.BaseSecurityException
import com.codesquad.library.dtos.exceptions.ExceptionsDocument
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class AuthenticationExceptionsFilter: OncePerRequestFilter() {

    private val log: Logger = LoggerFactory.getLogger(AuthenticationExceptionsFilter::class.java)

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, filter: FilterChain) {
        try {
            log.debug("Authentication Attempt!")
            filter.doFilter(req, res)
        } catch (e: BaseSecurityException) {
            var doc: ExceptionsDocument = e.generateDocument()

            res.status = doc.httpStatus.value()
            res.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
            res.writer
                    .write(ObjectMapper().writeValueAsString(doc))
        }
    }
}