package ru.job4j.iterator;

import org.junit.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class IteratorConverterTest.
 */
public class IteratorConverterTest {
    /**
     * Three iterator.
     */
    @Test
    public void whenItHasThreeInnerIt() {
        Iterator<Integer> it1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4)).iterator();
        Iterator<Integer> it2 = new ArrayList<>(Arrays.asList(20, 21, 27, 8)).iterator();
        Iterator<Integer> it3 = new ArrayList<>(Arrays.asList(9, 10, 11, 12)).iterator();
        Iterator<Iterator<Integer>> it = Arrays.asList(it1, it2, it3).iterator();
        Iterator<Integer> convert = new IteratorConverter().convert(it);
        int result = 0;
        for (int i = 0; i < 6; i++) {
            result = convert.next();
        }
        assertThat(result, is(21));
    }

    /**
     * Two Iterator.
     */
    @Test
    public void whenItHasTwoInnerI() {
        Iterator<Iterator<Integer>> it = Arrays.asList(
                Collections.singletonList(1).iterator(),
                Collections.singletonList(2).iterator()
        ).iterator();
        Iterator<Integer> convert = new IteratorConverter().convert(it);
        convert.next();
        int result = convert.next();
        assertThat(result, is(2));
    }
}

