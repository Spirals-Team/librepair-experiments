package com.simplenotesapp.simplenotesapp.service;


import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    public Note save(final Note note) {
        return noteRepository.save(note);
    }

    public void delete(final Note note) {
        noteRepository.delete(note);
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note findOneById(final long id) {
        return noteRepository.findOneById(id).get();
    }

    public List<Note> findAllById(final Set<Long> ids) {
        return noteRepository.findAllById(ids);
    }

    public Set<Note> findAllByTitle(final String title) {
        return noteRepository.findAllByTitle(title);
    }

    public Note findOneByIdNullable(final long id) {
        return noteRepository.findOneById(id).orElse(null);
    }
}
