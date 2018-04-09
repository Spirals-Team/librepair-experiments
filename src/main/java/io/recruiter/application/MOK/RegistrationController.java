package io.recruiter.application.MOK;

import io.recruiter.application.MOK.services.UserService;
import io.recruiter.application.common.networking.model.MessageResponse;
import io.recruiter.application.common.database.model.AuthorityName;
import io.recruiter.application.common.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/register")
public class RegistrationController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            userService.create(user, AuthorityName.ROLE_USER);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(new MessageResponse(exception.getMessage()), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new MessageResponse("User created"), HttpStatus.OK);
    }
}
