package ru.job4j.sync;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashSet;
import java.util.Set;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Set<User> users = new HashSet<>();

    /**
     * Method add
     * @param user for adding
     * @return result
     */
    public synchronized boolean add(User user) {
        return users.add(user);
    }

    /**
     * Method update
     * @param user for updating
     * @return result
     */
    public synchronized boolean update(User user) {
        boolean result = false;
        for (User temp : users) {
            if (temp.getId() == user.getId()) {
                temp.setAmount(user.getAmount());
                result = true;
            }
        }
        return result;
    }

    /**
     * Method delete
     * @param user for deleting
     * @return result
     */
    public synchronized boolean delete(User user) {
        return users.remove(user);
    }

    /**
     * Method delete
     * @param fromId from user
     * @param toId to user
     * @param amount how much money
     * @return result
     */
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User from = null;
        User to = null;
        for (User temp : users) {
            if (temp.getId() == fromId) {
                from = temp;
            } else if (temp.getId() == toId) {
                to = temp;
            }
        }

        if (from != null && to != null) {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            result = true;
        }
        return result;
    }
}
