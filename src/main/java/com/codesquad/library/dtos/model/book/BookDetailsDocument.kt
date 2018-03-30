package com.codesquad.library.dtos.model.book

import com.codesquad.library.domain.BookStatus

data class BookDetailsDocument(
        val title: String = "",
        val link: String = "",
        val image: String = "",
        val author: String = "",
        val price: Int = 0,
        val discount: Int = 0,
        val publisher: String = "",
        val pubdate: Int = 0,
        val isbn: String = "",
        val description: String = ""
)