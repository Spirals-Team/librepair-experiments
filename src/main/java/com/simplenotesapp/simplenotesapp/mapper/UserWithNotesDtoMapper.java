package com.simplenotesapp.simplenotesapp.mapper;

import com.simplenotesapp.simplenotesapp.dto.UserDto;
import com.simplenotesapp.simplenotesapp.dto.UserWithNotesDto;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public class UserWithNotesDtoMapper {

    @Autowired
    NoteDtoMapper noteDtoMapper;

    @Autowired
    NoteService noteService;

    public User mapToEntity(final UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getLogin(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getPassword(),
                new HashSet<>(noteService.findAllById(userDto.getNotesId())));
    }

    public UserWithNotesDto mapToDto(final User user) {
        return new UserWithNotesDto(user.getId(),
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getPassword(),
                user.getNotes().stream().map(noteDtoMapper::mapToDto).collect(Collectors.toSet()));
    }
}
