package ru.job4j.ioc.task;

import java.util.List;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface Storage<T> {
    boolean add(final T entity);
    boolean del(final T entity);
    boolean del(final int id);
    boolean find(T entity);
    T find(final String name);
    T find(final int id);
    List<T> getUsers();
}
