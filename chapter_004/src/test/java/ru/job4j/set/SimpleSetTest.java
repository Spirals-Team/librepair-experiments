package ru.job4j.set;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleSetTest {
    @Test
    public void addTest() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(150);
        set.add(78);
        set.add(11);
        set.add(11);
        assertThat(set.toArray(), is(new Integer[] {150, 78, 11}));
    }

    @Test(expected = NoSuchElementException.class)
    public void iteratorTest() {
        SimpleSet<Integer> set = new SimpleSet<>();
        set.add(150);
        set.add(78);
        set.add(11);
        set.add(11);

        assertThat(set.hasNext(), is(true));
        assertThat(set.next(), is(150));
        assertThat(set.hasNext(), is(true));
        assertThat(set.next(), is(78));
        assertThat(set.hasNext(), is(true));
        assertThat(set.next(), is(11));
        set.next();
    }
}