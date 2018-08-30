package ru.job4j.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import ru.job4j.filter.SalesPlatformUserPrincipal;
import ru.job4j.json.AuthJson;
import ru.job4j.model.User;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * Controller to retrieve user details.
 */
@RestController
public class UserController {

    @GetMapping(value = "/user", produces = "application/json;charset=UTF-8")
    public AuthJson getUser(Principal principal) {
        SalesPlatformUserPrincipal platformUserPrincipal =
            (SalesPlatformUserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        User user = platformUserPrincipal.getUser();
        HttpSession session =
            (HttpSession) RequestContextHolder.currentRequestAttributes().getSessionMutex();
        return new AuthJson(session.getId(),
            user.getId(), (long) session.getMaxInactiveInterval());
    }
}
