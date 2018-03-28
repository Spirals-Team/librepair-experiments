package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CounterTest {
    @Test
    public void fromOneToTen() {
        Counter counter = new Counter();
        int result = counter.add(1, 10);
        assertThat(result, is(30));
    }
    @Test
    public void fromFiveToEight() {
        Counter counter = new Counter();
        int result = counter.add(5, 8);
        assertThat(result, is(14));
    }
    @Test
    public void fromElevenToSixteen() {
        Counter counter = new Counter();
        int result = counter.add(11, 16);
        assertThat(result, is(42));
    }
}
