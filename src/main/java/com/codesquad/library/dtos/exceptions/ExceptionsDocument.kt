package com.codesquad.library.dtos.exceptions

import com.codesquad.library.domain.exceptions.ErrorCodes
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException

data class ExceptionsDocument(
        @field:JsonProperty("ERROR_CODE")
        val errorCode: Int = 0,

        @field:JsonProperty("ERROR_MESSAGE")
        val message: String = "",

        @field:JsonIgnore
        val httpStatus: HttpStatus = HttpStatus.NOT_FOUND
) {
        constructor(accessException: AccessDeniedException): this(ErrorCodes.SECURITY_FORBIDDEN_ERROR.code, accessException.message!!, HttpStatus.FORBIDDEN)
}