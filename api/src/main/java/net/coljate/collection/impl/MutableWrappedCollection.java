package net.coljate.collection.impl;

import java.util.Collections;
import java.util.Iterator;

import net.coljate.collection.MutableCollection;
import net.coljate.util.NativeCollections;

/**
 *
 * @author Ollie
 */
public class MutableWrappedCollection<T>
        extends WrappedCollection<T>
        implements MutableCollection<T> {

    public static <T> MutableWrappedCollection<T> viewOf(final java.util.Collection<T> collection) {
        return new MutableWrappedCollection<>(collection);
    }

    public static <T> MutableWrappedCollection<T> copyOf(final java.util.Collection<? extends T> collection) {
        return new MutableWrappedCollection<>(new java.util.ArrayList<>(collection));
    }

    private final java.util.Collection<T> delegate;

    protected MutableWrappedCollection(final java.util.Collection<T> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public boolean add(final T element) {
        return delegate.add(element);
    }

    public boolean addAll(final java.util.Collection<? extends T> iterable) {
        return delegate.addAll(delegate);
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public boolean removeFirst(final Object element) {
        return delegate.remove(element);
    }

    @Override
    public int removeAll(final Object element) {
        final int startingSize = delegate.size();
        delegate.removeAll(Collections.singleton(element));
        return startingSize - delegate.size();
    }

    @Override
    public boolean removeAll(final Iterable<?> elements) {
        return delegate.removeAll(NativeCollections.asCollection(elements));
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.iterator();
    }

    @Override
    public void clear() {
        delegate.clear();
    }

}
