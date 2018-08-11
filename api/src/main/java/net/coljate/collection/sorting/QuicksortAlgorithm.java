package net.coljate.collection.sorting;

import java.util.Comparator;

/**
 * Select a `random' partition index, and re-order the array so thatelements less than the value at the partition index
 * (the `pivot') precede it, and elements greater than the pivot succeed it.
 * By performing this recursively on smaller subarrays the original array is eventually fully sorted.
 * <p>
 * Uses Lomuto partition scheme.
 * <p>
 * For example suppose you have an array {@code [3, 2, 5, 1, 4]}.
 * Choose the left-most element {@code 3} as the initial pivot.
 * For each element in the array, if it is less than the right-most element, swap it with the pivot and increment the
 * partition index;
 * thus {@code 3<4 => pivot=2}; {@code 2<4 => pivot=5}; {@code 1<4 => pivot=}
 */
class QuicksortAlgorithm implements SortingAlgorithm {

    @Override
    public <T> void sort(final T[] array, final Comparator<? super T> comparator) {
        this.quicksort(array, comparator, 0, array.length - 1);
    }

    private <T> void quicksort(final T[] array, final Comparator<? super T> comparator, final int low, final int high) {
        if (high > low) {
            final int partition = this.partition(array, comparator, low, high);
            this.quicksort(array, comparator, low, partition - 1);
            this.quicksort(array, comparator, partition + 1, high);
        }
    }

    private <T> int partition(final T[] array, final Comparator<? super T> comparator, final int start, final int end) {
        //Lomuto partition scheme
        int partition = start;
        for (int i = start; i < end; i++) {
            if (comparator.compare(array[i], array[end]) < 0) {
                SortingAlgorithm.swap(array, partition++, i);
            }
        }
        SortingAlgorithm.swap(array, partition, end);
        return partition;
    }

}
