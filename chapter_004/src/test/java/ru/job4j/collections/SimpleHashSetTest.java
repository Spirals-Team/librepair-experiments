package ru.job4j.collections;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleHashSetTest {

   SimpleHashSet<Integer> array = new SimpleHashSet<>();

    @Test
    public void whetAddElementInHashTableThenDoubleDontAdd() {
        array.add(7);
        array.add(5);
        array.add(5);
        array.add(5);
        array.add(new Integer(4));
        array.add(3);
        array.add(15);
        assertThat(array.contains(15), is(true));
        array.remove(15);
        assertThat(array.contains(15), is(false));
        assertThat(array.size, is(4));
    }
}