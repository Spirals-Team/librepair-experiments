package com.codesquad.library.dtos.model.book

import com.codesquad.library.domain.BookSearchCriteria
import com.fasterxml.jackson.annotation.JsonProperty

data class SearchInfoDocument(
        @field:JsonProperty("bookCount")
        val count: Long = 0,

        @field:JsonProperty("criteria")
        val criteria: List<BookSearchCriteria>? = null
)