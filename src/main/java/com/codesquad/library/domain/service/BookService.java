package com.codesquad.library.domain.service;

import com.codesquad.library.domain.Author;
import com.codesquad.library.domain.Book;
import com.codesquad.library.domain.BookCategories;
import com.codesquad.library.domain.authentication.Member;
import com.codesquad.library.domain.exceptions.model.NoBookExistsException;
import com.codesquad.library.domain.repositories.AuthorRepository;
import com.codesquad.library.domain.repositories.BookRepository;
import com.codesquad.library.dtos.model.book.BookDocument;
import com.codesquad.library.dtos.model.book.BookQueryDocument;
import com.codesquad.library.dtos.model.book.NewBookDocument;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public List<BookDocument> getAllBooks() {
        List<BookDocument> books = Lists.newArrayList();

        bookRepository.findAll().forEach(b -> books.add(b.generateDocument()));
        return books;
    }

    public Book borrowBook(Member member, Book book) {
        book.setPossessed(member);

        return bookRepository.save(book);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book addBook(NewBookDocument document) {
        Book book = Book.builder()
                .title(document.getTitle())
                .description(document.getDescription())
                .isbn(document.getIsbn())
                .bookCategories(BookCategories.findByLabel(document.getCategory()))
                .bookLocations(document.getLocation())
                .build();

        Author author = authorRepository.findByName(document.getAuthor()).orElse(
                Author.builder()
                .name(document.getAuthor())
                .build()
        );

        book.setAuthor(author);

        return bookRepository.save(book);
    }

    public Book borrowBookbyBookid(Member member, long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new NoBookExistsException("해당 ID를 가진 책이 없습니다."));

        book.setPossessed(member);
        return book;
    }

    public Book borrowBookByTitle(Member member, String bookTitle) {
        Book book = bookRepository.findByTitle(bookTitle).orElseThrow(() -> new NoBookExistsException("제목으로 검색된 책이 없습니다."));

        book.setPossessed(member);
        return book;
    }

    public Book returnBookbyBookId(Member member, long bookId) {
        Book book = bookRepository.findById(bookId).get();

        //TODO throws an exception if lender - requested member does not match.
        book.returnBook(member);
        return book;
    }

    public List<Book> searchBookByQuery(BookQueryDocument document) {
        return bookRepository.findByQuery(document);
    }

    public List<BookDocument> searchBookByQuery() {
        return Streams.stream(bookRepository.findAll()).map(b -> b.generateDocument()).collect(Collectors.toList());
    }

}
