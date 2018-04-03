package com.d4dl.hellofib.fibonacci;

import java.math.BigInteger;

/**
 * Specification for how to find a number in the
 * <a target="_blank"  href="https://en.wikipedia.org/wiki/Fibonacci_number">Fibonacci</a> sequence
 * using various parameters.
 * To summarize, each iteration of a fibonacci series after the first two (each of which produces a 1)
 * is the sum of the previous two values.
 */
public interface FibonacciFinder {

    /**
     * For a given iteration, find the number at that index in the fibonacci sequence
     * @param iterationCount the index at which the fibonacci number is desired
     * @return the number at the iterationCount for the number
     */
    BigInteger findIntegerAtIteration(int iterationCount);
}
