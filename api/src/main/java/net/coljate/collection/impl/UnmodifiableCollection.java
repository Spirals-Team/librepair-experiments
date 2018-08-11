package net.coljate.collection.impl;

import java.util.Iterator;
import java.util.Objects;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.collection.ImmutableCollection;
import net.coljate.collection.MutableCollection;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class UnmodifiableCollection<T> extends AbstractCollection<T> {

    @SuppressWarnings("unchecked")
    public static <T> UnmodifiableCollection<T> viewOf(final Collection<? extends T> collection) {
        return collection instanceof UnmodifiableCollection
                ? (UnmodifiableCollection<T>) collection
                : new UnmodifiableCollection<>(collection);
    }

    private final Collection<? extends T> collection;

    protected UnmodifiableCollection(final Collection<? extends T> collection) {
        this.collection = collection;
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.covariant(collection.iterator());
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof UnmodifiableCollection
                && Objects.equals(this.collection, ((UnmodifiableCollection) that).collection);
    }

    @Override
    public MutableCollection<T> mutableCopy() {
        return MutableCollection.copyOf(collection);
    }

    @Override
    public ImmutableCollection<T> immutableCopy() {
        return ImmutableCollection.copyOf(collection.immutableCopy());
    }

}
