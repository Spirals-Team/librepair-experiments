package dev.repositories.note;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.Note;

public interface NoteDataJpaRepo extends JpaRepository<Note, Long> {

}
