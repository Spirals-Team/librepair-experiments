package ru.job4j.storage;

import java.util.List;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface StorageDAO<T> {
    T add(final T entity);
    List<T> getAll();
    T findById(final int id);
    void del(final T entity);
    void del(final int id);
}
