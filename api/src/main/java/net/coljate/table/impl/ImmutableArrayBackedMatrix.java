package net.coljate.table.impl;

import net.coljate.table.ImmutableMatrix;

/**
 *
 * @author Ollie
 */
public class ImmutableArrayBackedMatrix<T>
        extends ArrayBackedMatrix<T>
        implements ImmutableMatrix<T> {

    protected ImmutableArrayBackedMatrix(final Object[][] matrix) {
        super(matrix);
    }

}
