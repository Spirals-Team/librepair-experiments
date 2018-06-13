package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * class MatrixIterator
 *
 * @author Aleksandr Visotskiy
 * @version 1.0
 * @since 13.15.18
 */

public class MatrixIterator implements Iterator {

    /**
     * This array serves to store objects.
     */
    private int[][] array;

    /**
     * This is a horizontal index.
     */
    private int horizontal;

    /**
     * This is a vertical index.
     */
    private int vertical;

    /**
     * This is a class constructor.
     *
     * @param array
     */
    public MatrixIterator(int[][] array) {
        this.array = array;
    }

    /**
     * This is a method hasNext.
     *
     * @return true if there is next object, other false.
     */
    @Override
    public boolean hasNext() {
        return this.horizontal != this.array.length;
    }

    /**
     * This is a method next;
     *
     * @return next object in iteration.
     */
    @Override
    public Object next() {
        if (this.horizontal >= array.length) {
            throw new NoSuchElementException();
        }
        int result = this.array[this.horizontal][this.vertical++];
        if (this.vertical >= this.array[this.horizontal].length) {
            this.vertical = 0;
            this.horizontal++;
        }
        return result;
    }
}