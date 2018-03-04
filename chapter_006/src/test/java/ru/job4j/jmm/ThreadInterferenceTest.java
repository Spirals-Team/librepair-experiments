package ru.job4j.jmm;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ThreadInterferenceTest {
    @Test
    public void whenRunsSomeThreadsThenValueNotEqualsZero() throws InterruptedException {
        ThreadInterference example = new ThreadInterference(new Counter());
        example.execute();
        assertThat(example.getCounter().value() != 0, is(true));
    }
}