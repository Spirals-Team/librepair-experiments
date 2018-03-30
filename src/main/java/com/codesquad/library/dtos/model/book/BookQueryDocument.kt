package com.codesquad.library.dtos.model.book

import com.codesquad.library.domain.BookSearchCriteria

data class BookQueryDocument(
        val criteria: String = "",
        val query: String = ""
) {
    fun getSearchCriteria() : BookSearchCriteria {
        return BookSearchCriteria.findByLabel(this.criteria)
    }
}