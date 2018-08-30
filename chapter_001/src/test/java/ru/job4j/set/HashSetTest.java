package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class HashSetTest {

    HashSet<Integer> hs;

    @Before
    public void setUp() {
        hs = new HashSet();
        hs.add(3);
        hs.add(4);
        hs.add(5);
        hs.add(6);
        hs.add(7);
    }

    @Test
    public void givenEmptyHashSetWhenUseContainsThanFalse() {
        hs = new HashSet();
        assertThat(hs.contains(3), is(false));
    }

    @Test
    public void givenHashSetWhenAddDuplicateThenItNotPresentInSet() {
        hs = new HashSet();
        assertThat(hs.contains(3), is(false));
        assertThat(hs.add(3), is(true));
        assertThat(hs.contains(3), is(true));
        assertThat(hs.toString(), is("[null, {3}]"));
        hs.add(4);
        hs.add(5);
        hs.add(6);
        hs.add(7);
        assertThat(hs.contains(7), is(true));
        hs.add(6);
        assertThat(hs.toString(), is("[{46}, {357}]"));
    }

    @Test
    public void givenHashSetWhenResizeItThenAllValuesTransferredToBiggerHashSet() {
        hs.resize();
        assertThat(hs.contains(7), is(true));
        hs.add(6);
        assertThat(hs.toString(), is("[{4}, {5}, {6}, {37}]"));
        hs.add(1);
        assertThat(hs.toString(), is("[{4}, {51}, {6}, {37}]"));
        assertThat(hs.remove(5), is(true));
        assertThat(hs.toString(), is("[{4}, {1}, {6}, {37}]"));
        hs.remove(1);
        assertThat(hs.toString(), is("[{4}, {}, {6}, {37}]"));
    }
}