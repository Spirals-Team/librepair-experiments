package ru.job4j.controltask.extra;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Class Factorial.
 */
public class Factorial {
    /**
     * @param result result
     */
    private BigInteger result = BigInteger.valueOf(1);

    /**
     * Method for calculating factorial.
     * @param fact factorial
     * @throws ExecutionException exception
     * @throws InterruptedException exception
     */
    public void calculateFact(int fact) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<BigInteger>> allResult = new ArrayList<>();
        Future<BigInteger> temp;
        for (int i = 1; i <= fact; i = i + 2) {
            int next = i + 1;
            if (i < fact) {
                temp = es.submit(new Multiply(i, next));
            } else {
                temp = es.submit(new Multiply(fact, 1));
            }
            allResult.add(temp);
        }

        es.shutdown();

        for (Future<BigInteger> f : allResult) {
            System.out.println(f);
            result = result.multiply(f.get());
        }
    }

    /**
     * Getter for result.
     * @return result
     */
    public BigInteger getResult() {
        return result;
    }

    /**
     * Inner class Multipay.
     */
    class Multiply implements Callable<BigInteger> {
        /**
         * @param first first value for multiply
         */
        private int first;
        /**
         * @param second second value for multiply
         */
        private int second;

        /**
         * Constructor.
         * @param first first value for multiply
         * @param second second value for multiply
         */
        public Multiply(int first, int second) {
            this.first = first;
            this.second = second;
        }

        /**
         * Method call.
         * @return result of the multiply
         */
        @Override
        public BigInteger call() {
            BigInteger result = BigInteger.valueOf(first * second);
            return result;
        }
    }
}
