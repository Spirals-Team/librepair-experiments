package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TurnTest {
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        Turn even = new Turn();
        int[] p;
        p = new int[]{2, 6, 1, 4};
        int[] result;
        result = even.back(p);
        int[] n;
        n = new int[]{4, 1, 6, 2};
        assertThat(result, is(n));
    }

    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        Turn odd = new Turn();
        int[] w;
        w = new int[]{1, 2, 3, 4, 5};
        int[] result;
        result = odd.back(w);
        int[] a;
        a = new int[]{5, 4, 3, 2, 1};
        assertThat(result, is(a));
    }
}