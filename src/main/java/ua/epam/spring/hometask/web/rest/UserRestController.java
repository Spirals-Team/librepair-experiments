package ua.epam.spring.hometask.web.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;
import ua.epam.spring.hometask.web.exception.ResourceNotFoundException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping("/user/{id}")
    public User getById(@PathVariable long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        return userService.getById(id);

    }

    @PutMapping("/user/{id}")
    public User update(@PathVariable("id") long id, @RequestBody User userDetails) {
        User user = userService.getById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }

        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setEmail(userDetails.getEmail());
        user.setBirthday(userDetails.getBirthday());
        user.setPassword(userDetails.getPassword());
        user.setRoles(userDetails.getRoles());

        return userService.save(user);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") long id) {
        User user = userService.getById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userService.remove(user);
    }

    @GetMapping("/user")
    public Collection<User> getAll() {
        return userService.getAll();
    }

    @PostMapping("user/upload")
    public void uploadUsers(@RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            String json = new String(file.getBytes());
            System.out.println(json);
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();

            List<User> users = mapper.readValue(json, new TypeReference<List<User>>() {
            });
            users.forEach(u -> userService.save(u));
        }
    }

}
