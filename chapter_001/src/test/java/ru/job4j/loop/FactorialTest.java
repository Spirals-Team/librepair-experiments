package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FactorialTest {
    Factorial fa = new Factorial();

    @Test
    public void honorableCheckingFactorialFive() {

        int result = fa.calc(5);
        assertThat(result, is(120));
    }
    @Test
    public void honorableCheckingFactorialZero() {

        int result = fa.calc(0);
        assertThat(result, is(1));
    }
}
