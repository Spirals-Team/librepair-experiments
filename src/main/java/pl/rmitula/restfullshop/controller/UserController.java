package pl.rmitula.restfullshop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.rmitula.restfullshop.model.User;
import pl.rmitula.restfullshop.model.dto.UserDto;
import pl.rmitula.restfullshop.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static pl.rmitula.restfullshop.controller.converter.Converter.fromUserDto;
import static pl.rmitula.restfullshop.controller.converter.Converter.toUserDto;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserDto> get() {
        return userService.getUsers().stream().map((User user) -> toUserDto(user)).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public UserDto findById(@PathVariable(name = "id") long id) {
        return toUserDto(userService.findById(id));
    }

    @GetMapping(path = "/findByUserName/{userName}")
    public UserDto findByUserName(@PathVariable(name = "userName") String userName) {
        return toUserDto(userService.findByUsername(userName));
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid UserDto userDto) {
        return new ResponseEntity<>(userService.create(fromUserDto(userDto)), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable(name = "id") long id, @RequestBody @Valid UserDto user) {
        userService.update(id, user.getUsername(), user.getFirstName(), user.getLastName(), user.getEmail());
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable(name = "id") long id) {
        userService.delete(id);
    }

}
