package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SquareTest {

    @Test
    public void calculate() {
        Square square = new Square();
        int size = 7;
        int[] rsl = square.calculate(size);
        int[] equal = {1, 4, 9, 16, 25, 36, 49};
        assertThat(rsl, is(equal));
    }
}