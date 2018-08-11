package net.coljate.collection.sorting;

import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

import static org.assertj.core.api.Assertions.assertThat;

public interface SortingAlgorithmTest {

    @Nonnull
    SortingAlgorithm algorithm();

    @Test
    default void shouldSortEmpty() {
        final int[] ints = new int[0];
        this.algorithm().sort(ints);
        assertThat(ints).isEqualTo(new int[0]);
    }

    @Test
    default void shouldSortSingleton() {
        final int[] ints = {1};
        this.algorithm().sort(ints);
        assertThat(ints).isEqualTo(new int[]{1});
    }

    @Test
    default void shouldSortTwoElements() {
        {
            final int[] ints = {1, 2};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2});
        }
        {
            final int[] ints = {2, 1};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2});
        }
    }

    @Test
    default void shouldSortThreeElements() {
        {
            final int[] ints = {1, 2, 3};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2, 3});
        }
        {
            final int[] ints = {1, 3, 2};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2, 3});
        }
        {
            final int[] ints = {2, 1, 3};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2, 3});
        }
        {
            final int[] ints = {2, 3, 1};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2, 3});
        }
        {
            final int[] ints = {3, 1, 2};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2, 3});
        }
        {
            final int[] ints = {3, 2, 1};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2, 3});
        }
    }

    @Test
    default void shouldSortFiveItems() {
        {
            final int[] ints = {3, 2, 5, 1, 4};
            this.algorithm().sort(ints);
            assertThat(ints).isEqualTo(new int[]{1, 2, 3, 4, 5});
        }
    }

}