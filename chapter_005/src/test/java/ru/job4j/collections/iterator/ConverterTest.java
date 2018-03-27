package ru.job4j.collections.iterator;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 04.10.17;
 * @version $Id$
 * @since 0.1
 */
public class ConverterTest {
    /**
     * Test.
     */
    @Test
    public void whenItHasTwoInnerIt() {
        Iterator<Iterator<Integer>> it = Arrays.asList(
                Collections.singletonList(1).iterator(),
                Collections.singletonList(2).iterator()
        ).iterator();
        Iterator<Integer> convert = new Converter().convert(it);
        convert.next();
        int result = convert.next();
        assertThat(result, is(2));
    }

    /**
     * Test.
     * @throws Exception ex.
     */
    @Test
    public void whenItHasTheeElements() throws Exception {
        Iterator<Integer> i1 = Arrays.asList(4).iterator();
        Iterator<Integer> i2 = Arrays.asList(0, 9, 8, 7, 5).iterator();
        Iterator<Integer> i3 = Arrays.asList(1, 3, 5, 6, 7, 0, 9, 8, 4).iterator();
        ArrayList<Iterator<Integer>> it = new ArrayList<>();
        it.add(i1);
        it.add(i2);
        it.add(i3);
        Iterator<Iterator<Integer>> iteratorIterator = it.iterator();
        Iterator<Integer> converter = new Converter().convert(iteratorIterator);
        converter.next();
        int i = converter.next();
        assertThat(i, is(0));
    }
}