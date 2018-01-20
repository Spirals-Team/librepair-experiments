package ru.curriculum.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.curriculum.application.route.Routes;

import static ru.curriculum.web.View.*;


// TODO: может быть отдельный контроллер для управления пользователями и преподователями
@Controller
public class AdminController {

    @RequestMapping(value = Routes.admin, method = RequestMethod.GET)
    public String adminIndex() {
        return INDEX;
    }
}
