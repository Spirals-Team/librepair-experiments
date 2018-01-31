package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.NotFoundException;

import java.util.Collection;

@Controller
public final class AdminRestController extends AbstractUserController {
    public void update(User user, User onBehalfOf) {
        // TODO: implement
    }

    public void remove(int userId, User onBehalfOf) {
        // TODO: implement
    }

    public User get(int userId, User onBehalfOf) {
        // TODO: implement
        return null;
    }

    @Override
    public User getByMail(String email) throws NotFoundException {
        return super.getByMail(email);
    }

    @Override
    public Collection<User> list() {
        return super.list();
    }
}
