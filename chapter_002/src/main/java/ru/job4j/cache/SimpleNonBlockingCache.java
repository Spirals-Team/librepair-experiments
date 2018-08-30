package ru.job4j.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Non blocking cache.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class SimpleNonBlockingCache<K, V extends CacheModel> implements NonBlockingCache<K, V> {

    private ConcurrentHashMap<K, CacheModel> map = new ConcurrentHashMap<>();

    /**
     * Add value
     *
     * @param key   specified key
     * @param value value associated with key
     */
    @Override
    public void add(K key, V value) {
        map.putIfAbsent(key, value);
    }

    /**
     * Update value for specified key
     *
     * @param key      specified key
     * @param newValue new value replaced the old one
     */
    @Override
    public void update(K key, V newValue) {
        map.computeIfPresent(key, (k, v) -> {
            int ver = map.get(key).getVersion();
            if (ver != v.getVersion()) {
                throw new OptimisticException(
                    new Throwable("Mismatched object's version"));
            }
            v = newValue;
            v.setVersion(ver + 1);
            return v;
        });
    }

    /**
     * Remove value associated with key
     *
     * @param key key to remove
     */
    @Override
    public void delete(K key) {
        map.remove(key);
    }

    /**
     * Get size of cache
     *
     * @return size of cache
     */
    public int size() {
        return map.size();
    }

    /**
     * Get value by associated key
     *
     * @param key key associated with value
     * @return value
     */
    public V get(Integer key) {
        return (V) map.get(key);
    }

    @Override
    public String toString() {
        return String.format("SimpleNonBlockingCache{map=%s}", map);
    }
}
