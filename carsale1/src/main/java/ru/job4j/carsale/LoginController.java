package ru.job4j.carsale;

import org.apache.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.models.User;
import ru.job4j.storage.CarStor;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
@Controller
public class LoginController {
    private static final Logger LOG = Logger.getLogger(LoginController.class);
    private final CarStor carStor = CarStor.INSTANCE;
    private int id = -1;

    @RequestMapping(value = "/main/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return "LoginPage";
    }

    @RequestMapping(value = "/main/login/exit", method = RequestMethod.GET)
    public String exitSession(HttpSession session) {
        session.invalidate();
        return "redirect:/main";
    }

    @RequestMapping(value = "/main/login/new", method = RequestMethod.GET)
    public String getNewUserPage() {
        return "NewUserPage";
    }

    @RequestMapping(value = "/main/login/new", method = RequestMethod.POST)
    public String addNewUser(@RequestParam("login") String login,
                                @RequestParam("password") String password,
                                @RequestParam("password2") String password2,
                                HttpSession session,
                                ModelMap model) {
        if (!password.equals(password2)) {
            model.addAttribute("login", login);
            model.addAttribute("error", "Пароли не совпадают.");
            return "NewUserPage";
        } else if (password.matches("^[a-zA-z0-9]") || password.length() < 8) {
            model.addAttribute("login", login);
            model.addAttribute("error",
                    "Длина пароля должна быть не менее 8 символов. Обязатльно из букв и/или цифр.");
            return "NewUserPage";
        } else {
            List<User> list = carStor.getuStor().getAll();
            for (User user : list) {
                if (user.getLogin().equalsIgnoreCase(login)) {
                    model.addAttribute("login", login);
                    model.addAttribute("error", "Такой логин уже занят.");
                    return "NewUserPage";
                }
            }
            id = (carStor.getuStor().add(new User(login, new BCryptPasswordEncoder().encode(password)))).getId();
            return "redirect:/main/login";
        }
    }
}
