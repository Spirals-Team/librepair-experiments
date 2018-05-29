package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FindLoopTest {
    @Test
    public void findIndexFiveThenThenArrayTen() {
        FindLoop find = new FindLoop();
        int result = find.indexOf(new int[] {2, 5, 6, 7, 2, 1}, 5);
        assertThat(result, is(1));
    }
    @Test
    public void findIndexElevenThenThenArrayTen() {
        FindLoop find = new FindLoop();
        int result = find.indexOf(new int[10], 11);
        assertThat(result, is(-1));
    }
}