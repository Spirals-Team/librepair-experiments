package ru.job4j.coffeemachine;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class AutomatTest {

    @Test(expected = NotEnoughMoney.class)
    public void whenSmallMoney() {
        Automat automat = new Automat();
        automat.changes(30, 35);
    }

    @Test
    public void whenBalanceZero() {
        Automat automat = new Automat();
        int[] test = automat.changes(35, 35);
        int[] expect = {0};
        assertThat(test, is(expect));
    }

    @Test
    public void whenNeedChangesTenAndFive() {
        Automat automat = new Automat();
        int[] test = automat.changes(50, 35);
        int[] expect = {10, 5};
        assertThat(test, is(expect));
    }

    @Test
    public void whenNeedChangesTwoAndOne() {
        Automat automat = new Automat();
        int[] test = automat.changes(50, 47);
        int[] expect = {2, 1};
        assertThat(test, is(expect));
    }

    @Test
    public void whenNeedMenyChanges() {
        Automat automat = new Automat();
        int[] test = automat.changes(100, 61);
        int[] expect = {10, 10, 10, 5, 2, 2};
        assertThat(test, is(expect));
    }
}