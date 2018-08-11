package net.coljate.collection.sorting;

import javax.annotation.Nonnull;

class MergeSortAlgorithmTest implements SortingAlgorithmTest {

    @Nonnull
    @Override
    public MergeSortAlgorithm algorithm() {
        return new MergeSortAlgorithm();
    }

}