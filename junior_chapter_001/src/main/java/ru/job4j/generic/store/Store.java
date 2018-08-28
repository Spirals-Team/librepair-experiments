package ru.job4j.generic.store;

import ru.job4j.generic.base.Base;

public interface Store<T extends Base> {
    void add(T model);

    boolean replace(String id, T model);

    boolean delete(String id);

    T findById(String id);
}
