package com.d4dl.hellofib.fibonacci;

import java.math.BigInteger;

/**
 * This is a utility class that selects an algorithm to calculate the value at the nth iteration of
 * the Fibonacci sequence.
 */
public class FibonacciFinderFactory implements FibonacciFinder {

    /**
     * This is the desired time complexity that is used to determine which algorithm to use.
     */
    private TimeComplexity complexity;

    /**
     * This is used to specify the desired time complexity of the algorithm used to calculate Fibonacci numbers.
     */
    public enum TimeComplexity {
        /**
         * Used to specify that the recursive, exponentially complex ({@link FibonacciExponential}) algorithm should be used.
         */
        EXPONENTIAL,
        /**
         * Used to specify that the an algorithm of linear complexity ({@link FibonacciLinear}) should be used.
         */
        LINEAR,
        /**
         * Used to specify that the an algorithm of logarithmic ({@link FibonacciLogarithmic}) complexity should be used.
         */
        LOGARITHMIC
    }


    /**
     * Default constructor
     */
    public FibonacciFinderFactory() {
    }

    /**
     * Convenience constructor to create the generator that will use a specific algorithm
     * @param complexity the complexity of the desired algorithm to use to determine fibonacci numbers.  If
     *                   null is specified this defaults to {@link TimeComplexity#EXPONENTIAL} (the worst one)
     */
    public FibonacciFinderFactory(TimeComplexity complexity) {
        this.complexity = complexity == null ? TimeComplexity.EXPONENTIAL : complexity;
    }


    /**
     * find the nth iteration in the Fibonacci sequence using an algorthm determined by the complexity
     * set in the constructor.
     * @param iterationCount the index at which the fibonacci number is desired
     * @return  The Fibonacci number in the sequence at <code>iterationCount</code>
     */
    @Override
    public BigInteger findIntegerAtIteration(int iterationCount) {
        FibonacciFinder fibonacciFinder = getFibonacciFinder();
        return fibonacciFinder.findIntegerAtIteration(iterationCount);
    }

    /**
     * This just switches on the complexity to return the corresponding FibonacciFinder
     * @return a new instance of a FibonacciFinder that will calculate Fibonacci numbers with the given complexity.
     */
    public FibonacciFinder getFibonacciFinder() {
        FibonacciFinder fibonacciFinder;
        switch(complexity) {
            case EXPONENTIAL: fibonacciFinder = new FibonacciExponential();
                break;
            case LINEAR: fibonacciFinder = new FibonacciLinear();
                break;
            case LOGARITHMIC: fibonacciFinder = new FibonacciLogarithmic();
                break;
            default: fibonacciFinder = new FibonacciExponential();
        }
        return fibonacciFinder;
    }

    /**
     * Retreives the value of the time complexity parameter for this generated
     * @return the value of the time complexity parameter used to determine which algorithm use
     * to generate Fibonacci numbers.
     */
    public TimeComplexity getComplexity() {
        return this.complexity;
    }
}
