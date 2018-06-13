package ru.job4j.generic;

/**
 * @author Alexander Visotskiy
 * @version $Id$
 * @since 0.1
 */
public interface Store<T extends Base> {

    void add(T model);

    void replace(String id, T model);

    void delete(String id);

    T findById(String id);
}
