package ru.job4j.forum;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

public class ConvertList2ArrayTest {
    @Test
    public void when7ElementsThen9() {
        ConvertList2Array list = new ConvertList2Array();
        int[][] result = list.twoArray(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), 3);
        int[][] expect = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        assertThat(result, is(expect));
    }

    @Test
    public void when7ElementsThen9Test() {
        ConvertList2Array list = new ConvertList2Array();
        int[][] result = list.twoArray(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), 2);
        int[][] expect = {
                {1, 2, 3, 4},
                {5, 6, 7, 8}
        };
        assertThat(result, is(expect));
    }

    @Test
    public void when8ElementsThen8Test() {
        ConvertList2Array list = new ConvertList2Array();
        int[][] result = list.twoArray(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8), 4);
        int[][] expect = {
                {1, 2},
                {3, 4},
                {5, 6},
                {7, 8}
        };
        assertThat(result, is(expect));
    }
}