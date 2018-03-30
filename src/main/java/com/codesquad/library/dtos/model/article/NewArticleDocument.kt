package com.codesquad.library.dtos.model.article

data class NewArticleDocument(
        val title: String = "",
        val content: String = "",
        val urgent: Boolean = false)
