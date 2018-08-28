package ru.job4j.monsyn;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private Map<Integer, User> mapUser = new HashMap<>();

    public synchronized boolean add(User user) {
        mapUser.put(user.getId(), user);
        mapUser.get(user.getId());
        if (mapUser.get(user.getId()) != null) {
            return true;
        }
        return false;
    }

    public synchronized boolean update(User user) {
        if (mapUser.get(user.getId()) != null) {
            mapUser.remove(user.getId());
            mapUser.put(user.getId(), user);
            return true;
        }
        return false;
    }

    public synchronized boolean delete(User user) {
        if (mapUser.get(user.getId()) != null) {
            mapUser.remove(user.getId());
            return true;
        }
        return false;
    }

    public synchronized void transfer(int fromid, int told, int amount) {
        User one = mapUser.get(fromid);
        User two = mapUser.get(told);
        if (one.getAmount() >= amount) {
            one.setAmount(one.getAmount() - amount);
            two.setAmount(two.getAmount() + amount);
        }
    }
}
