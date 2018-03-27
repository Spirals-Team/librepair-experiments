package ru.job4j.collections.iterator;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест Iterators.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 30.09.17;
 * @version $Id$
 * @since 0.1
 */
public class IteratorArrayTest {
    /**
     *метод проверяет вывод двумерного массива с помощь итератора.
     */
    @Test
    public void hasNext() {
        IteratorArray it = new IteratorArray(new int[][]{{1, 2}, {3, 4}});
        String result = "1234";
        String ex = "";
        while (it.hasNext()) {
            ex += "" + it.next();
        }
        assertThat(result, is(ex));
    }

}