package ru.job4j.set;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashSetTest {
    SimpleHashSet<String> simpleHashSet = new SimpleHashSet<>();
    @Test
    public void whereTestSimpleHashSet() {
        simpleHashSet.add("Mike");
        assertThat(simpleHashSet.contains("Mike"), is(true));
        assertThat(simpleHashSet.add("Mike"), is(false));
        assertThat(simpleHashSet.getSize(), is(1));
        assertThat(simpleHashSet.getCapacityAllItems(), is(1));
        simpleHashSet.add("Test");
        simpleHashSet.add("Admin");
        assertThat(simpleHashSet.getSize(), is(3));
        assertThat(simpleHashSet.getCapacityAllItems(), is(3));
        assertThat(simpleHashSet.contains("Mike"), is(true));
        assertThat(simpleHashSet.contains("Test"), is(true));
        assertThat(simpleHashSet.contains("Admin"), is(true));
        assertThat(simpleHashSet.remove("Test"), is(true));
        assertThat(simpleHashSet.remove("Test"), is(false));
        assertThat(simpleHashSet.getSize(), is(2));
        assertThat(simpleHashSet.getCapacityAllItems(), is(2));
        assertThat(simpleHashSet.contains("Mike"), is(true));
        assertThat(simpleHashSet.contains("Test"), is(false));
        assertThat(simpleHashSet.contains("Admin"), is(true));
    }

}