package ru.job4j.set;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class AbstractSet.
 * @param <E> generic
 */
public abstract class AbstractLink<E> {
    protected int size = 0;
    protected Node<E> first;
    protected Node<E> last;

    /**
     * Iterator.
     * @return iterator
     */
    public Iterator<E> iterator() {
        Iterator<E> iterator = new Iterator<E>() {
            private Node<E> temp = first;
            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E result = temp.item;
                temp = temp.next;
                return result;
            }
        };
        return iterator;
    }

    /**
     * Method put.
     * @param e element for putting
     */
    public void put(E e) {
        if (size > 0) {
            Node<E> newLink = new Node<>(last, e, null);
            last.next = newLink;
            last = newLink;
            size++;
        } else {
            first = new Node<>(null, e, null);
            last = first;
            size++;
        }
    }

    /**
     * Method delete first link.
     */
    public void deleteFirst() {
        if (first == last) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
            size--;
        }
    }

    /**
     * Method delete last link.
     */
    public void deleteLast() {
        if (last == first) {
            last = null;
            first = null;
        } else {
            last = last.prev;
            last.next = null;
            size--;
        }
    }

    /**
     * Getter First.
     * @return First link
     */
    public Node<E> getFirst() {
        return first;
    }

    /**
     * Getter Last.
     * @return Last link
     */
    public Node<E> getLast() {
        return last;
    }

    /**
     * Getter size.
     * @return return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Static nested class Link.
     * @param <E> generic
     */
    protected static class Node<E> {
        protected E item;
        protected Node<E> prev;
        protected Node<E> next;

        /**
         * Constructor.
         * @param prev previous Link
         * @param item object
         * @param next next link
         */
        Node(Node<E> prev, E item, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
