package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TurnTest {
    @Test
    public void arrayOne() {
        Turn turn = new Turn();
        int[] expected = {5, 4, 3, 2, 1};
        int[] result = {1, 2, 3, 4, 5};
        result = turn.back(result);
        assertThat(expected, is(result));
    }

    @Test
    public void arrayTwo() {
        Turn turn = new Turn();
        int[] expected = {2, 6, 1, 4};
        int[] result = {4, 1, 6, 2};
        result = turn.back(result);
        assertThat(expected, is(result));
    }
}
