package ru.job4j.storage;

import ru.job4j.config.AppContext;
import ru.job4j.models.User;
import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStorage implements StorageDAO<User> {
    private final UserDataRepository repository = new AppContext().getContext().getBean(UserDataRepository.class);

    @Override
    public User add(User entity) {
        repository.save(entity);
        return entity;
    }

    @Override
    public List<User> getAll() {
        return (List<User>) repository.findAll();
    }

    @Override
    public User findById(int id) {
        return repository.findById(id).get();
    }

    @Override
    public void del(User entity) {
        repository.delete(entity);
    }

    @Override
    public void del(int id) {
        repository.deleteById(id);
    }
}
