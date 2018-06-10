package ru.job4j.threads;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
import java.util.concurrent.ConcurrentHashMap;
@ThreadSafe
public class SimpleCash<K, T> {
    private volatile static int version = 0;
    private volatile int modVersion;

    public SimpleCash() {
        version++;
        modVersion = version;
    }

    @GuardedBy("this")
    private ConcurrentHashMap<K, T> map = new ConcurrentHashMap<>();

    public synchronized boolean add(K key, T value) {
        T result = map.putIfAbsent(key, value);
        return result == null;
    }

    public synchronized boolean update(K key, T value) throws  OplimisticException {
        T result;
        if (modVersion == version) {
            modVersion++;
            result = map.put(key, value);
        } else {
            throw new OplimisticException();
        }
        return result != null;
    }

    public synchronized boolean delete(K key, T value) throws  OplimisticException {
        boolean result;
        if (modVersion == version) {
            modVersion++;
            result = map.remove(key, value);
        } else {
            throw new OplimisticException();
        }
        return result;
    }
}

class OplimisticException extends Exception {
     OplimisticException() {
        System.out.println("Данные были изменены");
    }
}
