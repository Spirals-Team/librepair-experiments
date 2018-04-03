package com.d4dl.hellofib.fibonacci;

import java.math.BigInteger;

/**
 * This class finds the nth iteration in the Fibonacci sequence using an the recursive algorithm which
 * has an exponential time complexity and O(n) space complexity due to the call stack size accumulation
 * during recursion.
 */
public class FibonacciExponential implements FibonacciFinder {

    /**
     * Default constructor
     */
    public FibonacciExponential() {
    }

    /**
     * find the nth iteration in the Fibonacci sequence using the recursive algorithm.
     * @param iterationCount the index at which the fibonacci number is desired
     * @return  The Fibonacci number in the sequence at <code>iterationCount</code>
     */
    @Override
    public BigInteger findIntegerAtIteration(int iterationCount) {
        if (iterationCount <= 1) {
            return new BigInteger(Integer.toString(iterationCount));
        }
        BigInteger a = findIntegerAtIteration(iterationCount - 1);
        BigInteger b = findIntegerAtIteration(iterationCount - 2);
        return a.add(b);
    }
}
