package com.simplenotesapp.simplenotesapp.controller;

import com.simplenotesapp.simplenotesapp.dto.NoteDto;
import com.simplenotesapp.simplenotesapp.dto.NoteWithUsersDto;
import com.simplenotesapp.simplenotesapp.mapper.NoteDtoMapper;
import com.simplenotesapp.simplenotesapp.mapper.NoteWithUsersDtoMapper;
import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.service.NoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@Controller
public class NoteController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private NoteService noteService;

    @Autowired
    private NoteDtoMapper noteDtoMapper;

    @Autowired
    private NoteWithUsersDtoMapper noteWithUsersDtoMapper;

    @RequestMapping(value = "/api/notes", method = RequestMethod.POST)
    public ResponseEntity<NoteDto> addNote(@RequestBody NoteDto noteDto) {
        NoteDto saved = noteDtoMapper.mapToDto(noteService.save(noteDtoMapper.mapToEntity(noteDto)));
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/notes/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        noteService.delete(noteService.findOneById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/notes", method = RequestMethod.GET)
    public ResponseEntity<List<NoteDto>> getNotes() {
        List<Note> notes = noteService.findAll();
        List<NoteDto> notesDtos = notes.stream().map(note -> noteDtoMapper.mapToDto(note)).collect(Collectors.toList());
        return new ResponseEntity<>(notesDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/notes/{id}", method = RequestMethod.GET)
    public ResponseEntity<NoteWithUsersDto> getNote(@PathVariable Long id) {
        NoteWithUsersDto noteWithUsersDto = noteWithUsersDtoMapper.mapToDto(noteService.findOneById(id));
        return new ResponseEntity<>(noteWithUsersDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/notes", method = RequestMethod.PUT)
    public ResponseEntity<NoteDto> updateNote(@RequestBody NoteDto noteDto) {
        NoteDto updatedNoteDto = noteDtoMapper.mapToDto(noteService.update(noteDtoMapper.mapToEntity(noteDto)));
        return new ResponseEntity<>(updatedNoteDto, HttpStatus.OK);
    }
}