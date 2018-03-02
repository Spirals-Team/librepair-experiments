package ru.job4j.monitoresynchronizy;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import java.util.HashMap;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final HashMap<Integer, User> storage = new HashMap<>();

    public synchronized HashMap<Integer, User> getStorage() {
        return storage;
    }

    public synchronized boolean add(User user) {
        if (storage.containsKey(user.getId())) {
            return false;
        }
        storage.put(user.getId(), user);
        return true;

    }

    public synchronized boolean update(User user) {
        return storage.replace(user.getId(), user) != null;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        int fromAmount = storage.get(fromId).getAmount();
        if (fromAmount >= amount) {
            this.update(new User(fromId, fromAmount - amount));
            this.update(new User(toId, storage.get(toId).getAmount() + amount));
        }
    }
}
