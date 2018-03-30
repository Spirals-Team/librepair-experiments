package com.codesquad.library.dtos.exceptions

import org.junit.Assert.*
import org.junit.Test
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException

class ExceptionsDocumentTest {

    var exceptionsDocument: ExceptionsDocument

    init {
        this.exceptionsDocument = ExceptionsDocument(AccessDeniedException("fuck you!"))
    }

    @Test
    fun EXCEPTIONSDTO_FROM_ACCESS_EXCEPTION() {
        assertEquals(HttpStatus.FORBIDDEN, this.exceptionsDocument.httpStatus)
        assertEquals("fuck you!", this.exceptionsDocument.message)
    }

}