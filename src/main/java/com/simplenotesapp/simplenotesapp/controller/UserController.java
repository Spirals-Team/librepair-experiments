package com.simplenotesapp.simplenotesapp.controller;

import com.simplenotesapp.simplenotesapp.dto.UserDto;
import com.simplenotesapp.simplenotesapp.dto.UserWithNotesDto;
import com.simplenotesapp.simplenotesapp.mapper.UserDtoMapper;
import com.simplenotesapp.simplenotesapp.mapper.UserWithNotesDtoMapper;
import com.simplenotesapp.simplenotesapp.model.User;
import com.simplenotesapp.simplenotesapp.service.UserService;
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
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;

    @Autowired
    private UserWithNotesDtoMapper userWithNotesDtoMapper;

    @RequestMapping(value = "/api/users", method = RequestMethod.POST)
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto saved = userDtoMapper.mapToDto(userService.save(userDtoMapper.mapToEntity(userDto)));
        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(userService.findOneById(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.findAll();
        List<UserDto> usersDtos = users.stream().map(user -> userDtoMapper.mapToDto(user)).collect(Collectors.toList());
        return new ResponseEntity<>(usersDtos, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserWithNotesDto> getUser(@PathVariable Long id) {
        UserWithNotesDto userWithNotesDto = userWithNotesDtoMapper.mapToDto(userService.findOneById(id));
        return new ResponseEntity<>(userWithNotesDto, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.PUT)
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) {
        UserDto updatedUserDto = userDtoMapper.mapToDto(userService.update(userDtoMapper.mapToEntity(userDto)));
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }
}