package ru.javawebinar.topjava.web.user;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.DuplicateException;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.web.AuthenticatedUser;

@Controller
public final class ProfileRestController extends AbstractUserController {
    @Override
    public void update(User user) throws NotFoundException, DuplicateException {
        super.update(user);
    }

    @Override
    public void remove(int id) throws NotFoundException {
        super.remove(AuthenticatedUser.getUser().getId());
    }

    @Override
    public User get(int id) throws NotFoundException {
        return super.get(AuthenticatedUser.getUser().getId());
    }
}
