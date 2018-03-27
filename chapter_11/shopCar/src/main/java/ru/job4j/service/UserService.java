package ru.job4j.service;

import ru.job4j.dao.modeldao.UserImpl;
import ru.job4j.model.User;

import java.util.List;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 21.03.2018.
 * @version $Id$.
 * @since 0.1.
 */
public class UserService {
    private  UserImpl userDao;

    public UserService() {
        this.userDao = new UserImpl();
    }

    public List<User> getAllUsers() {
        userDao.openCurrentSession();
        List<User> users = userDao.getAll();
        userDao.closeCurrentSession();
        return users;
    }

    public User getUserById(Long id) {
        userDao.openCurrentSession();
        User user =  userDao.getEntityById(id);
        userDao.closeCurrentSession();
        return user;
    }

    public Long saveUser(User user) {
       userDao.openCurrentSessionWithTransaction();
       Long id = userDao.save(user);
       userDao.closeSessionWithTransaction();
       return id;
    }

    public void updateUser(User user) {
         userDao.openCurrentSessionWithTransaction();
         userDao.update(user);
         userDao.closeSessionWithTransaction();
    }

    public boolean deleteUser(Long id) {
        userDao.openCurrentSessionWithTransaction();
        boolean delete = userDao.delete(id);
        userDao.closeSessionWithTransaction();
        return delete;
    }

    public void deleteUser(User user) {
        userDao.openCurrentSessionWithTransaction();
        userDao.delete(user);
        userDao.closeSessionWithTransaction();
    }
}
