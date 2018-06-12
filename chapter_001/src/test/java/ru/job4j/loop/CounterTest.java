package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Yury Matskevich (y.n.matskevich@gmail.com)
 * @version $Id$
 */

public class CounterTest {
    @Test
    public void whenSumEvenNumbersFromOneToTenThenThirty() {
        Counter count = new Counter();
        assertThat(count.add(-2, 10), is(28));
    }
}