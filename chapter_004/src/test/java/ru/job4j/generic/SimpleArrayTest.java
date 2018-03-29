package ru.job4j.generic;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleArrayTest {
    @Test
    public void whenAddReturnsNumber() {
        SimpleArray<Integer> array = new SimpleArray<>(4);

        array.add(7);
        Integer result = array.get(0);

        assertThat(result, is(7));
    }

    @Test
    public void whenSetGetsTheElement() {
        SimpleArray<Integer> array = new SimpleArray<>(4);

        array.add(7);
        array.add(3);
        array.add(5);
        array.add(1);
        array.set(2, 24);

        Integer expected = 24;
        Integer result = array.get(2);

        assertThat(result, is(expected));
    }

    @Test
    public void whenDeleteReturnsArrayWithoutTheElement() {
        SimpleArray<Integer> array = new SimpleArray<>(4);

        array.add(7);
        array.add(3);
        array.add(5);
        array.add(1);
        array.delete(2);

        Integer[] expected = new Integer[] {7, 3, 1};
        Object[] result = array.getAll();

        assertThat(result, is(expected));
    }
}