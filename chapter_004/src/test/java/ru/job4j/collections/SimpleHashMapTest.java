package ru.job4j.collections;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
public class SimpleHashMapTest {

    SimpleHashMap<Integer, Integer> array = new SimpleHashMap<>();

    @Test
    public void whetAddElementInSimplaHashMapThenDoubleDontInsert() {
        assertThat(array.insert(1, 1), is(true));
        assertThat(array.insert(1, 2), is(false));
        assertThat(array.insert(2, 2), is(true));
        assertThat(array.insert(3, 3), is(true));
        assertThat(array.insert(5, 5), is(true));
        assertThat(array.get(5), is(5));
        assertThat(array.get(1), is(1));
        assertThat(array.delete(1), is(true));
        assertThat(array.get(1), is((Integer) null));
        assertThat(array.size, is(3));
    }
}