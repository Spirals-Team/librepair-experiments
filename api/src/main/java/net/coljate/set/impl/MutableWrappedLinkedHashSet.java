package net.coljate.set.impl;

import net.coljate.set.SequentialSet;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class MutableWrappedLinkedHashSet<T>
        extends MutableWrappedSet<T>
        implements SequentialSet<T> {

    public static <T> MutableWrappedLinkedHashSet<T> create(final int initialCapacity) {
        return viewOf(new java.util.LinkedHashSet<>(initialCapacity));
    }

    public static <T> MutableWrappedLinkedHashSet<T> viewOf(final java.util.LinkedHashSet<T> set) {
        return new MutableWrappedLinkedHashSet<>(set);
    }

    protected MutableWrappedLinkedHashSet(final java.util.LinkedHashSet<T> delegate) {
        super(delegate);
    }

    @Override
    public T first() {
        return Iterators.first(this.iterator());
    }

    @Override
    public T last() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public java.util.LinkedHashSet<T> mutableJavaCopy() {
        return this.mutableJavaCopy(java.util.LinkedHashSet::new);
    }

    @Override
    public MutableWrappedLinkedHashSet<T> mutableCopy() {
        return new MutableWrappedLinkedHashSet<>(this.mutableJavaCopy());
    }

}
