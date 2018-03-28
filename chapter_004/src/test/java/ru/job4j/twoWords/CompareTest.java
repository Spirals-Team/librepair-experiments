package ru.job4j.twoWords;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CompareTest {
    @Test
    public void whenFirstMethodShouldBeTrue() {
         Compare compare = new Compare("onne", "oenn");
         assertThat(compare.compareFirst(), is(true));
    }

    @Test
    public void whenFirstMethodShouldBeFalse() {
        Compare compare = new Compare("onne", "omnn");
        assertThat(compare.compareFirst(), is(false));
    }

    @Test
    public void whenSecondMethodShouldBeTrue() {
        Compare compare = new Compare("onne", "oenn");
        assertThat(compare.compareSecond(), is(true));
    }

    @Test
    public void whenSecondMethodShouldBeFalse() {
        Compare compare = new Compare("onne", "ommn");
        assertThat(compare.compareSecond(), is(false));
    }

    @Test
    public void whenThirdMethodShouldBeTrue() {
        Compare compare = new Compare("onne", "oenn");
        assertThat(compare.compareThird(), is(true));
    }

    @Test
    public void whenThirdMethodShouldBeFalse() {
        Compare compare = new Compare("onme", "ommn");
        assertThat(compare.compareThird(), is(false));
    }
}