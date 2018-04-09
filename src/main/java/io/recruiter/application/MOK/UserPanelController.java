package io.recruiter.application.MOK;

import io.recruiter.application.common.networking.model.MessageResponse;
import io.recruiter.application.MOK.services.UserService;
import io.recruiter.application.common.database.model.User;
import io.recruiter.application.common.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController(value = "/userpanel")
public class UserPanelController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        return new ResponseEntity<>(
                userService.findByUsername(username),
                HttpStatus.OK
        );
    }

    @RequestMapping(method = RequestMethod.PUT)
    ResponseEntity<?> editAccount(@RequestBody User user, HttpServletRequest request) {
        try {
            String token = request.getHeader(tokenHeader).substring(7);
            String currentUsername = jwtTokenUtil.getUsernameFromToken(token);
            user.setUsername(currentUsername);
            userService.edit(user);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new MessageResponse(ex.getMessage()), HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(new MessageResponse("User modified"), HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteAccount", method = RequestMethod.PATCH)
    ResponseEntity<?> deleteAccount(HttpServletRequest request) {
        try {
            String token = request.getHeader(tokenHeader).substring(7);
            String currentUsername = jwtTokenUtil.getUsernameFromToken(token);
            userService.delete(currentUsername);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new MessageResponse("Requested user was not found in the repository!"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MessageResponse("User disabled"), HttpStatus.OK);
    }

}
