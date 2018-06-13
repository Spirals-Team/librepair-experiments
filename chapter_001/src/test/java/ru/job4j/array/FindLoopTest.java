package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */

public class FindLoopTest {
    @Test
    public void findsAnElement() {
        FindLoop finds = new FindLoop();
        int[] r = new int[6];
        r[0] = 1;
        r[1] = 2;
        r[2] = 3;
        r[3] = 4;
        r[4] = 5;
        int result = finds.indexOf(r, 5);
        assertThat(result, is(4));
    }

    @Test
    public void doesNotFindAnItem() {
        FindLoop haveNotFound = new FindLoop();
        int[] r = new int[4];
        r[2] = 3;
        int result = haveNotFound.indexOf(r, 4);
        assertThat(result, is(-1));
    }
}
