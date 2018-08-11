package net.coljate.collection.sorting;

import javax.annotation.Nonnull;

class JdkSortingAlgorithmTest implements SortingAlgorithmTest {

    @Nonnull
    @Override
    public SortingAlgorithm algorithm() {
        return new JdkSortingAlgorithm();
    }

}