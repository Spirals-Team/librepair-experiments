package ru.job4j.list;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public interface SimpleContainer<E> extends Iterable<E> {
    void add(E e);
    E get(int index);
}
