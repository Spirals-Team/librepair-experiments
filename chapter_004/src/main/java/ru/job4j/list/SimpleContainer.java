package ru.job4j.list;

public interface SimpleContainer<T> extends Iterable<T> {
    boolean add(T element);

    T get(int index);
}
