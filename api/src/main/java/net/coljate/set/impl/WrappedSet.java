package net.coljate.set.impl;

import net.coljate.collection.impl.WrappedCollection;
import net.coljate.set.ImmutableSet;
import net.coljate.set.MutableSet;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class WrappedSet<T>
        extends WrappedCollection<T>
        implements Set<T> {

    private final java.util.Set<? extends T> delegate;

    public WrappedSet(final java.util.Set<? extends T> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public java.util.Set<T> mutableJavaCopy() {
        return new java.util.HashSet<>(delegate);
    }

    @Override
    public MutableSet<T> mutableCopy() {
        return new MutableWrappedSet<>(this.mutableJavaCopy());
    }

    @Override
    public ImmutableSet<T> immutableCopy() {
        return new ImmutableWrappedSet<>(this.mutableJavaCopy());
    }

}
