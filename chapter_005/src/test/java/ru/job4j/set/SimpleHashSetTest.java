package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleHashSetTest {
    //Add and check contains specific element in list
    @Test
    public void whenAddElementThenSetContainSpecifiedElement() {
        SimpleHashSet<Integer> hashSet = new SimpleHashSet<>(16);
        hashSet.add(100);
        assertThat(hashSet.contains(100), is(true));
    }
    //Remove element
    @Test
    public void whenRemoveElementThenSetNotContainSpecifiedElement() {
        SimpleHashSet<Integer> hashSet = new SimpleHashSet<>(16);
        hashSet.add(100);
        hashSet.remove(100);
        assertThat(hashSet.contains(100), is(false));
    }
}