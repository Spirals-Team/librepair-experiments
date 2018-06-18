package de.swt.inf.database;

import de.swt.inf.model.User;

public interface UserDao {

    public abstract User getUser(int USER_ID);

    public abstract boolean updateUser(int USER_ID);

    public abstract boolean deleteUser(int USER_ID);

    public abstract boolean addUser(User user);

    public User getUserByName(String username);

    public boolean isEmailInUse(String Email);


}
