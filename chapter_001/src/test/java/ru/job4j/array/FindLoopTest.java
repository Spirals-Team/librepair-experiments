package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FindLoopTest {
    @Test
    public void whenThirdThenIsThree() {
		FindLoop loop = new FindLoop();
		int[] res = new int[] {0, 56, 20, 30, 40};
		int result = loop.indexOf(res, 30);
		assertThat(result, is(3));
	}

    @Test
    public void whenSixThenResultIsEmpty() {
        FindLoop loop = new FindLoop();
		int[] res = new int[] {0, 1, 2, 3, 4};
		int result = loop.indexOf(res, 6);
		assertThat(result, is(-1));
    }
}