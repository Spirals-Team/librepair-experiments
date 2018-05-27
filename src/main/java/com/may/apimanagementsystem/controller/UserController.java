package com.may.apimanagementsystem.controller;

import com.may.apimanagementsystem.dto.ResponseEntity;
import com.may.apimanagementsystem.po.User;
import com.may.apimanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.may.apimanagementsystem.constant.ExceptionMessage.SUCCESS;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> register(User user) {
        userService.addUser(user);
        return new ResponseEntity<>(200, SUCCESS, user);
    }

    @PostMapping("login")
    public ResponseEntity<User> login(User user) {
        User userGet = userService.checkUser(user);
        return new ResponseEntity<>(200, SUCCESS, userGet);
    }

    @GetMapping("{userId}")
    public ResponseEntity<User> showUserInfo(@PathVariable int userId) {
        User user = userService.getUserByUserId(userId);
        return new ResponseEntity<>(200, SUCCESS, user);
    }

    @PutMapping("{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable int userId, User user) {
        userService.updateUser(user);
        return new ResponseEntity<>(200, SUCCESS, null);
    }

    @GetMapping("userName")
    public ResponseEntity<List<User>> showUsersByNameKey(String userName) {
        List<User> users = userService.searchUserByUserName(userName);
        return new ResponseEntity<>(200, SUCCESS, users);
    }

}
