package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FactorialTest {
    @Test
    public void Five() {
        Factorial factorial = new Factorial();
        int resukt = factorial.calc(5);
        assertThat(resukt, is(120));
    }

    @Test
    public void Six() {
        Factorial factorial = new Factorial();
        int resukt = factorial.calc(6);
        assertThat(resukt, is(720));
    }

    @Test
    public void Zero() {
        Factorial factorial = new Factorial();
        int resukt = factorial.calc(0);
        assertThat(resukt, is(1));
    }
}
