package com.codesquad.library.dtos.model.book

data class BookSearchDocument(
        val lastBuildDate: String = "",
        val total: Int = 0,
        val start: Int = 0,
        val display: Int = 0,
        val items: List<BookDetailsDocument> = emptyList()
)