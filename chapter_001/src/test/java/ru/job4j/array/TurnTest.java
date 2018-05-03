package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TurnTest {

    @Test
    public void testirovanieArrayTurnFOReach() {
        int[] excepted = new int[]{6, 5, 4, 3, 2, 1};
        int[] arr123 = new int[]{1, 2, 3, 4, 5, 6};
        int[] intarray = new Turn().backArray(arr123);
        assertThat(intarray, is(excepted));
    }
    @Test
    public void testirovanieArrayTurnFOReach2() {
        int[] excepted = new int[]{5, 4, 3, 2, 1};
        int[] arr1234 = new int[]{1, 2, 3, 4, 5};
        int[] intarray = new Turn().backArrayV2(arr1234);
        assertThat(intarray, is(excepted));
    }
}
