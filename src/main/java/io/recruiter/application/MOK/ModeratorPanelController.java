package io.recruiter.application.MOK;

import io.recruiter.application.common.networking.model.MessageResponse;
import io.recruiter.application.MOK.services.UserService;
import io.recruiter.application.common.database.model.AuthorityName;
import io.recruiter.application.common.database.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static io.recruiter.application.common.ErrorMessages.USER_DOES_NOT_EXIST;

@RestController
@RequestMapping("/editors")
@Secured("ROLE_MODERATOR")
public class ModeratorPanelController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> createEditor(@Valid @RequestBody User user) {
        try {
            userService.create(user, AuthorityName.ROLE_EDITOR);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(new MessageResponse("User exists"), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new MessageResponse("User created"), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<?> getAllEditors() {
        return new ResponseEntity<>(userService.getAllByAuthority(AuthorityName.ROLE_EDITOR), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> modifyEditor(@Valid @RequestBody User user) {
        try {
            Optional<User> optionalUser = userService.findByAuthoritiesAndUsername(AuthorityName.ROLE_EDITOR, user.getUsername());
            if (!optionalUser.isPresent()) {
                throw new IllegalArgumentException(USER_DOES_NOT_EXIST);
            }
            userService.edit(user);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new MessageResponse(e.getMessage()), HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(new MessageResponse("User modified"), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-account/{username}", method = RequestMethod.POST)
    ResponseEntity<?> deleteEditor(@PathVariable String username) {
        try {
            Optional<User> optionalUser = userService.findByAuthoritiesAndUsername(AuthorityName.ROLE_EDITOR, username);
            if (!optionalUser.isPresent()) {
                throw new IllegalArgumentException(USER_DOES_NOT_EXIST);
            }
            userService.delete(username);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(new MessageResponse("Deletion failed"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse("Editor deleted"), HttpStatus.OK);
    }

    @RequestMapping(value = "/promote/{username}", method = RequestMethod.PATCH)
    ResponseEntity<?> promoteToEditor(@PathVariable String username) {
        try {
            userService.promote(username, AuthorityName.ROLE_EDITOR);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(new MessageResponse("Promotion failed"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse("Promoted successfully"), HttpStatus.OK);
    }

    @RequestMapping(value = "/degrade/{username}", method = RequestMethod.PATCH)
    ResponseEntity<?> removeEditorAuthority(@PathVariable String username) {
        try {
            userService.removeAuthority(username, AuthorityName.ROLE_EDITOR);
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(new MessageResponse("Removing authority failed"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse("Authority removed successfully"), HttpStatus.OK);
    }
}
