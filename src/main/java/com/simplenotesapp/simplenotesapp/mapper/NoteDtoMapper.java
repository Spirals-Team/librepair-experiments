package com.simplenotesapp.simplenotesapp.mapper;

import com.simplenotesapp.simplenotesapp.dto.NoteDto;
import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class NoteDtoMapper {

    @Autowired
    UserDtoMapper userDtoMapper;

    @Autowired
    UserService userService;

    public Note mapToEntity(final NoteDto noteDto) {
        return new Note(noteDto.getId(),
                noteDto.getTitle(),
                noteDto.getContent(),
                new HashSet<>(userService.findAllById(noteDto.getUsersId())));
    }

    public NoteDto mapToDto(final Note note) {
        return new NoteDto(note.getId(),
                note.getTitle(),
                note.getContent(),
                note.getUsers().stream().map(User::getId).collect(Collectors.toSet()));
    }
}
