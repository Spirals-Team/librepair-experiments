package io.joaopinheiro.userservice.web;

import io.joaopinheiro.userservice.service.UserService;
import io.joaopinheiro.userservice.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {

    UserService userService;

    @Autowired
    public UserController(UserService service){
        this.userService = service;
    }

    @GetMapping(path = "users", produces = "application/json")
    public List<User> getUsers(){
        return userService.getAll();
    }

    @PostMapping(path= "users", produces = "application/json", consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody User user){
        User result = userService.createUser(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location).body(result);
    }

    @GetMapping(path="users/{id}", produces = "application/json")
    public User getUser(@PathVariable("id") Long id){
        return userService.getUserByID(id);
    }

    @PutMapping(path= "users/{id}", produces = "application/json")
    public User updateUser(@RequestBody User user, @PathVariable("id") Long id){
        return userService.updateUser(user, id);
    }

    @DeleteMapping(path ="users/{id}", produces = "application/json")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

}