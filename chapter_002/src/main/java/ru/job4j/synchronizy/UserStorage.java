package ru.job4j.synchronizy;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;

/**
 * Class Storage.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
@ThreadSafe
public class UserStorage {

    private final Object lock = new Object();
    @GuardedBy("this.lock")
    private HashMap<Integer, User> storage = new HashMap<>();

    /**
     * Add user to storage.
     *
     * @param user {@link User}
     * @return {@code true} if user added successfully
     */
    public boolean add(User user) {
        synchronized (this.lock) {
            return storage.putIfAbsent(user.getId(), user) == null;
        }
    }

    /**
     * Update user in storage.
     *
     * @param user {@link User}
     * @return {@code true} if user updated successfully
     */
    public boolean update(User user) {
        synchronized (this.lock) {
            return storage.replace(user.getId(), user) != null;
        }
    }

    /**
     * Delete user from storage.
     *
     * @param user {@link User}
     * @return {@code true} if user deleted successfully
     */
    public boolean delete(User user) {
        synchronized (this.lock) {
            return storage.remove(user.getId()) != null;
        }
    }

    /**
     * Transfer money from user to user.
     *
     * @param fromId id of user from
     * @param toId   id of user to
     * @param amount amount of money to transfer
     */
    public void transfer(int fromId, int toId, int amount) {
        synchronized (this.lock) {
            User from = storage.get(fromId);
            User to = storage.get(toId);
            if (from.getAmount() >= amount) {
                from.setAmount(from.getAmount() - amount);
                to.setAmount(to.getAmount() + amount);
            }
        }
    }
}
