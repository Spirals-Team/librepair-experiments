package ru.job4j.search;

import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class ParallelSearchTest {

    @Test
    public void search() {
        ParallelSearch ps =
            new ParallelSearch("./", "ParallelSearch", Arrays.asList("java"));
        ps.init();
        assertThat(ps.result().size(), is(2));
    }
}