package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleSetBasedArrayTest {

    //Add unique element
    @Test
    public void whenAddDuplicateThenListHasNoIt() {
        SimpleSetBasedArray<Integer> set = new SimpleSetBasedArray<>();
        set.add(1);
        set.add(2);
        set.add(1);
        Iterator<Integer> iter = set.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(1));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(2));
        assertThat(iter.hasNext(), is(false));
    }
}