package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayThreeSortTest {
    @Test
    public void threeSort() {
        ArrayThreeSort s = new ArrayThreeSort();
        int[] result = s.threeIsSort(new int[]{10, 11, 12, 13, 14}, new int[]{6, 7, 8, 9, 10});
        int[] expectArray = new int[]{6, 7, 8, 9, 10, 10, 11, 12, 13, 14};
        assertThat(result, is(expectArray));
    }
}