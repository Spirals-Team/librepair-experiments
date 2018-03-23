package ru.job4j.jmm;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MemoryConsistencyErrorTest {
    @Test
    public void whenRunsSomeThreadsThenCountNotHundredMillion() throws InterruptedException {
        MemoryConsistencyError example = new MemoryConsistencyError();
        example.execute();
        assertThat(MemoryConsistencyError.getCounter() != 100_000_000, is(true));
    }
}