package ru.job4j.set;

import ru.job4j.list.LinkedList;

import java.util.Iterator;

/**
 * A collection that contains no duplicate elements.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class SimpleSet2<E> {

    private LinkedList<E> container;

    public SimpleSet2() {
        this.container = new LinkedList<>();
    }

    public SimpleSet2(E... args) {
        this.container = new LinkedList<>();
        for (E value : args) {
            add(value);
        }
    }

    /**
     * Adds the specified element to this set if it is not already present.
     *
     * @param value element to be added to this set
     */
    public void add(E value) {
        for (E element : container) {
            if (element != null && element.equals(value)) {
                return;
            }
        }
        container.add(value);
    }

    /**
     * Returns an iterator over the elements in this set.
     *
     * @return an iterator over the elements in this set
     */
    public Iterator<E> iterator() {
        Iterator it = container.iterator();
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public E next() {
                return (E) it.next();
            }
        };
    }

    /**
     * Returns a string representation of the sequence of elements.
     *
     * @return a string representation of the sequence of elements
     */
    @Override
    public String toString() {
        Iterator<E> it = container.iterator();
        String result = "";
        while (it.hasNext()) {
            result += it.next();
        }
        return result;
    }
}
