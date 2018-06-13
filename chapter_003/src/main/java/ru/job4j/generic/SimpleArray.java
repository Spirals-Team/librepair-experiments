package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class is universal wrapper over an array.
 *
 * @param <T> - "T" is name parameter.
 */

public class SimpleArray<T> implements Iterable<T> {

    /**
     * An array that contains elements
     */
    private Object[] array;

    /**
     * Position is index for array.
     */
    private int position = 0;

    /**
     * This is constructor
     *
     * @param capacity - size of array.
     */
    public SimpleArray(int capacity) {
        this.array = new Object[capacity];
    }

    /**
     * This is method for adds elements in array.
     *
     * @param model - added element.
     */
    public void add(T model) {
            if (this.position >= this.array.length) {
                throw new RuntimeException("Overflow");
            }
            this.array[this.position++] = model;
        }

    /**
     * This is a method for changing elements in an array.
     *
     * @param index - index changed element.
     * @param model - new element.
     */
    public void set(int index, T model) {
        if (index < 0 || index >= position) {
            throw new IndexOutOfBoundsException();
        }
        this.array[index] = model;
    }

    /**
     * This is method return element the index.
     *
     * @param index - element index.
     * @return
     */
    public T get(int index) {
        if (index < 0 || index >= position) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    /**
     * This is method delete element in array.
     *
     * @param index - index of the element to be deleted
     */
    public void delete(int index) {
        if (index < 0 || index >= position) {
            throw new IndexOutOfBoundsException();
        }
        System.arraycopy(this.array, index + 1, this.array, index, position - index - 1);
        position--;
    }

    /**
     * This is an auxiliary method.
     *
     * @return array length
     */
    public int getLength() {
        return array.length;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int pointer = 0;

            @Override
            public boolean hasNext() {
                return pointer < position;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return (T) array[pointer++];
            }
        };
    }
}
