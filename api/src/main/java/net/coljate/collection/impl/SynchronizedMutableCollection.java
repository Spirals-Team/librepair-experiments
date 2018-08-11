package net.coljate.collection.impl;

import java.util.Iterator;

import net.coljate.collection.ImmutableCollection;
import net.coljate.collection.MutableCollection;

/**
 *
 * @author Ollie
 */
public class SynchronizedMutableCollection<T> implements MutableCollection<T> {

    private final MutableCollection<T> collection;

    public SynchronizedMutableCollection(final MutableCollection<T> collection) {
        this.collection = collection;
    }

    @Override
    public synchronized T[] arrayCopy(final T[] array) {
        return collection.arrayCopy(array);
    }

    @Override
    public synchronized boolean contains(final Object object) {
        return collection.contains(object);
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return collection.iterator();
    }

    @Override
    public boolean add(final T element) {
        return collection.add(element);
    }

    @Override
    public synchronized void clear() {
        collection.clear();
    }

    @Override
    public synchronized boolean removeFirst(final Object element) {
        return collection.removeFirst(element);
    }

    @Override
    public synchronized int removeAll(final Object element) {
        return collection.removeAll(element);
    }

    @Override
    public synchronized boolean removeAll(final Iterable<?> elements) {
        return collection.removeAll(elements);
    }

    @Override
    public synchronized MutableCollection<T> mutableCopy() {
        return collection.mutableCopy();
    }

    @Override
    public synchronized ImmutableCollection<T> immutableCopy() {
        return collection.immutableCopy();
    }

}
