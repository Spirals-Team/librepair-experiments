package org.lwstudio.springtodo.web.controller;

import org.lwstudio.springtodo.constant.ResourceName;

import org.lwstudio.springtodo.service.UserService;
import org.lwstudio.springtodo.model.entity.User;
import org.lwstudio.springtodo.model.dto.UserDTO;

import org.lwstudio.springtodo.exception.ValidationException;
import org.lwstudio.springtodo.exception.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isFullyAuthenticated() and #id == principal.id")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        assertUserExist(id);

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<?> postUser(@RequestBody UserDTO userDTO) throws ValidationException {
        userService.saveUser(userDTO);

        User user = userService.getUserByUsername(userDTO.getUsername());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}/todos")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isFullyAuthenticated() and #id == principal.id")
    public ResponseEntity<?> putUser(@PathVariable Long id, @RequestBody UserDTO userDTO) throws ValidationException {
        assertUserExist(id);

        userDTO.setId(id);

        userService.modifyUserById(userDTO);

        User user = userService.getUserByUsername(userDTO.getUsername());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isFullyAuthenticated() and #id == principal.id")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        assertUserExist(id);

        userService.deleteUserById(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    // Helper Method
    private void assertUserExist(Long userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            ResourceNotFoundException exception = new ResourceNotFoundException();
            exception.setResourceName(ResourceName.USER);
            exception.setId(userId);

            throw exception;
        }
    }

}
