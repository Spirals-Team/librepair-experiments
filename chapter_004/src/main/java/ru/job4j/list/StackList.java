package ru.job4j.list;

import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class StackList<T> extends QueueList<T> {

    @Override
    public T pull() {
        if (super.list.getSize() == 0) {
            throw new NoSuchElementException("Stack is empty.");
        }
        T item = super.list.get(super.list.getSize() - 1);
        list.remove(super.list.getSize() - 1);
        return item;
    }
}
