package ru.job4j.cache;

/**
 * Non blocking cache.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public interface NonBlockingCache<K, V> {

    /**
     * Associates the specified value with the specified key in this map.
     */
    void add(K key, V value);

    /**
     * Update value in map,
     * if the map previously contained a mapping for the key,
     * the old value is replaced.
     */
    void update(K key, V newValue);

    /**
     * Removes the mapping for a key from this map if it is present
     */
    void delete(K key);
}
