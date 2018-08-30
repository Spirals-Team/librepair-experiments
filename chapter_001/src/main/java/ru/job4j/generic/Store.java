package ru.job4j.generic;

/**
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public interface Store<T extends Base> {

    void add(T model);

    boolean replace(String id, T model);

    boolean delete(String id);

    T findById(String id);
}
