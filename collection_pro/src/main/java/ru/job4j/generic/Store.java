package ru.job4j.generic;

public interface Store<T extends  Base> {

    void add(T model);

    boolean replace(String id);

    boolean delete(String id);

    T findVyId(String id);
}
