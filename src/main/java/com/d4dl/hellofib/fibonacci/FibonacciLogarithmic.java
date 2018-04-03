package com.d4dl.hellofib.fibonacci;

import java.math.BigInteger;

/**
 * This class finds the nth iteration in the Fibonacci sequence using an O(logN) time complexity algorithm.
 * This implementation is an adaptation of code found
 * <a target="_blank" href="https://www.geeksforgeeks.org/program-for-nth-fibonacci-number/">here</a>
 */
public class FibonacciLogarithmic implements FibonacciFinder {

    /**
     * Default constructor
     */
    public FibonacciLogarithmic() {
    }

    /**
     * find the nth iteration in the Fibonacci sequence using an O(logN) time complexity algorithm.
     * Each iteration stores the previous two values in variables for use during future iterations.
     * @param iterationCount the index at which the fibonacci number is desired
     * @return  The Fibonacci number in the sequence at <code>iterationCount</code>
     */
    @Override
    public BigInteger findIntegerAtIteration(int iterationCount) {
        BigInteger F[][] = new BigInteger[][]{{BigInteger.ONE, BigInteger.ONE}, {BigInteger.ONE, BigInteger.ZERO}};
        if (iterationCount == 0)
            return BigInteger.ZERO;

        raiseMatrixToPower(F, iterationCount - 1);

        return F[0][0];
    }

    /**
     * Helper function that multiplies 2 matrices F and M of size 2*2, and
     * puts the multiplication result back to multiplier[][]
     * @param multiplier a 2*2 matrix to multiply with the multiplicand inplace.  This will contain the result.
     * @param multiplicand another 2x2 matrix to multiplier by.
     **/
    private void multiplyMatrices(BigInteger multiplier[][], BigInteger multiplicand[][]) {
        BigInteger x = multiplier[0][0].multiply(multiplicand[0][0]).add(multiplier[0][1].multiply(multiplicand[1][0]));
        BigInteger y = multiplier[0][0].multiply(multiplicand[0][1]).add(multiplier[0][1].multiply(multiplicand[1][1]));
        BigInteger z = multiplier[1][0].multiply(multiplicand[0][0]).add(multiplier[1][1].multiply(multiplicand[1][0]));
        BigInteger w = multiplier[1][0].multiply(multiplicand[0][1]).add(multiplier[1][1].multiply(multiplicand[1][1]));

        multiplier[0][0] = x;
        multiplier[0][1] = y;
        multiplier[1][0] = z;
        multiplier[1][1] = w;
    }

    /**
     * Helper function that calculates F[][] raise to the power iterationCount and puts the
     * result in F[][]
     * Note that this function is designed only for fib() and won't work as general
     * power function
     * @param baseMatrix the matrix that will be raised to the specified exponent in place.
     * @param exponent the power to raise the matrix too.  This is not a general function. This will be a no-op
     *                 if the exponent is 0 or 1.
     */
    public void raiseMatrixToPower(BigInteger baseMatrix[][], int exponent) {
        if( exponent == 0 || exponent == 1)
            return;
        BigInteger M[][] = new BigInteger[][]{{BigInteger.ONE, BigInteger.ONE}, {BigInteger.ONE, BigInteger.ZERO}};

        raiseMatrixToPower(baseMatrix, exponent/2);
        multiplyMatrices(baseMatrix, baseMatrix);

        if (exponent%2 != 0) {
            multiplyMatrices(baseMatrix, M);
        }
    }

}
