package ru.job4j.twoWords;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConverterTest {
    @Test
    public void whenFirstMethodComparesShouldBeTrue() {
        Converter converter = new Converter("word", "word");
        assertThat(converter.compareFirstMethod(), is(true));
    }

    @Test
    public void whenFirstMethodComparesShouldBeFalse() {
        Converter converter = new Converter("word", "different");
        assertThat(converter.compareFirstMethod(), is(false));
    }

    @Test
    public void whenSecondMethodComparesShouldBeTrue() {
        Converter converter = new Converter("word", "word");
        assertThat(converter.compareSecondMethod(), is(true));
    }

    @Test
    public void whenSecondMethodComparesShouldBeFalse() {
        Converter converter = new Converter("word", "different");
        assertThat(converter.compareSecondMethod(), is(false));
    }

    @Test
    public void whenThirdMethodComparesShouldBeTrue() {
        Converter converter = new Converter("word", "word");
        assertThat(converter.compareThirdMethod(), is(true));
    }

    @Test
    public void whenThirdMethodComparesShouldBeFalse() {
        Converter converter = new Converter("word", "different");
        assertThat(converter.compareThirdMethod(), is(false));
    }
}