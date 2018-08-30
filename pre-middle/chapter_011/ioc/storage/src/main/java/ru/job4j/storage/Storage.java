package ru.job4j.storage;

import java.util.Collection;

/**
 * Interface Storage.
 */
public interface Storage<T, K> {

    /**
     * Find entity
     *
     * @param key key
     * @return entity
     */
    T findOne(K key);

    /**
     * Find all entities
     *
     * @return entities
     */
    Collection<T> findAll();

    /**
     * Create entity
     *
     * @param entity entity
     * @return key
     */
    K create(T entity);

    /**
     * Update entity
     *
     * @param entity entity
     */
    void update(T entity);

    /**
     * Delete entity
     *
     * @param entity entity
     */
    void delete(T entity);

    /**
     * Delete entity
     *
     * @param key key
     */
    void deleteById(K key);
}
