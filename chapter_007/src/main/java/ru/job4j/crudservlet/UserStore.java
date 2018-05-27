package ru.job4j.crudservlet;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class UserStore {
    private final static UserSQL USER_SQL = new UserSQL();
    private final static UserStore INSTANCE = new UserStore();

    private UserStore() {
    }

    public static synchronized UserStore getInstance() {
        return INSTANCE;
    }

    public void addUser(User user) {
        USER_SQL.addUser(user);
    }

    public void updateUser(String id, User userNew) {
        USER_SQL.updateUser(id, userNew);
    }

    public void delUser(User user) {
        USER_SQL.delUser(user);
    }

    public void delUser(String id) {
        USER_SQL.delUser(id);
    }

    public List<User> getUsers() {
        return USER_SQL.getUsers();
    }
}
