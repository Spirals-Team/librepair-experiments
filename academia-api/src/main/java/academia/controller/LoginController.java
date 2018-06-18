package academia.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class LoginController {

    private User getUser(UsernamePasswordAuthenticationToken principal) {
        UsernamePasswordAuthenticationToken token = principal;
        if (token == null) {
            return null;
        }
        return (User) token.getPrincipal();
    }

    @RequestMapping("/api/public/login")
    public User login(Principal principal) {
        return getUser((UsernamePasswordAuthenticationToken) principal);
    }

    @RequestMapping("/api/private/check")
    public User check(Principal principal) {
        return getUser((UsernamePasswordAuthenticationToken) principal);
    }
}
