package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class CounterTest {
    @Test
    public  void  count() {
        Counter counter = new Counter();
        int result = counter.add(15, 20);
        assertThat(result, is(54));
    }
}
