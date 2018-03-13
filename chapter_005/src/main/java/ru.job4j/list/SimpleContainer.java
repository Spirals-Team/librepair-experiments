package ru.job4j.list;

public interface SimpleContainer<E> extends Iterable<E> {
    void add(E value);
    E get(int index);
}
