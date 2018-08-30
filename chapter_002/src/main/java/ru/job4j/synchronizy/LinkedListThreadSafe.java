package ru.job4j.synchronizy;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.list.LinkedList;

import java.util.Iterator;

/**
 * Iterable thread safe linked list collection with fail-fast behavior.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
@ThreadSafe
public class LinkedListThreadSafe<E> implements Iterable<E> {

    private final Object lock = new Object();
    @GuardedBy("this.lock")
    LinkedList<E> list;

    public LinkedListThreadSafe() {
        this.list = new LinkedList<>();
    }

    public LinkedListThreadSafe(E... args) {
        this.list = new LinkedList<>(args);
    }

    /**
     * Add element to collection.
     *
     * @param value
     */
    public void add(E value) {
        synchronized (this.lock) {
            this.list.add(value);
        }
    }

    /**
     * Get element from collection by index.
     *
     * @param index of element
     * @return element
     */
    public E get(int index) {
        synchronized (this.lock) {
            return this.list.get(index);
        }
    }

    /**
     * Remove and return first element from collection.
     *
     * @return element
     */
    public E pollFirst() {
        synchronized (this.lock) {
            return this.list.pollFirst();
        }
    }

    /**
     * Remove and return last element from collection.
     *
     * @return element
     */
    public E pollLast() {
        synchronized (this.lock) {
            return this.list.pollLast();
        }
    }

    /**
     * Remove element from collection.
     *
     * @param value element to remove
     * @return {@code true} if element removed successfully
     */
    public boolean remove(E value) {
        synchronized (this.lock) {
            return this.list.remove(value);
        }
    }

    /**
     * Gets size of collection.
     *
     * @return size
     */
    public int size() {
        synchronized (this.lock) {
            return this.list.size();
        }
    }

    /**
     * Iterate over collection.
     *
     * @return iterator
     */
    @Override
    public Iterator<E> iterator() {
        synchronized (this.lock) {
            return this.list.iterator();
        }
    }

    /**
     * String to represent collection.
     *
     * @return presentation of collection
     */
    @Override
    public String toString() {
        synchronized (this.lock) {
            return this.list.toString();
        }
    }
}
