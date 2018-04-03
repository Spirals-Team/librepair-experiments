package com.d4dl.hellofib.unit;

import com.d4dl.hellofib.fibonacci.*;
import org.junit.Test;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class FibonacciSeriesGeneratorTest {
    NumberFormat formatter = new DecimalFormat("0.###################E0", DecimalFormatSymbols.getInstance(Locale.ROOT));


    /**
     * Make sure the factory returns the correct instances
     */
    @Test
    public void whenTimeComplexityIsUsedCorrectFinderIsReturned() {
        FibonacciFinderFactory finder = new FibonacciFinderFactory(null);
        assertThat(finder.getFibonacciFinder()).isInstanceOf(FibonacciExponential.class);

        finder = new FibonacciFinderFactory(FibonacciFinderFactory.TimeComplexity.EXPONENTIAL);
        assertThat(finder.getFibonacciFinder()).isInstanceOf(FibonacciExponential.class);

        finder = new FibonacciFinderFactory(FibonacciFinderFactory.TimeComplexity.LINEAR);
        assertThat(finder.getFibonacciFinder()).isInstanceOf(FibonacciLinear.class);

        finder = new FibonacciFinderFactory(FibonacciFinderFactory.TimeComplexity.LOGARITHMIC);
        assertThat(finder.getFibonacciFinder()).isInstanceOf(FibonacciLogarithmic.class);
    }

}
