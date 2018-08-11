package net.coljate.matrix;

import net.coljate.table.Matrix;
import net.coljate.table.TableTest;

/**
 *
 * @author Ollie
 */
public interface MatrixTest<T> extends TableTest<Integer, Integer, T> {

    @Override
    Matrix<T> createTestCollection();

}
