package ru.job4j.generic;

public interface Store<T extends Base> {

    T add(T model);

    T update(T model);

    boolean delete(String id);
}
