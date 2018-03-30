package com.codesquad.library.domain.repositories;

import com.codesquad.library.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long>, BookRepositoryCustom {

    Optional<Book> findByTitle(String title);
}
