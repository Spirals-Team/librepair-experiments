package com.codesquad.library.controllers

import com.codesquad.library.domain.service.BookSearchService
import com.codesquad.library.domain.service.BookService
import com.codesquad.library.dtos.model.book.BookDocument
import com.codesquad.library.dtos.model.book.BookQueryDocument
import com.codesquad.library.dtos.model.book.SearchInfoDocument
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/test/")
@RestController
class MockApiV1Controller constructor(private val bookService: BookService, private val bookSearchService: BookSearchService) {

    @PostMapping("/search")
    fun getMockSearchResult(@RequestBody query: BookQueryDocument) : List<BookDocument> {
        return bookSearchService.search(query)
    }

    @GetMapping("/search")
    fun getSearchInfo() : SearchInfoDocument {
        return bookSearchService.getSearchInfo()
    }
}