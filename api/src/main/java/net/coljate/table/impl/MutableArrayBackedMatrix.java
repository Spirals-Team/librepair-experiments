package net.coljate.table.impl;

import net.coljate.table.MutableMatrix;

/**
 *
 * @author Ollie
 */
public class MutableArrayBackedMatrix<T>
        extends ArrayBackedMatrix<T>
        implements MutableMatrix<T> {

    public static <T> MutableArrayBackedMatrix<T> create(final int rows, final int columns) {
        return new MutableArrayBackedMatrix<>(new Object[rows][columns]);
    }

    private final Object[][] matrix;

    protected MutableArrayBackedMatrix(final Object[][] matrix) {
        super(matrix);
        this.matrix = matrix;
    }

    @Override
    public T put(final int x, final int y, final T value) {
        return swap(matrix[x], y, value);
    }

    @Override
    public T evict(final int x, final int y) {
        return swap(matrix[x], y, null);
    }

    private static <T> T swap(final Object[] array, final int index, final T value) {
        final T current = (T) array[index];
        array[index] = value;
        return current;
    }

    @Override
    public void clear() {
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new Object[matrix[i].length];
        }
    }

}
