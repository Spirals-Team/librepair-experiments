package ru.job4j.nonBlockingCache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    @GuardedBy("this")
    private ConcurrentHashMap<Integer, User> map = new ConcurrentHashMap<>();

    public User getValue(Integer key) {
        synchronized (map) {
            return map.get(key);
        }
    }

    public boolean add(Integer key, User user) {
        synchronized (map) {
            map.put(key, user);
        }
        return true;
    }

    public void update(Integer key, User value) {
        synchronized (map) {
            map.computeIfPresent(key, (k, oldValue) -> {
                int oldV = oldValue.getVersion();
                if (oldV != value.getVersion()) {
                    throw new OptimisticException();
                }
                map.put(k, value);
                return value;
            });
            map.get(key).incrementVersion();
        }
    }

    public boolean delete(Integer key) {
        synchronized (map) {
            map.remove(key);
        }
        return true;
    }

    private class OptimisticException extends RuntimeException {
        public OptimisticException() {
            super("Optimistic Exception!");
        }
    }
}
