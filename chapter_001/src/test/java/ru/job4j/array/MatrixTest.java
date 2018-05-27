package ru.job4j.array;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class MatrixTest {

    /**
     * Тест создания матрицы умножения.
     */
    @Test
    public void whenCreateMatrixFiveForFiveAndPutMultiplexNumber() {
        Matrix matrix = new Matrix();
        int[][] testMatrix = matrix.multiple(5);
        int[][] expectMatrix = {{1, 2, 3, 4, 5}, {2, 4, 6, 8, 10}, {3, 6, 9, 12, 15},
            {4, 8, 12, 16, 20}, {5, 10, 15, 20, 25}};
        assertThat(testMatrix, is(expectMatrix));
    };

}