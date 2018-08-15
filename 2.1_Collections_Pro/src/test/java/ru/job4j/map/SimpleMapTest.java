package ru.job4j.map;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class SimpleMapTest.
 */
public class SimpleMapTest {
    /**
     * Test method insert.
     */
    @Test
    public void whenAddThreeItemThenSizeIsThree() {
        SimpleMap<Integer, Integer> sm = new SimpleMap<>();
        sm.insert(1, 1);
        sm.insert(2, 2);
        sm.insert(3, 3);
        int result = sm.size();
        assertThat(result, is(3));
    }

    /**
     * Test method get.
     */
    @Test
    public void whenAddNewElemenetThenMapReturnThisElement() {
        SimpleMap<Integer, Integer> sm = new SimpleMap<>();
        sm.insert(-1, 1);
        sm.insert(2, 2);
        sm.insert(3, 3);
        sm.insert(4, 4);
        int result = sm.get(-1);
        assertThat(result, is(1));
    }

    /**
     * Test method delete.
     */
    @Test
    public void whenDeleteElementThenSizeDecrease() {
        SimpleMap<Integer, Integer> sm = new SimpleMap<>();
        sm.insert(-1, 1);
        sm.insert(2, 2);
        sm.insert(3, 3);
        sm.insert(4, 4);
        sm.delete(3);
        int result = sm.size();
        assertThat(result, is(3));
    }
}