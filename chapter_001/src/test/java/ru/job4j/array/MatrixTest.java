package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class MatrixTest {
    @Test
    public void when() {
        Matrix matrix = new Matrix();
        int[][] result = {
                {0, 0, 0, 0},
                {0, 1, 2, 3},
                {0, 2, 4, 6},
                {0, 3, 6, 9}
        };
        assertThat(matrix.multiple(result.length),  is(result));
    }
}
