package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayBondingTest {
    @Test
    public void firstArray() {
        ArrayBonding bonding = new ArrayBonding();
        int first[]  = new int[] {1, 5, 9, 10, 20};
        int second[]  = new int[] {2, 3, 8, 11};
        int result[] = bonding.bond(first, second);
        int expected[] = new int[] {1, 2, 3, 5, 8, 9, 10, 11, 20};
        assertThat(result, is(expected));
    }

    @Test
    public void secondArray() {
        ArrayBonding bonding = new ArrayBonding();
        int first[]  = new int[] {21, 23, 24, 40, 75, 76, 78, 77, 900, 2100, 2200, 2300, 2400, 2500};
        int second[]  = new int[] {10, 11, 41, 50, 65, 86, 98, 101, 190, 1100, 1200, 3000, 5000};
        int result[] = bonding.bond(first, second);
        int expected[] = new int[] {10, 11, 21, 23, 24, 40, 41, 50, 65, 75, 76, 78, 77, 86, 98, 101, 190, 900, 1100, 1200, 2100, 2200, 2300, 2400, 2500, 3000, 5000};
        assertThat(result, is(expected));
    }

}
