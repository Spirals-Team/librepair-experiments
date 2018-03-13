package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleHashSetTest {

    @Test
    public void whenAddToSimpleHashSetThenYouCanGetThisElement() {
        SimpleHashSet simpleHashSet = new SimpleHashSet();
        simpleHashSet.add(1);
        simpleHashSet.add(2);
        simpleHashSet.add(2);
        simpleHashSet.add(3);

        boolean result = simpleHashSet.contains(2);
        boolean expected = true;

        assertThat(result, is(expected));

    }

    @Test
    public void whenRemoveFromSimpleHashSetThenYouCanNotGetThisElement() {

        SimpleHashSet simpleHashSet = new SimpleHashSet();
        simpleHashSet.add(1);
        simpleHashSet.add(2);
        simpleHashSet.add(3);

        boolean result = simpleHashSet.remove(2);
        boolean expected = true;

        assertThat(result, is(expected));

        result = simpleHashSet.contains(2);
        expected = false;

        assertThat(result, is(expected));
    }




}
