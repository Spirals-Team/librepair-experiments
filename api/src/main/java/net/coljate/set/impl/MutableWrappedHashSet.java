package net.coljate.set.impl;

import net.coljate.set.HashSet;

import java.io.Serializable;

import net.coljate.util.Arrays;
import net.coljate.util.Iterables;

/**
 *
 * @author Ollie
 */
public class MutableWrappedHashSet<T>
        extends MutableWrappedSet<T>
        implements HashSet<T>, Serializable {

    public static final int DEFAULT_INITIAL_CAPACITY = 10;

    public static <T> MutableWrappedHashSet<T> create() {
        return create(DEFAULT_INITIAL_CAPACITY);
    }

    public static <T> MutableWrappedHashSet<T> create(final int initialCapacity) {
        return viewOf(new java.util.HashSet<>(initialCapacity));
    }

    public static <T> MutableWrappedHashSet<T> viewOf(final java.util.HashSet<T> set) {
        return new MutableWrappedHashSet<>(set);
    }

    @SafeVarargs
    public static <T> MutableWrappedHashSet<T> copyOf(final T... elements) {
        final java.util.HashSet<T> set = new java.util.HashSet<>(elements.length);
        Arrays.consume(elements, set::add);
        return viewOf(set);
    }

    public static <T> MutableWrappedHashSet<T> copyOf(final java.util.Collection<? extends T> set) {
        return new MutableWrappedHashSet<>(new java.util.HashSet<>(set));
    }

    public static <T> MutableWrappedHashSet<T> copyOf(final Iterable<? extends T> iterable) {
        final java.util.HashSet<T> set = new java.util.HashSet<>(Iterables.maybeCount(iterable).orElse(10));
        iterable.forEach(set::add);
        return new MutableWrappedHashSet<>(set);
    }

    private static final long serialVersionUID = 1L;

    private final java.util.HashSet<T> delegate;

    protected MutableWrappedHashSet(final java.util.HashSet<T> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public java.util.HashSet<T> mutableJavaCopy() {
        return new java.util.HashSet<>(delegate);
    }

    @Override
    public MutableWrappedHashSet<T> mutableCopy() {
        return new MutableWrappedHashSet<>(this.mutableJavaCopy());
    }

}
