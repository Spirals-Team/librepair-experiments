package net.coljate.list.impl;

import net.coljate.collection.Collection;
import net.coljate.collection.sorting.SortingAlgorithm;
import net.coljate.list.SortedArray;
import net.coljate.util.Arrays;
import net.coljate.util.complexity.TimeComplexity;

import java.util.Comparator;

/**
 *
 * @author Ollie
 */
public class ImmutableSortedArray<T>
        extends ImmutableNativeArray<T>
        implements SortedArray<T> {

    public static <T extends Comparable<? super T>> SortedArray<T> sort(final Collection<T> collection) {
        return sort(collection, Comparator.naturalOrder(), SortingAlgorithm.JAVA_DEFAULT);
    }

    @TimeComplexity(computed = true)
    public static <T> SortedArray<T> sort(
            final Collection<T> collection,
            final Comparator<? super T> comparator,
            final SortingAlgorithm sortingAlgorithm) {
        return sort((T[]) collection.arrayCopy(), comparator, sortingAlgorithm);
    }

    @TimeComplexity(computed = true)
    public static <T extends Comparable<? super T>> SortedArray<T> sort(final java.util.Collection<T> collection) {
        return sort(collection, Comparator.naturalOrder(), SortingAlgorithm.JAVA_DEFAULT);
    }

    @TimeComplexity(computed = true)
    public static <T> SortedArray<T> sort(
            final java.util.Collection<T> collection,
            final Comparator<? super T> comparator,
            final SortingAlgorithm sortingAlgorithm) {
        return sort((T[]) collection.toArray(), comparator, sortingAlgorithm);
    }

    @TimeComplexity(computed = true)
    public static <T> SortedArray<T> copyAndSort(
            final T[] array,
            final Comparator<? super T> comparator,
            final SortingAlgorithm sortingAlgorithm) {
        return sort(Arrays.copyOf(array), comparator, sortingAlgorithm);
    }

    @TimeComplexity(computed = true)
    public static <T> ImmutableSortedArray<T> sort(final T[] array, final Comparator<? super T> comparator, final SortingAlgorithm sortingAlgorithm) {
        sortingAlgorithm.sort(array, comparator);
        return new ImmutableSortedArray<>(array, comparator);
    }

    private final Comparator<? super T> comparator;

    protected ImmutableSortedArray(final Object[] array, final Comparator<? super T> comparator) {
        super(array);
        this.comparator = comparator;
    }

    @Override
    public Comparator<? super T> comparator() {
        return comparator;
    }

}
