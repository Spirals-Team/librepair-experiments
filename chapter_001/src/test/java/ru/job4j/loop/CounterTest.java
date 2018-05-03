package ru.job4j.loop;
/**
 * @autor Alexander Kaleganov
 * @version CounterTest 1.000
 * @since 29.01.2018 23:44
 */
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CounterTest {
    Counter counter = new Counter();

    @Test
    public void honorableCheckingDoTen() {
        int result = counter.add(1, 10);
        assertThat(result, is(30));
    }
}
