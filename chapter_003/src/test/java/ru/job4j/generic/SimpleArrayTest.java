package ru.job4j.generic;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleArrayTest {

    @Test
    public void addTest() {
        SimpleArray<Integer> simpleArray = new SimpleArray<>(10);
        simpleArray.add(20);
        assertThat(simpleArray.get(0), is(20));
    }

    @Test
    public void setTest() {
        SimpleArray<String> simpleArray = new SimpleArray<>(10);
        simpleArray.add("First");
        simpleArray.set(0, "Second");
        assertThat(simpleArray.get(0), is("Second"));
    }

    @Test
    public void deleteTest() {
        SimpleArray<String> simpleArray = new SimpleArray<>(10);
        simpleArray.add("First");
        simpleArray.add("Second");
        simpleArray.delete(0);
        assertThat(simpleArray.get(0), is("Second"));
    }

    @Test
    public void iteratorTest() {
        SimpleArray<String> simpleArray = new SimpleArray<>(10);
        simpleArray.add("First");
        simpleArray.add("Second");
        simpleArray.add("Third");
        Iterator<String> iterator = simpleArray.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("First"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("Second"));
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("Third"));
        assertThat(iterator.hasNext(), is(false));
    }

    @Test(expected = RuntimeException.class)
    public void addTestWhenOverflow() {
        SimpleArray<String> simpleArray = new SimpleArray<>(1);
        simpleArray.add("First");
        simpleArray.add("Second");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void setTestWhenIndexOutOfBoundsException() {
        SimpleArray<String> simpleArray = new SimpleArray<>(3);
        simpleArray.add("First");
        simpleArray.set(3, "Third");
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getTestArrayIndexOutOfBoundsException() {
        SimpleArray<String> simpleArray = new SimpleArray<>(1);
        simpleArray.add("First");
        simpleArray.get(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void deleteTestIndexOutOfBoundsException() {
        SimpleArray<String> simpleArray = new SimpleArray<>(1);
        simpleArray.add("First");
        simpleArray.delete(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTestNoSuchElementException() {
        SimpleArray<String> simpleArray = new SimpleArray<>(10);
        simpleArray.add("First");
        Iterator<String> iterator = simpleArray.iterator();
        assertThat(iterator.hasNext(), is(true));
        assertThat(iterator.next(), is("First"));
        assertThat(iterator.hasNext(), is(false));
        assertThat(iterator.next(), is("Second"));
    }
}