package ru.curriculum.web.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.curriculum.application.route.Routes;
import ru.curriculum.web.Redirect;


@Controller
public class AuthController {

    @RequestMapping(Routes.index)
    public String index() {
        return Redirect.redirectTo(Routes.users);
    }

    @RequestMapping(Routes.login)
    public String login() {
        return "login";
    }

    @RequestMapping(Routes.accessDenied)
    public String accessDenied() {
        return "error/accessDenied";
    }
}
