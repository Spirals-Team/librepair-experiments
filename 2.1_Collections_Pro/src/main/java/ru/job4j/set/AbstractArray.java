package ru.job4j.set;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class AbstractArray.
 * @param <E> generic
 */
public abstract class AbstractArray<E> {
    /**
     * @param DEFAULT_CAPACITY default size of array
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * @param EMPTY_ELEMENTDATA size array when initialize by null
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};
    protected Object[] objects;
    protected int index = 0;

    /**
     * Constructor.
     *
     * @param initialCapacity start size.
     */
    public AbstractArray(int initialCapacity) {
        if (initialCapacity > 0) {
            this.objects = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
            this.objects = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
    }

    /**
     * Default constructor.
     */
    public AbstractArray() {
        this.objects = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Method add.
     *
     * @param e object for adding
     */
    public void put(E e) {
        if (index >= objects.length) {
            objects = Arrays.copyOf(objects, objects.length * 2);
            objects[index++] = e;
        } else {
            objects[index++] = e;
        }
    }

    /**
     * Iterator.
     *
     * @return iterator
     */
    public Iterator iterator() {
        Iterator<E> iterator = new Iterator<E>() {
            /**
             * @param position position in array
             */
            private int position = 0;

            /**
             * Method hasNext.
             * @return result
             */
            @Override
            public boolean hasNext() {
                return position < index;
            }

            /**
             * Method next.
             * @return object
             */
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (E) objects[position++];
            }
        };
        return iterator;
    }
}
