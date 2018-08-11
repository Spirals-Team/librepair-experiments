package net.coljate.collection.sorting;

import java.util.Comparator;

class JdkSortingAlgorithm implements SortingAlgorithm {

    @Override
    public <T> void sort(T[] array, Comparator<? super T> comparator) {
        java.util.Arrays.sort(array, comparator);
    }

    @Override
    public void sort(final int[] array) {
        java.util.Arrays.sort(array);
    }

    @Override
    public void sort(double[] array) {
        java.util.Arrays.sort(array);
    }

}
