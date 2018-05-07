package com.simplenotesapp.simplenotesapp.mapper;

import com.simplenotesapp.simplenotesapp.dto.UserDto;
import com.simplenotesapp.simplenotesapp.model.Note;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class UserDtoMapper {

    @Autowired
    NoteDtoMapper noteDtoMapper;

    @Autowired
    NoteService noteService;

    public User mapToEntity(final UserDto userDto) {
        User a = new User(userDto.getId(),
                userDto.getLogin(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getPassword(),
                new HashSet<>(noteService.findAllById(userDto.getNotesId())));
        return a;
    }

    public UserDto mapToDto(final User user) {
        return new UserDto(user.getId(),
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getNotes().stream().map(Note::getId).collect(Collectors.toSet()));
    }
}
