package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class FindLoopTest {
    @Test
    public void whenElementIsFromArrayThenIndexOfElement() {
        FindLoop findLoop = new FindLoop();
        int[] array = {5, 6, 7};
        int search = 6;
        assertThat(findLoop.indexOf(array, search), is(1));
    }

    @Test
    public void whenElementIsNotFromArrayThenNumber() {
        FindLoop findLoop = new FindLoop();
        int[] array = {5, 6, 7};
        int search = 10;
        assertThat(findLoop.indexOf(array, search), is(-1));
    }
}