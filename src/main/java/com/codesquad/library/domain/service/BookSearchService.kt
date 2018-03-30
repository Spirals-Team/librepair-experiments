package com.codesquad.library.domain.service

import com.codesquad.library.domain.Book
import com.codesquad.library.domain.BookSearchCriteria
import com.codesquad.library.domain.repositories.BookRepository
import com.codesquad.library.dtos.model.book.BookDocument
import com.codesquad.library.dtos.model.book.BookQueryDocument
import com.codesquad.library.dtos.model.book.SearchInfoDocument
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class BookSearchService constructor(private val bookRepository: BookRepository) {

    private val log: Logger = LoggerFactory.getLogger(BookSearchService::class.java)

    open fun getSearchInfo(): SearchInfoDocument {
        return generateSearchInfo()
    }

    open fun search(query: BookQueryDocument) : List<BookDocument> {
        var books = bookRepository.findByQuery(query)

        return books.map { b -> b.generateDocument() }
    }

    open fun generateSearchInfo() : SearchInfoDocument {
        return SearchInfoDocument(this.bookRepository.count(), BookSearchCriteria.values().asList())
    }


}