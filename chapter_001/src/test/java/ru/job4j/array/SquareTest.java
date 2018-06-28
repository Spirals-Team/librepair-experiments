package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SquareTest {
    @Test
    public void whenBoundIsThreeThenResultIsSquareThreeElements() {
		Square square = new Square();
		int[] result = square.calculate(3);
		int[] expected = new int[] {1, 4, 9};
		assertThat(result, is(expected));
	}

    @Test
    public void whenBoundIsFiveThenResultIsSquareFiveElements() {
        Square square = new Square();
		int[] result = square.calculate(5);
		int[] expected = new int[] {1, 4, 9, 16, 25};
		assertThat(result, is(expected));
    }
}