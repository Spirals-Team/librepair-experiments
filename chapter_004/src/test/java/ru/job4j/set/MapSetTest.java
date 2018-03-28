package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MapSetTest {
    @Test
    public void addAndRemoveAndContainsTest() {
        MapSet<String> set = new MapSet<>();
        set.add("one");
        set.add("two");
        set.add("two");
        set.add("three");
        assertThat(set.get("one"), is("one"));
        assertThat(set.get("two"), is("two"));
        assertThat(set.get("three"), is("three"));
        assertThat(set.contains("one"), is(true));
        assertThat(set.contains("two"), is(true));
        assertThat(set.contains("three"), is(true));
        assertThat(set.contains("four"), is(false));
        set.remove("three");
        assertThat(set.contains("three"), is(false));
    }

    @Test
    public void whenSetIsFullSetShouldExpand() {
        MapSet<String> set = new MapSet<>();
        for (int i = 0; i < 16; i++) {
            set.add(Integer.toString(i));
        }
        set.add("16");
        set.add("17");
        for (int i = 1; i < 18; i++) {
            assertThat(set.get(Integer.toString(i)), is(Integer.toString(i)));
        }
    }
}