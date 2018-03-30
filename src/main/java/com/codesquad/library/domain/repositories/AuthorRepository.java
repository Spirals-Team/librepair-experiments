package com.codesquad.library.domain.repositories;

import com.codesquad.library.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> findByName(String name);
}
