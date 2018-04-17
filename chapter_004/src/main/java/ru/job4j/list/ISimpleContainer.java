package ru.job4j.list;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public interface ISimpleContainer<E> extends Iterable<E> {
    void add(E e);
    E get(int index);
}
