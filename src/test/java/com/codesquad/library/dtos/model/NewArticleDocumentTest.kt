package com.codesquad.library.dtos.model

import com.codesquad.library.domain.Article
import com.codesquad.library.dtos.model.article.NewArticleDocument
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NewArticleDocumentTest {

    var document: NewArticleDocument? = null

    @Before
    fun setUp() {
        this.document = NewArticleDocument("test article", "hello", false)
    }

    @Test
    fun CREATE_OBJECT_FROM_DTO() {
        var articleObject: Article = Article.getObjectByDto(this.document)

        assertEquals(articleObject.content, "hello")
    }

    @Test
    fun CREATE_JSON() {
        println(ObjectMapper().writeValueAsString(this.document))
    }

}