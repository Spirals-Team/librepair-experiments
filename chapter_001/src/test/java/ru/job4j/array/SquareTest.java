package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SquareTest {
    @Test
    public void squareThen() {
        Square sq = new Square();
        int[] result = sq.calculate(10);
        int[] expectArray = new int[] {0, 1, 4, 9, 16, 25, 36, 49, 64, 81};
        assertThat(result, is(expectArray));
    }
}