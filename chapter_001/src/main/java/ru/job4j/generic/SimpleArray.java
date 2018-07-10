package ru.job4j.generic;

/**
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class SimpleArray<T> {

    private Object[] items;
    private int idx;

    public SimpleArray(int size) {
        this.items = new Object[size];
    }

    /**
     * Appends the specified element to the end of this array.
     *
     * @param value value to be appended to this list
     */
    public void add(T value) {
        items[idx++] = value;
    }

    /**
     * Replaces the element at the specified position in this array with
     * the specified element.
     *
     * @param position position of the element to replace
     * @param value    value to be stored at the specified position
     * @throws IndexOutOfBoundsException
     */
    public void update(int position, T value) {
        items[position] = value;

    }

    /**
     * Removes the element at the specified position in this array.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     *
     * @param position the position of the element to be removed
     * @throws IndexOutOfBoundsException
     */
    public void delete(int position) {
        items[position] = null;
        System.arraycopy(items, position + 1,
            items, position, items.length - 1 - position);
        items[items.length - 1] = null;
    }

    /**
     * Returns the element at the specified position in this array.
     *
     * @param position position of the element to return
     * @return the element at the specified position in this array
     * @throws IndexOutOfBoundsException
     */
    public T get(int position) {
        return (T) items[position];
    }

    public int size() {
        return idx;
    }
}
