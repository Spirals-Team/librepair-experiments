package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {
    private SimpleArray<Integer> sa;

    @Before
    public void setUp() {
        sa = new SimpleArray<Integer>(10);
    }

    @Test
    public void addAndGetTest() throws Exception {
        sa.add(2);
        assertThat(sa.get(0), is(2));
    }

    @Test
    public void updateTest() throws Exception {
        sa.add(2);
        sa.update(0, 5);
        assertThat(sa.get(0), is(5));
    }

    @Test
    public void deleteTest() throws Exception {
        sa.add(2);
        sa.add(3);
        sa.delete(0);
        assertThat(sa.get(0), is(3));
    }

}