package ru.javawebinar.topjava.web.user;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.DuplicateException;
import ru.javawebinar.topjava.repository.NotFoundException;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.AuthenticatedUser;

import java.util.Collection;

abstract class AbstractUserController {
    @Autowired
    private UserService service;

    public User register(User user) throws DuplicateException {
        Preconditions.checkArgument(user.isNew(), "not new");
        return service.register(user);
    }

    public void update(User user) throws NotFoundException, DuplicateException {
        Preconditions.checkArgument(!user.isNew(), "new");
        service.update(user, AuthenticatedUser.getUser());
    }

    public void remove(int id) throws NotFoundException {
        service.remove(id, AuthenticatedUser.getUser());
    }

    public User get(int id) throws NotFoundException {
        return service.get(id, AuthenticatedUser.getUser());
    }

    public User getByMail(String email) throws NotFoundException {
        return service.getByEmail(email, AuthenticatedUser.getUser());
    }

    public Collection<User> list() {
        return service.list(AuthenticatedUser.getUser());
    }
}
