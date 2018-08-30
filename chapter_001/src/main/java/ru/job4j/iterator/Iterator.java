package ru.job4j.iterator;

import java.util.NoSuchElementException;

/**
 * An iterator over a collection.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public interface Iterator {

    /**
     * Returns the next element in the iteration.
     *
     * @return the next element in the iteration
     * @throws NoSuchElementException if the iteration has no more elements
     */
    Object next();

    /**
     * Returns {@code true} if the iteration has more elements.
     *
     * @return {@code true} if the iteration has more elements
     */
    boolean hasNext();
}
