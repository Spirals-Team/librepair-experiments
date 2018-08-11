package net.coljate.collection.impl;

import java.util.Iterator;
import java.util.Objects;
import java.util.Spliterator;

import net.coljate.collection.AbstractCollection;
import net.coljate.util.iterator.UnmodifiableIterator;
import net.coljate.collection.Collection;
import net.coljate.collection.ImmutableCollection;
import net.coljate.collection.MutableCollection;

/**
 *
 * @author Ollie
 */
public class WrappedCollection<T>
        extends AbstractCollection<T> {

    private final java.util.Collection<? extends T> collection;

    public WrappedCollection(final java.util.Collection<? extends T> collection) {
        this.collection = Objects.requireNonNull(collection, "collection");
    }

    @Override
    public Object[] arrayCopy() {
        return collection.toArray();
    }

    @Override
    public java.util.Collection<T> mutableJavaCopy() {
        return new java.util.ArrayList<>(collection);
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public boolean contains(final Object object) {
        return collection.contains(object);
    }

    @Override
    public int count() {
        return collection.size();
    }

    @Override
    public Iterator<T> iterator() {
        return UnmodifiableIterator.wrap(collection.iterator());
    }

    @Override
    public MutableCollection<T> mutableCopy() {
        return new MutableWrappedCollection<>(this.mutableJavaCopy());
    }

    @Override
    public ImmutableCollection<T> immutableCopy() {
        return new ImmutableWrappedCollection<>(this.mutableJavaCopy());
    }

    @Override
    @SuppressWarnings("unchecked") //Read-only
    public Spliterator<T> spliterator() {
        return (Spliterator<T>) collection.spliterator();
    }

    @Override
    public boolean equals(final Object that) {
        return super.equals(that);
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof WrappedCollection
                && Objects.equals(collection, ((WrappedCollection) that).collection);
    }

    @Override
    public int hashCode() {
        return collection.hashCode();
    }

}
