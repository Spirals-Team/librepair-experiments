package ru.job4j.generic;

/**
 * Базовый интерфейс для хранилища
 * @param <T> параметр типа (наследник от Base)
 */
public interface Store<T extends Base> {

    void add(T model);

    boolean replace(String id, T model);

    boolean delete(String id);

    T findById(String id);
}
