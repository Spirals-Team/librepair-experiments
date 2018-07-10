package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterator over list of iterators.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class IteratorOfIterators<T> {

    private Iterator<Iterator<T>> it;
    private Iterator current;

    Iterator convert(Iterator<Iterator<T>> it) {
        this.it = it;

        return new Iterator() {
            @Override
            public boolean hasNext() {
                switchIterator();
                if (current == null) {
                    return false;
                }
                if (current.hasNext()) {
                    return true;
                }
                if (it.hasNext()) {
                    current = it.next();
                }
                return current.hasNext();
            }

            @Override
            public T next() {
                switchIterator();
                if (hasNext()) {
                    throw new NoSuchElementException();
                }
                if (!current.hasNext() && it.hasNext()) {
                    current = it.next();
                }
                return (T) current.next();
            }
        };
    }

    private void switchIterator() {
        if (current == null && it.hasNext()) {
            current = it.next();
        }
    }
}