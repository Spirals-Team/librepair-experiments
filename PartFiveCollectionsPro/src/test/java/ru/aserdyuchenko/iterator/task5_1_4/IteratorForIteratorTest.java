package ru.aserdyuchenko.iterator.task5_1_4;

import org.junit.Test;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * @author anton.
 */
public class IteratorForIteratorTest {
    /**
     * @param iteratorForIterator - main iterator.
     */
    private IteratorForIterator iteratorForIterator = new IteratorForIterator();
    /**
     * @param firstList - list with integers.
     */
    private List<Integer> firstList = Arrays.asList(4);
    /**
     * @param firstIterator - iterator for firstList.
     */
    private Iterator<Integer> firstIterator = firstList.iterator();
    /**
     * @param secondList - list with integers.
     */
    private List<Integer> secondList = Arrays.asList(0);
    /**
     * @param secondIterator - iterator for secondList.
     */
    private Iterator<Integer> secondIterator = secondList.iterator();
    /**
     * @param listIterator - list with iterators.
     */
    private List<Iterator<Integer>> listIterator = Arrays.asList(firstIterator, secondIterator);
    /**
     * @param it - iterator for listIterator.
     */
    private Iterator<Iterator<Integer>> it = listIterator.iterator();

    /**
     * Test method next().
     */
    @Test
    public void whenGetSecondIntInIteratorForIterator() {
        Iterator<Integer> iterator = iteratorForIterator.convert(it);
        iterator.next();
        int result = iterator.next();

        assertThat(result, is(0));
    }

    /**
     * Test method hasNext().
     */
    @Test
    public void whenCheckSecondIntInIteratorForIterator() {
        Iterator<Integer> iterator = iteratorForIterator.convert(it);
        iterator.next();
        boolean result = iterator.hasNext();

        assertThat(result, is(true));
    }
}