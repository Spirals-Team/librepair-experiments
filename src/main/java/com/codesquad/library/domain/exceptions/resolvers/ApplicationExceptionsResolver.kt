package com.codesquad.library.domain.exceptions.resolvers

import com.codesquad.library.domain.exceptions.ApplicationException
import com.codesquad.library.dtos.exceptions.ExceptionsDocument
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.servlet.ModelAndView
import java.lang.Exception
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ApplicationExceptionsResolver: HandlerExceptionResolver {

    private val logger: Logger = LoggerFactory.getLogger(ApplicationExceptionsResolver::class.java)

    override fun resolveException(req: HttpServletRequest, res: HttpServletResponse, obj: Any, e: Exception): ModelAndView? {
        if (e is ApplicationException) {
            var appException: ApplicationException = e
            logger.debug("Base Security Exception Caught!")

            var document: ExceptionsDocument = appException.generateDocument()
            sendResponse(res, document)

            return ModelAndView()
        }
        return null
    }

    private fun sendResponse(res: HttpServletResponse, doc: ExceptionsDocument) {
        res.status = doc.httpStatus.value()
        res.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        res.writer.write(ObjectMapper().writeValueAsString(doc))
    }
}