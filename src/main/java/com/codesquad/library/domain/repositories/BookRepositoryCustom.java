package com.codesquad.library.domain.repositories;

import com.codesquad.library.domain.Book;
import com.codesquad.library.dtos.model.book.BookQueryDocument;

import java.util.List;

public interface BookRepositoryCustom {

    List<Book> findByQuery(BookQueryDocument document);
}
