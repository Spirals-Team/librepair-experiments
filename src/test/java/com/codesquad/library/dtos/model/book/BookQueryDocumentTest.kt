package com.codesquad.library.dtos.model.book

import com.codesquad.library.domain.Book
import com.codesquad.library.domain.BookSearchCriteria
import com.codesquad.library.domain.repositories.BookRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import kotlin.test.assertTrue

@SpringBootTest
@RunWith(SpringRunner::class)
class BookQueryDocumentTest {

    private val log: Logger = LoggerFactory.getLogger(BookQueryDocument::class.java)

    var queryDoc: BookQueryDocument = BookQueryDocument("title", "이말년씨리즈")

    @Autowired
    lateinit var repo: BookRepository


    @Before
    fun setUp() {
        repo.save(Book.builder().title("이말년씨리즈").build())
    }

    @Test
    fun test() {
        assertEquals(repo.findByQuery(this.queryDoc).size, 1)
    }

    @Test
    fun JSON_CREATION() {
        log.debug(ObjectMapper().writeValueAsString(this.queryDoc))
    }
}