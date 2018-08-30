package ru.job4j.map;

import java.util.NoSuchElementException;

/**
 * An object that maps keys to values.
 *
 * A map cannot contain duplicate keys. Doesn't permit null as a key.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public interface Map<K, V> {

    /**
     * Returns the number of key-value mappings in this map.
     *
     * @return the number of key-value mappings in this map
     */
    int size();

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     *
     * @throws NoSuchElementException if the specified key is null
     */
    V get(K key);

    /**
     * Associates the specified value with the specified key in this map
     * (optional operation). If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.
     *
     * @param key   key with which the specified value is to be associated
     * @param value value to be associated with the specified key
     * @return {@code true} or {@code false} as result of operation
     */
    boolean insert(K key, V value);

    /**
     * Removes the mapping for a key from this map if it is present
     *
     * @param key key whose mapping is to be removed from the map
     * @return true or false as result of operation
     */
    boolean delete(K key);
}
