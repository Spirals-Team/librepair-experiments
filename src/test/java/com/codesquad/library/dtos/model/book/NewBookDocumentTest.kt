package com.codesquad.library.dtos.model.book

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NewBookDocumentTest {

    var document: NewBookDocument? = null

    @Before
    fun setUp() {
        this.document = NewBookDocument("this is title", "this is author", "hi")
    }

    @Test
    fun JSON_ANNOTATION_TEST() {
        println(ObjectMapper().writeValueAsString(this.document))
    }
}