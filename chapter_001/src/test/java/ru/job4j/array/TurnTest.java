package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TurnTest {
    @Test
    public void backArrayFive() {
        Turn ba = new Turn();
        int[] result = ba.back(new int[]{1, 2, 3, 4, 5});
        int[] expectArray = new int[]{5, 4, 3, 2, 1};
        assertThat(result, is(expectArray));
    }
    @Test
    public void backArrayFour() {
        Turn ba = new Turn();
        int[] result = ba.back(new int[]{4, 1, 6, 2});
        int[] expectArray = new int[] {2, 6, 1, 4};
        assertThat(result, is(expectArray));
    }
}
