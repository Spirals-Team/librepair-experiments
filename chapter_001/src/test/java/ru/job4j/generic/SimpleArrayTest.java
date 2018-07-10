package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class SimpleArrayTest {

    private SimpleArray arr;

    @Before
    public void setUp() {
        arr = new SimpleArray(3);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenIntArrayWhenUseCrudOperationsThenOk() {
        arr.add(3);
        assertThat(arr.get(0), is(3));
        arr.add(2);
        assertThat(arr.get(1), is(2));
        arr.add(1);
        assertThat(arr.get(2), is(1));
        arr.update(0, 5);
        assertThat(arr.get(0), is(5));
        arr.delete(0);
        assertThat(arr.get(0), is(2));
        assertThat(arr.get(1), is(1));
        assertThat(arr.get(2), is(nullValue()));
        arr.delete(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void givenStringArrayWhenUseCrudOperationsThenOk() {
        int[] a = new int[4];
        int g = a.length;
        arr.add("3");
        assertThat(arr.get(0), is("3"));
        arr.add("2");
        assertThat(arr.get(1), is("2"));
        arr.add("1");
        assertThat(arr.get(2), is("1"));
        arr.update(0, "5");
        assertThat(arr.get(0), is("5"));
        arr.delete(0);
        assertThat(arr.get(0), is("2"));
        assertThat(arr.get(1), is("1"));
        assertThat(arr.get(2), is(nullValue()));
        arr.get(7);
    }
}