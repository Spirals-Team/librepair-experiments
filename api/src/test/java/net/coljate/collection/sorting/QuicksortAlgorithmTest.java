package net.coljate.collection.sorting;

import javax.annotation.Nonnull;

class QuicksortAlgorithmTest implements SortingAlgorithmTest {

    @Nonnull
    @Override
    public SortingAlgorithm algorithm() {
        return new QuicksortAlgorithm();
    }

}