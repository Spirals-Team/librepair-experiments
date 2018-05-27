package ru.job4j.servlet;

import ru.job4j.crudservlet.User;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStore {
    private final static PoolSQL POOL_SQL = new PoolSQL();
    private final static UserStore INSTANCE = new UserStore();

    private UserStore() {
    }

    public static synchronized UserStore getInstance() {
        return INSTANCE;
    }

    public void addUser(User user) {
        POOL_SQL.addUser(user);
    }

    public void updateUser(String id, User userNew) {
        POOL_SQL.updateUser(id, userNew);
    }

    public void delUser(String id) {
        POOL_SQL.delUser(id);
    }

    public boolean isDublicat(String login) {
        boolean exist = false;
        for (User user : getUsers()) {
            if (user.getLogin().equals(login)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    public boolean isEnter(String login, String email) {
        boolean exist = false;
        for (User user : getUsers()) {
            if (user.getLogin().equals(login) && user.getEmail().equals(email)) {
                exist = true;
                break;
            }
        }
        return exist;
    }

    public User getUser(String login) {
        for (User user : getUsers()) {
            if (user.getLogin().equals(login)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return POOL_SQL.getUsers();
    }

    public List<String> getRoles() {
        return POOL_SQL.getRoles();
    }
}
