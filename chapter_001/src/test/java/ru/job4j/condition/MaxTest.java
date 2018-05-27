package ru.job4j.condition;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MaxTest {

    @Test
    public void whenFirstLessSecond() {
        Max maxim = new Max();
        int result = maxim.max(3, 7);
        assertThat(result, is(7));
    }

    @Test
    public void whenMaxFirstSecondOrThird() {
        Max maxim = new Max();
        int result = maxim.max(3, 7, 10);
        assertThat(result, is(10));
    }
}
