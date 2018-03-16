package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {
    SimpleArray<Integer> simpleArray;

    @Before
    public void setUp() {
        simpleArray = new SimpleArray<>(1);
    }

    @Test
    public void whenAddObjectThenGetAddedObject() {
        simpleArray.add(5);
        assertThat(simpleArray.get(0), is(5));
    }

    @Test
    public void whenUpdateObjectThenObjectUpdated() {
        simpleArray.update(4, 0);
        assertThat(simpleArray.get(0), is(4));
    }

    @Test(expected = NullPointerException.class)
    public void whenDeleteObjectThenObjectIsNull() {
        simpleArray.delete(0);
        assertThat(simpleArray.get(0), null);
    }
}