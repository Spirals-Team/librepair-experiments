package ru.job4j.list;

import org.junit.Test;
import java.util.NoSuchElementException;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleStackTest {
    @Test(expected = NoSuchElementException.class)
    public void whenTestStackPushAndPullElements() {
        SimpleStack<Integer> simpleStack = new SimpleStack<>();
        simpleStack.push(100);
        simpleStack.push(77);
        simpleStack.push(999);
        assertThat(simpleStack.getSize(), is(3));
        assertThat(simpleStack.pull(), is(999));
        assertThat(simpleStack.getSize(), is(2));
        assertThat(simpleStack.pull(), is(77));
        assertThat(simpleStack.getSize(), is(1));
        assertThat(simpleStack.pull(), is(100));
        assertThat(simpleStack.getSize(), is(0));
        simpleStack.pull();
    }
}