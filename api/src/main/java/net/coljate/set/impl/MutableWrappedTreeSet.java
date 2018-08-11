package net.coljate.set.impl;

import java.util.Comparator;

import net.coljate.collection.Collection;
import net.coljate.set.MutableSortedSet;
import net.coljate.util.Arrays;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;
import net.coljate.util.suppliers.Suppliers;

/**
 *
 * @author Ollie
 */
public class MutableWrappedTreeSet<T>
        extends MutableWrappedSet<T>
        implements MutableSortedSet<T> {

    public static <T> MutableWrappedTreeSet<T> create(final Comparator<? super T> comparator) {
        return viewOf(new java.util.TreeSet<>(comparator));
    }

    public static <T> MutableWrappedTreeSet<T> viewOf(final java.util.TreeSet<T> set) {
        return new MutableWrappedTreeSet<>(set);
    }

    public static <T> MutableWrappedTreeSet<T> copyOf(final java.util.SortedSet<T> set) {
        return new MutableWrappedTreeSet<>(new java.util.TreeSet<>(set));
    }

    @TimeComplexity(Complexity.LINEARITHMIC)
    public static <T> MutableWrappedTreeSet<T> copyOf(
            final Comparator<? super T> comparator,
            final Collection<? extends T> collection) {
        final java.util.TreeSet<T> set = collection.mutableJavaCopy(i -> new java.util.TreeSet<>(comparator));
        return viewOf(set);
    }

    @SafeVarargs
    public static <T> MutableWrappedTreeSet<T> copyOf(final Comparator<? super T> comparator, final T... elements) {
        final java.util.TreeSet<T> set = new java.util.TreeSet<>(comparator);
        Arrays.consume(elements, set::add);
        return viewOf(set);
    }

    @SafeVarargs
    public static <T extends Comparable<? super T>> MutableWrappedTreeSet<T> copyOf(final T... elements) {
        final java.util.TreeSet<T> set = new java.util.TreeSet<>();
        Arrays.consume(elements, set::add);
        return viewOf(set);
    }

    private final java.util.TreeSet<T> delegate;

    protected MutableWrappedTreeSet(final java.util.TreeSet<T> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Comparator<? super T> comparator() {
        return Suppliers.firstNonNull(
                delegate.comparator(),
                (Comparator<T>) Comparator.naturalOrder());
    }

    @Override
    public T least() {
        return delegate.first();
    }

    @Override
    public T greatest() {
        return delegate.last();
    }

    @Override
    public MutableWrappedTreeSet<T> mutableCopy() {
        return new MutableWrappedTreeSet<>(this.mutableJavaCopy());
    }

    @Override
    public java.util.TreeSet<T> mutableJavaCopy() {
        return new java.util.TreeSet<>(delegate);
    }

    @Override
    public MutableWrappedTreeSet<T> sortedCopy(final Comparator<? super T> comparator) {
        return new MutableWrappedTreeSet<>(this.mutableJavaCopy());
    }

}
