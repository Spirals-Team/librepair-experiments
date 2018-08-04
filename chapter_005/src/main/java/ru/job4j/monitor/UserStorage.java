package ru.job4j.monitor;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private Map<Integer, User> list = new HashMap<>();

    public synchronized boolean add(User user) {
        return list.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        int key = user.getId();
        return list.containsKey(key) && list.put(key, user) != null;
    }

    public synchronized boolean delete(User user) {
        return list.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User userFrom = list.get(fromId);
        if (twoUserExist(fromId, toId) && userFrom.getAmount() >= amount) {
            User userTo = list.get(toId);
            userFrom.changeAmount(-amount);
            userTo.changeAmount(amount);
            result = true;
        }
        return result;
    }

    public synchronized User getUser(int id) {
        return list.get(id);
    }

    private synchronized boolean twoUserExist(int fromId, int toId) {
        return list.containsKey(fromId) && list.containsKey(toId);
    }
}
