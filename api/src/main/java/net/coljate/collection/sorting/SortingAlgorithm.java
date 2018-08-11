package net.coljate.collection.sorting;

import net.coljate.util.Arrays;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

import javax.annotation.Nonnull;
import java.util.Comparator;

public interface SortingAlgorithm {

    /**
     * Sort an array of elements according to the given comparator.
     */
    @TimeComplexity(bestCase = Complexity.LINEAR, worstCase = Complexity.QUADRATIC)
    <T> void sort(@Nonnull T[] array, Comparator<? super T> comparator);

    /**
     * Sort an array of naturally comparable elements.
     */
    @TimeComplexity(bestCase = Complexity.LINEAR, worstCase = Complexity.QUADRATIC)
    @SuppressWarnings("type.argument.type.incompatible")
    default <T extends Comparable<? super T>> void sort(final T[] array) {
        this.sort(array, Comparator.naturalOrder());
    }

    default void sort(final int[] array) {
        final Integer[] integers = Arrays.copyNativeArray(array);
        this.sort(integers);
        Arrays.writeNativeArray(integers, array);
    }

    default void sort(final double[] array) {
        final Double[] doubles = Arrays.copyNativeArray(array);
        this.sort(doubles);
        Arrays.writeNativeArray(doubles, array);
    }

    @Nonnull
    @TimeComplexity(bestCase = Complexity.LINEAR, worstCase = Complexity.QUADRATIC)
    default <T> T[] sortedCopy(@Nonnull final T[] array, final Comparator<? super T> comparator) {
        final T[] copy = Arrays.copy(array);
        this.sort(copy, comparator);
        return copy;
    }

    @Nonnull
    @TimeComplexity(bestCase = Complexity.LINEAR, worstCase = Complexity.QUADRATIC)
    default <T extends Comparable<? super T>> T[] sortedCopy(@Nonnull final T[] array) {
        return this.sortedCopy(array, Comparator.naturalOrder());
    }

    static <T> void swap(final T[] array, final int from, final int to) {
        if (from != to) {
            final T temp = array[from];
            array[from] = array[to];
            array[to] = temp;
        }
    }

    SortingAlgorithm JAVA_DEFAULT = new JdkSortingAlgorithm();
    SortingAlgorithm MERGE_SORT = new MergeSortAlgorithm();
    SortingAlgorithm QUICK_SORT = new QuicksortAlgorithm();

}
