package ru.job4j.even;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class EvenIterator
 *
 * @author Aleksandr Vysotskiy.
 * @version 1.5
 * @since 22.05.18
 */

public class EvenIterator implements Iterator {

    /**
     * This array serves to store objects.
     */
    private int[] array;

    /**
     * This is a index.
     */
    private int index;

    /**
     * This is a constructor
     *
     * @param array is this.array.
     */
    public EvenIterator(int[] array) {
        this.array = array;
    }

    /**
     * This is hasNext method.
     *
     * @return true if there is next object, other false.
     */
    @Override
    public boolean hasNext() {
        boolean even = false;
        while (this.index != array.length) {
            if (this.array[index] % 2 == 0) {
                even = true;
                break;
            }
            this.index++;
        }
        return even;
    }

    /**
     * This is method next.
     *
     * @return next object.
     */
    @Override
    public Object next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return this.array[index++];
    }
}