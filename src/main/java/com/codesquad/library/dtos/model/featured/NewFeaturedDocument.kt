package com.codesquad.library.dtos.model.featured

data class NewFeaturedDocument(
        val title: String,
        val booksId: List<Long>,
        val content: String
) {
    constructor() : this(
            "", emptyList(), ""
    )
}