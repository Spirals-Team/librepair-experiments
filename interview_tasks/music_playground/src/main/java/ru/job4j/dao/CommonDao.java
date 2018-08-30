package ru.job4j.dao;

import java.util.List;

/**
 * DAO interface.
 *
 * @param <T> type
 * @param <K> key
 */
public interface CommonDao<T, K> {

    T findOne(K key);

    List<T> findAll();

    K create(T entity);

    boolean update(T entity);

    boolean delete(T entity);

    boolean deleteById(K key);
}
