package ru.job4j.extratask;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimileTest {
    @Test
    public void whenTestFirstMetod() {
        Simile simile = new Simile();
        assertThat(simile.testOne("mama", "amam"), is(true));
        assertThat(simile.testOne("mama", "amm"), is(false));
        assertThat(simile.testOne("mama", "ammaa"), is(false));
        assertThat(simile.testOne("mama", "amma"), is(true));
        assertThat(simile.testOne("mama", "aman"), is(false));
        assertThat(simile.testOne("mama", "amaa"), is(false));
    }

    @Test
    public void whenTestSecondMetod() {
        Simile simile = new Simile();
        assertThat(simile.testTwo("mama", "amam"), is(true));
        assertThat(simile.testTwo("mama", "amm"), is(false));
        assertThat(simile.testTwo("mama", "ammaa"), is(false));
        assertThat(simile.testTwo("mama", "amma"), is(true));
        assertThat(simile.testTwo("mama", "aman"), is(false));
        assertThat(simile.testTwo("mama", "amaa"), is(false));
    }

    @Test
    public void whenTestThreeMetod() {
        Simile simile = new Simile();
        assertThat(simile.testThree("mama", "amam"), is(true));
        assertThat(simile.testThree("mama", "amm"), is(false));
        assertThat(simile.testThree("mama", "ammaa"), is(false));
        assertThat(simile.testThree("mama", "amma"), is(true));
        assertThat(simile.testThree("mama", "aman"), is(false));
        assertThat(simile.testThree("mama", "amaa"), is(false));
    }
}