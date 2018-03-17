package ru.job4j.collections.iterator;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 30.09.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class PrimeItTest {
    /**
     *Метод проверяет вывод всех простых чисел из массива.
     */
    @Test
    public void whenArrayHasFivePrimeElements() {
    PrimeIt it = new PrimeIt(new int[]{0, 1, 4, 6, 7, 5, 9, 13, 2});
        String result = "";
        while (it.hasNext()) {
            result += it.next() + ",";
        }
        String ex = "1,7,5,13,2,";
        assertThat(result, is(ex));
    }
    /**
     *Дёргаем next в ручную.
     */
    @Test
    public void whenArrayHasOneElementPrime() {
        PrimeIt it = new PrimeIt(new int[]{0, 1, 4, 6});
        int i = (int) it.next();
        boolean result = it.hasNext();
        assertThat(i, is(1));
        assertThat(result, is(false));
    }

    /**
     *Метод проверяет получаемые исключения.
     */
    @Test(expected = NoSuchElementException.class)
    public void whenArrayHasOneElementPrimeThenWasCalledNextTwoTimes() {
        PrimeIt it = new PrimeIt(new int[]{0, 1, 4, 6});
        int i = (int) it.next();
        int j = (int) it.next();
    }
}