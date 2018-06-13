package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CombiningTwoSortedArraysTest {
    @Test
    public void testMethodCombiningTwoSortedArraysTest() {
        CombiningTwoSortedArrays comb = new CombiningTwoSortedArrays();
        int[] a;
        a = new int[]{1, 3, 5, 7, 9, 11};
        int[] b;
        b = new int[]{2, 4, 6, 8, 10, 22, 24, 26, 100000};
        int[] c;
        c = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 22, 24, 26, 100000};
        int[] result;
        result = comb.array(a, b);
        assertThat(result, is(c));
    }
}
