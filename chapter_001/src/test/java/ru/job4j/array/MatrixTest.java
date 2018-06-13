package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Aleksandr Vysotskiiy (Aleksandr.vysotskiiy@gmail.com)
 * @version 1.0
 * @since 0.1
 */

public class MatrixTest {
    @Test
    public void whenTheMatrixWorksAsItShould() {
        int[][] testAnswer = {
                {1, 2},
                {2, 4},
        };
        Matrix reboot = new Matrix();
        int[][] result = reboot.multiple(2);
        assertThat(result, is(testAnswer));
    }
}
