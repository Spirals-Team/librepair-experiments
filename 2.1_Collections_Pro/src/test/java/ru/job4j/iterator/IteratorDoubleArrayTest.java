package ru.job4j.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test IteratorDoubleArrayTest.
 * @author Igor Fedorenko (mailto:if.zommy@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class IteratorDoubleArrayTest {
    /**
     * Test array 2x2.
     */
    @Test
    public void whenIterateTwoRowTwoColArrayThenSizeOfListIs4() {
        List<Integer> result = new ArrayList<>();
        IteratorDoubleArray ida = new IteratorDoubleArray(new int[][]{{1, 2}, {3, 4}});
        while (ida.hasNext()) {
            result.add((Integer) ida.next());
        }
        List<Integer> expect = Arrays.asList(1, 2, 3, 4);
        assertThat(result, is(expect));
    }

    /**
     * Test array 2x3.
     */
    @Test
    public void whenIterateTwoRowThreeColArrayThenSizeOfListIs6() {
        List<Integer> result = new ArrayList<>();
        IteratorDoubleArray ida = new IteratorDoubleArray(new int[][]{{1, 2, 3}, {4, 5, 6}});
        while (ida.hasNext()) {
            result.add((Integer) ida.next());
        }
        List<Integer> expect = Arrays.asList(1, 2, 3, 4, 5, 6);
        assertThat(result, is(expect));
    }
}


