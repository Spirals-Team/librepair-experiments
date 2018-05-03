package ru.job4j.arr;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MasstwomernarrTest {
    @Test
    public void testirovaniedumern() {
        int[][] arr = new int[][]{
                {1, 2},
                {3, 4}};
        int[] expected = new int[]{1, 3};
        assertThat(Masstwomernarr.arrmassDinODINod(arr), is(expected));
    }
}