package ru.job4j.set;

/**
 * Interface SimpleSet.
 * @param <E> generic
 */
public interface SimpleSet<E> extends Iterable<E> {
    /**
     * Method add.
     * @param e element for adding.
     */
    void add(E e);
}
