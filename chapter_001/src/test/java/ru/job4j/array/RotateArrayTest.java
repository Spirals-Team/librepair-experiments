package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RotateArrayTest {
    @Test
    public void firstArray() {
        RotateArray rotateArray = new RotateArray();
        int[][] result = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] expected = {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}};
        result = rotateArray.rotate(result);
        assertThat(result, is(expected));
    }

    @Test
    public void secondArray() {
        RotateArray rotateArray = new RotateArray();
        int[][] result = {{1, 2}, {3, 4}};
        int[][] expected = {{3, 1}, {4, 2}};
        result = rotateArray.rotate(result);
        assertThat(result, is(expected));
    }
}
