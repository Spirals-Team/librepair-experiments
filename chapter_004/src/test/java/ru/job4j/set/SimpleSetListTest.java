package ru.job4j.set;

import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleSetListTest {
    @Test(expected = NoSuchElementException.class)
    public void whereTestSimpleSet() {
        SimpleSetList<String> simpleSet = new SimpleSetList<>();
        simpleSet.add("Test");
        simpleSet.add("Test");
        simpleSet.add("Mike");
        Iterator<String> iter = simpleSet.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is("Test"));
        assertThat(iter.next(), is("Mike"));
        assertThat(iter.hasNext(), is(false));
        iter.next();
    }
}