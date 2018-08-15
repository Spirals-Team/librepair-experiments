package ru.job4j.controltask.extra;

import org.junit.Test;
import java.math.BigInteger;
import java.util.concurrent.ExecutionException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Class FactorialTest.
 */
public class FactorialTest {
    /**
     * Test №1.
     * @throws ExecutionException exception
     * @throws InterruptedException exception
     */
    @Test
    public void whenCalculateFactorialFrom7ThenResultIs120() throws ExecutionException, InterruptedException {
        Factorial f = new Factorial();
        f.calculateFact(5);
        BigInteger result = f.getResult();
        BigInteger expect = BigInteger.valueOf(120);
        assertThat(result, is(expect));
    }

    /**
     * Test №2.
     * @throws ExecutionException exception
     * @throws InterruptedException exception
     */
    @Test
    public void whenCalculateFactorialFrom10ThenResultIs5040() throws ExecutionException, InterruptedException {
        Factorial f = new Factorial();
        f.calculateFact(7);
        BigInteger result = f.getResult();
        BigInteger expect = BigInteger.valueOf(5040);
        assertThat(result, is(expect));
    }

    /**
     * Test №3.
     * @throws ExecutionException exception
     * @throws InterruptedException exception
     */
    @Test
    public void whenCalculateFactorialFrom10ThenResultIs3628800() throws ExecutionException, InterruptedException {
        Factorial f = new Factorial();
        f.calculateFact(10);
        BigInteger result = f.getResult();
        BigInteger expect = BigInteger.valueOf(3628800);
        assertThat(result, is(expect));
    }

}