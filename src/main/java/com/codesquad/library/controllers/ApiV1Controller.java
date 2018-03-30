package com.codesquad.library.controllers;

import com.codesquad.library.domain.Book;
import com.codesquad.library.domain.authentication.Member;
import com.codesquad.library.domain.repositories.FeaturedRepository;
import com.codesquad.library.domain.service.BookService;
import com.codesquad.library.domain.service.IsbnBookSearchService;
import com.codesquad.library.dtos.model.book.BookQueryDocument;
import com.codesquad.library.dtos.model.book.BookSearchDocument;
import com.codesquad.library.dtos.model.book.NewBookDocument;
import com.codesquad.library.utils.JwtParsingUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
public class ApiV1Controller {

    private static final Logger log = LoggerFactory.getLogger(ApiV1Controller.class);

    @Autowired
    private JwtParsingUtils parsingUtils;

    @Autowired
    private BookService bookService;

    @Autowired
    private IsbnBookSearchService searchService;

    @GetMapping("/userinfo")
    @PreAuthorize("hasRole('ROLE_USER')")
    public Member getUserinfo(OAuth2Authentication authentication) {
        return parsingUtils.getLoggedInMember(authentication);
    }

    @PostMapping("/book")
    public void newBook(@RequestBody NewBookDocument document) {
        bookService.addBook(document);
    }

    @PostMapping("/booksearch")
    public List<Book> searchBook(@RequestBody BookQueryDocument document) {
        return bookService.searchBookByQuery(document);
    }

    @GetMapping("/newbook/{isbn}")
    @CrossOrigin
    public BookSearchDocument searchIsbn(@PathVariable long isbn) {
        return searchService.getBookInfo(isbn);
    }
}
