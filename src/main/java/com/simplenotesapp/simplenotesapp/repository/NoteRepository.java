package com.simplenotesapp.simplenotesapp.repository;

import com.simplenotesapp.simplenotesapp.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findOneById(final Long id);

    Set<Note> findAllByTitle(final String title);

}