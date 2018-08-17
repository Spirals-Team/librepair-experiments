package ru.job4j.list;

/**
 * Class SimpleStructure.
 * @param <T> generic
 */
public interface SimpleStructure<T> {
    /**
     * Method poll.
     * @return element
     */
    T poll();
    /**
     * Method push.
     * @param value element for pushing.
     */
    void push(T value);
}
