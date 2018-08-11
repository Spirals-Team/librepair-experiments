package net.coljate.collection.sorting;

import net.coljate.util.Arrays;

import java.util.Comparator;

class MergeSortAlgorithm implements SortingAlgorithm {

    @Override
    public <T> void sort(final T[] array, final Comparator<? super T> comparator) {
        final T[] work = Arrays.copy(array);
        this.topDownSplitThenMerge(work, 0, work.length, array, comparator);
    }

    private <T> void topDownSplitThenMerge(final T[] work, final int start, final int end, final T[] out, final Comparator<? super T> comparator) {
        if (end - start < 2) {
            return;
        }
        final int middle = (start + end) / 2;
        this.topDownSplitThenMerge(out, start, middle, work, comparator);
        this.topDownSplitThenMerge(out, middle, end, work, comparator);
        this.topDownMerge(work, start, middle, end, out, comparator);
    }

    private <T> void topDownMerge(final T[] source, final int start, final int middle, final int end, final T[] out, final Comparator<? super T> comparator) {
        int i = start, j = middle;
        for (int k = start; k < end; k++) {
            if (i < middle && (j >= end || comparator.compare(source[i], source[j]) <= 0)) {
                out[k] = source[i++];
            } else {
                out[k] = source[j++];
            }
        }
    }

}
