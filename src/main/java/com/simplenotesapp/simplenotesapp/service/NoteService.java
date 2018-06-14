package com.simplenotesapp.simplenotesapp.service;


import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class NoteService {

    @Autowired
    NoteRepository noteRepository;

    @Transactional
    public Note save(final Note note) {
        note.getUsers().forEach(user -> user.addNote(note));
        return noteRepository.save(note);
    }

    @Transactional
    public void delete(final Note note) {
        note.getUsers().forEach(user -> user.removeNote(note));
        noteRepository.delete(note);
    }

    @Transactional
    public Note update(final Note note) {
        Note updatedNote = findOneById(note.getId());

        updatedNote.setTitle(note.getTitle());
        updatedNote.setContent(note.getContent());
        updatedNote.getUsers().forEach(user -> user.removeNote(updatedNote));
        updatedNote.setUsers(note.getUsers());
        updatedNote.getUsers().forEach(user -> user.addNote(updatedNote));

        return updatedNote;
    }

    public List<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note findOneById(final Long id) {

        return noteRepository.findOneById(id).get();
    }

    public List<Note> findAllById(final Set<Long> ids) {
        return noteRepository.findAllById(ids);
    }

    public Set<Note> findAllByTitle(final String title) {
        return noteRepository.findAllByTitle(title);
    }

    public Note findOneByIdNullable(final Long id) {
        return noteRepository.findOneById(id).orElse(null);
    }
}
