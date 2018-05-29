package ru.job4j.coffe;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ChangeTest {
    @Test
    public void whenChangeGet11() {
        int[] expect = new int[] {10, 10, 5};
        Change coffee = new Change();
        int[] actual = coffee.changes(50, 25);
        assertThat(actual, is(expect));
    }
}