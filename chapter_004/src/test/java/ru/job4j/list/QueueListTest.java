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
public class QueueListTest {
    @Test(expected = NoSuchElementException.class)
    public void whenTestQueuePushAndPullElements() {
        QueueList<Integer> queue = new QueueList<>();
        queue.push(100);
        queue.push(77);
        queue.push(999);
        assertThat(queue.pull(), is(100));
        assertThat(queue.pull(), is(77));
        assertThat(queue.pull(), is(999));
        queue.pull();
    }
}