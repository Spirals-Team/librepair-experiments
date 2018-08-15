package ru.job4j.set;

/**
 * Interface SimpleContainer.
 * @param <E> generic
 */
public interface SimpleContainer<E> extends Iterable<E> {
    /**
     * Method add.
     * @param e element for adding
     */
    void add(E e);

    /**
     * Method get.
     * @param index position in array.
     * @return E
     */
    E get(int index);
}
