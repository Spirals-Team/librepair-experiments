package ru.job4j.nonblockingalgoritm.examples;

public interface LruCache<KEY, VALUE> {
    void put(KEY key, VALUE value);

    VALUE get(KEY key);

    VALUE getSilent(KEY key);

    void remove(KEY key);

    int size();
}
