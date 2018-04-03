package com.d4dl.hellofib.fibonacci;

import java.math.BigInteger;

/**
 * This class finds the nth iteration in the Fibonacci sequence using a linear time complexity algorithm.
 */
public class FibonacciLinear implements FibonacciFinder {

    /**
     * Default constructor
     */
    public FibonacciLinear() {
    }

    /**
     * find the nth iteration in the Fibonacci sequence using a linear time complexity algorithm.
     * Each iteration stores the previous two values in variables for use during future iterations.
     * @param iterationCount the index at which the fibonacci number is desired
     * @return  The Fibonacci number in the sequence at <code>iterationCount</code>
     */
    @Override
    public BigInteger findIntegerAtIteration(int iterationCount) {
        if (iterationCount <= 1) {
            return new BigInteger(Integer.toString(iterationCount));
        } else if (iterationCount == 2) {
            return BigInteger.ONE;
        } else {
            BigInteger earliest = BigInteger.ONE;
            BigInteger previous = BigInteger.ONE;
            return findIntegerInSequence(iterationCount, earliest, previous);
        }
    }

    private BigInteger findIntegerInSequence(int iterationCount, BigInteger earlierInteger, BigInteger previousInteger) {
        BigInteger sum = BigInteger.ZERO;//Initialization doesn't matter here.  But required.
        for (int i=2; i < iterationCount; i++) {
            sum = earlierInteger.add(previousInteger);
            earlierInteger = previousInteger;
            previousInteger = sum;
        }
        return sum;
    }
}
