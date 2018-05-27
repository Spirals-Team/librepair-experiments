package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class FindLoopTest {

    /**
     * Тест на поиск 4 в массиве {1, 3, 4, 5, 2, 7}.
     */
    @Test
    public void whenFindElemenInArray() {
        FindLoop findLoop = new FindLoop();
        int[] array = {1, 3, 4, 5, 2, 7};
        int rsl = findLoop.indexOf(array, 4);
        assertThat(rsl, is(2));
    }

    /**
     * Тест на поиск несущесвуещего элемента в массиве.
     */
    @Test
    public void whenErrorElemenInArray() {
        FindLoop findLoop = new FindLoop();
        int[] array = {1, 3, 4, 5, 2, 7};
        int rsl = findLoop.indexOf(array, 9);
        assertThat(rsl, is(-1));
    }
}