package ru.job4j.list;

/**
 * Simple queue based on Linkedlist.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @see LinkedList
 * @version 1
 */
public class SimpleQueue<T> {

    private LinkedList<T> list;

    public SimpleQueue() {
        this.list = new LinkedList<>();
    }

    public T poll() {
        return list.pollFirst();
    }

    public void push(T value) {
        list.add(value);
    }
}
