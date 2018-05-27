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
public class SimpleQueueTest {
    @Test(expected = NoSuchElementException.class)
    public void whenTestQueuePushAndPullElements() {
        SimpleQueue<Integer> simpleQueue = new SimpleQueue<>();
        simpleQueue.push(100);
        simpleQueue.push(77);
        simpleQueue.push(999);
        assertThat(simpleQueue.getSize(), is(3));
        assertThat(simpleQueue.pull(), is(100));
        assertThat(simpleQueue.getSize(), is(2));
        assertThat(simpleQueue.pull(), is(77));
        assertThat(simpleQueue.getSize(), is(1));
        assertThat(simpleQueue.pull(), is(999));
        assertThat(simpleQueue.getSize(), is(0));
        simpleQueue.pull();
    }
}