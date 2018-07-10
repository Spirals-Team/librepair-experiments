package ru.job4j.synchronizy;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;
import ru.job4j.list.DynamicList;

import java.util.Iterator;

/**
 * Iterable thread safe dynamic collection with fail-fast behavior.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
@ThreadSafe
public class DynamicListThreadSafe<E> implements Iterable<E> {

    private final Object lock = new Object();
    @GuardedBy("this.lock")
    private DynamicList list;

    public DynamicListThreadSafe() {
        this.list = new DynamicList(1);
    }

    public DynamicListThreadSafe(int size) {
        this.list = new DynamicList(size);
    }

    public DynamicListThreadSafe(E... args) {
        this.list = new DynamicList(args);
    }

    /**
     * Add element to collection.
     *
     * @param value element
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
            return (E) this.list.get(index);
        }
    }

    /**
     * Size of collection.
     *
     * @return size
     */
    public int length() {
        synchronized (this.lock) {
            return this.list.length();
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
}
