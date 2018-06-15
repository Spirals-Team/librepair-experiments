package ru.job4j.review;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConvertTest {

    @Test
    public void whenListThenArray() {
        Convert convert = new Convert();
        int[][] result = convert.makeArray(Arrays.asList(1, 2, 3, 4, 5, 6, 7),
                3);
        int[][] expect = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 0, 0}
        };
        assertThat(result, is(expect));
    }

    @Test
    public void whenArrayThenList() {
        Convert convert = new Convert();
        int[][] input = {
                {1, 2},
                {3, 4}
        };
        List<Integer> expect = Arrays.asList(
                1, 2, 3, 4
        );
        List<Integer> result = convert.makeList(input);
        assertThat(result, is(expect));
    }
}