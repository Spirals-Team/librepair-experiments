package ru.job4j.list;

/**
 * Simple Stack base on dynamic list.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @see DynamicList
 * @version 1
 */
public class SimpleStack<T> {

    private LinkedList<T> list;

    public SimpleStack() {
        this.list = new LinkedList<>();
    }

    public T poll() {
        return list.pollLast();
    }

    public void push(T value) {
        list.add(value);
    }
}
