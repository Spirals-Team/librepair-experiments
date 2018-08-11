package net.coljate.collection.impl;

import net.coljate.util.iterator.UnmodifiableIterator;
import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.collection.Empty;
import net.coljate.collection.MutableCollection;

/**
 *
 * @author Ollie
 */
public class EmptyCollection<T>
        extends AbstractCollection<T>
        implements Empty<T> {

    private static final EmptyCollection EMPTY_COLLECTION = new EmptyCollection();

    @SuppressWarnings("unchecked")
    public static <T> EmptyCollection<T> instance() {
        return EMPTY_COLLECTION;
    }

    @Override
    public MutableCollection<T> mutableCopy() {
        return MutableCollection.copyOf();
    }

    @Override
    @SuppressWarnings("unchecked")
    public UnmodifiableIterator<T> iterator() {
        return UnmodifiableIterator.of();
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof EmptyCollection;
    }

    @Override
    public int hashCode() {
        return 123;
    }

}
