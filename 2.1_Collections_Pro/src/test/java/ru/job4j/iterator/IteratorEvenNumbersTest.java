package ru.job4j.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * Test IteratorEvenNumbersTest.
 * @author Igor Fedorenko (mailto:if.zommy@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class IteratorEvenNumbersTest {
    /**
     * Array have two even values.
     */
    @Test
    public void whenArrayHaveTwoEvenValuesThenSizeOfListIs2() {
        List<Integer> result = new ArrayList<>();
        Iterator iterator = new IteratorEvenNumbers(new int[] {2, 4, 1, 1});
        while (iterator.hasNext()) {
            result.add((Integer) iterator.next());
        }
        List<Integer> expect = Arrays.asList(2, 4);
        assertThat(result, is(expect));
    }

    /**
     * Array have three even values.
     */
    @Test
    public void whenArrayHaveThreeEvenValuesThenSizeOfListIs3() {
        List<Integer> result = new ArrayList<>();
        Iterator iterator = new IteratorEvenNumbers(new int[] {2, 3, 4, 1, 10});
        while (iterator.hasNext()) {
            result.add((Integer) iterator.next());
        }
        List<Integer> expect = Arrays.asList(2, 4, 10);
        assertThat(result, is(expect));
    }

    /**
     * Test work next without hasNext.
     */
    @Test
    public void whenFourthStepIsHasNextThemnReturnFalse() {
        Iterator iterator = new IteratorEvenNumbers(new int[] {2, 3, 4, 1, 10});
        iterator.next();
        iterator.next();
        iterator.next();
        boolean result = iterator.hasNext();
        assertThat(result, is(false));
    }
}