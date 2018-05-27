package ru.job4j.config;

import ru.job4j.models.User;
import ru.job4j.storage.CarStor;
import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SetValue {
    private final CarStor carStor = CarStor.INSTANCE;

    public void setUserInSession(Principal principal, HttpSession session) {
        if (principal != null) {
            User user = searUserName(principal.getName());
            session.setAttribute("user", user);
        }
    }

    private User searUserName(String name) {
        for (User user: carStor.getuStor().getAll()) {
            if (name.equals(user.getLogin())) {
                return user;
            }
        }
        return null;
    }
}
