package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MatrixTest {
    @Test
    public void whenMatrThreeThen() {
       	Matrix matr = new Matrix();
		int[][] res = matr.multiple(3);
		int[][] expected = {{0, 0, 0}, {0, 1, 2}, {0, 2, 4}};
		assertThat(res, is(expected));
	}
}