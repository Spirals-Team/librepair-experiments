package ru.aserdyuchenko.iterator.task5_1_2;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @author anton.
 */
public class IteratorForEvenNumbersTest {
    /**
     * Проверка метода next().
     */
    @Test
    public void whenGetNextEvenNumber() {
        IteratorForEvenNumbers it = new IteratorForEvenNumbers(new int[] {1, 2, 3, 4});
        it.next();
        int result = it.next();
        assertThat(result, is(4));
    }

    /**
     * Проверка метода hasNext().
     */
    @Test
    public void whenChekNextEvenNumber() {
        IteratorForEvenNumbers it = new IteratorForEvenNumbers(new int[] {1, 2, 3, 4});
        it.next();
        it.hasNext();
        boolean result = it.hasNext();
        assertThat(result, is(true));
    }
}