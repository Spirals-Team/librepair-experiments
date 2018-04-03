package com.d4dl.hellofib.unit;

import com.d4dl.hellofib.fibonacci.FibonacciLogarithmic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test ensuring the correctness of the recursive fibonacci algorithm
 */
@RunWith(SpringRunner.class)
public class FibonacciLogarithmicTests {

    /**
     * Make sure the special case values (0,1,1,1) are correct as well as a couple of mid stream values.
     */
    @Test
    public void whenValuesAreGivenCalculationAreCorrect() {
        FibonacciLogarithmic fibonacciFinder = new FibonacciLogarithmic();
        assertThat(fibonacciFinder.findIntegerAtIteration(0)).isEqualByComparingTo(BigInteger.ZERO);
        assertThat(fibonacciFinder.findIntegerAtIteration(1)).isEqualByComparingTo(BigInteger.ONE);
        assertThat(fibonacciFinder.findIntegerAtIteration(2)).isEqualByComparingTo(BigInteger.ONE);
        assertThat(fibonacciFinder.findIntegerAtIteration(10)).isEqualByComparingTo(BigInteger.valueOf(55));
        assertThat(fibonacciFinder.findIntegerAtIteration(13)).isEqualByComparingTo(BigInteger.valueOf(233));
    }
}
