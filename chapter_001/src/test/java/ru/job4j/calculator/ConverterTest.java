package ru.job4j.calculator;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ConverterTest {
    @Test
    public void when60RubleToDollarThen1() {
        Converter converter = new Converter();
        double result = converter.rubleToDollar(60);
        assertThat(result, is(1D));
    }

    @Test
    public void when70RubleToEuroThen1() {
        Converter converter = new Converter();
        double result = converter.rubleToEuro(70);
        assertThat(result, is(1D));
    }

    @Test
    public void when70EuroToRuble() {
        Converter converter = new Converter();
        double result = converter.euroToRuble(70);
        assertThat(result, is(4900D));
    }

    @Test
    public void when60DollarsToRuble() {
        Converter converter = new Converter();
        double result = converter.dollarToRuble(60);
        assertThat(result, is(3600D));
    }
}