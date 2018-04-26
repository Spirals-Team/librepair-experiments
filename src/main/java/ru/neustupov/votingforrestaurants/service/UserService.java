package ru.neustupov.votingforrestaurants.service;

import ru.neustupov.votingforrestaurants.model.User;
import ru.neustupov.votingforrestaurants.to.UserTo;
import ru.neustupov.votingforrestaurants.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    void update(User user);

    void update(UserTo user);

    List<User> getAll();

    User getByEmail(String email) throws NotFoundException;

    User getWithVotes(int id);

    void enable(int id, boolean enable);
}
