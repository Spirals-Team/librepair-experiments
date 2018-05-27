package ru.job4j.list;

import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class QueueList<T> {
    protected ListContainer<T> list = new ListContainer<>();

    public void push(T value) {
        list.add(value);
    }

    public int getSize() {
        return list.getSize();
    }

    public T pull() {
        if (list.getSize() == 0) {
            throw new NoSuchElementException("Queue is empty.");
        }
        T item = list.get(0);
        list.remove(0);
        return item;
    }
}
