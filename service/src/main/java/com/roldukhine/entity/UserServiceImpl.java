package com.roldukhine.entity;

import com.roldukhine.api.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void addUser(User user) {
        logger.debug("addUser {}", user);
        userDao.insert(user);
    }

    public User getUser(long id) {
        logger.debug("getUser {}", id);
        return userDao.get(id);
    }

    @Transactional
    public void updateUser(User user) {
        logger.debug("updateUser {}", user);
        userDao.update(user);
    }

    @Transactional
    public void deleteUser(User user) {
        logger.debug("deleteUser {}", user);
        userDao.delete(user);
    }

    public User checkUser(String login, String password) {
        logger.debug("checkUser, login {}, password {}");

        List<User> userList = userDao.getAll();
        logger.debug("getAll {}", userList);

        User user = userList.stream()
                .filter(userItem -> userItem.getLogin().equals(login) && userItem.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        logger.debug("user by login and password {}", user);
        return user;
    }
}
