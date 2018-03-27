package ru.job4j.loop;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Andrei Hincu (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class FactorialTest {
    /**
     * Test calc 5.
     */
    @Test
    public void whenCalculateFactorialForFiveThenOneHundreedTwenty() {
        Factorial factorial = new Factorial();
        int result = factorial.calc(5);
        int ex = 120;
        assertThat(result, is(ex));
    }

    /**
     * Test calc 0.
     */
    @Test
    public void whenCalculateFactorialForZeroThenOne() {
        Factorial factorial = new Factorial();
        int result = factorial.calc(0);
        int ex = 1;
        assertThat(result, is(ex));
    }
}
