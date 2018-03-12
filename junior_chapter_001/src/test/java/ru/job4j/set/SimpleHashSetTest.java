package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleHashSetTest {
    private SimpleHashSet<String> shs;

    @Before
    public void setUp() {
        shs = new SimpleHashSet<>();
    }

    @Test
    public void testAddElements() throws Exception {
        assertThat(shs.add("one"), is(true));
        assertThat(shs.add("two"), is(true));
        assertThat(shs.add("tree"), is(true));
        assertThat(shs.add("tree"), is(false));
        assertThat(shs.add("four"), is(true));
        assertThat(shs.add("five"), is(true));
    }

    @Test
    public void testContainsElements() throws Exception {
        assertThat(shs.add("one"), is(true));
        assertThat(shs.add("two"), is(true));
        assertThat(shs.add("tree"), is(true));
        assertThat(shs.contains("one"), is(true));
        assertThat(shs.contains("two"), is(true));
        assertThat(shs.contains("tree"), is(true));
        assertThat(shs.contains("four"), is(false));
    }

    @Test
    public void testRemoveElements() throws Exception {
        assertThat(shs.add("one"), is(true));
        assertThat(shs.add("two"), is(true));
        assertThat(shs.add("tree"), is(true));
        assertThat(shs.add("four"), is(true));
        assertThat(shs.add("five"), is(true));
        assertThat(shs.remove("one"), is(true));
        assertThat(shs.contains("one"), is(false));
        assertThat(shs.remove("four"), is(true));
        assertThat(shs.contains("four"), is(false));
    }

}