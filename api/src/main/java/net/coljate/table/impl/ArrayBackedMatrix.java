package net.coljate.table.impl;

import net.coljate.table.Cell;
import net.coljate.table.ImmutableMatrix;
import net.coljate.table.Matrix;
import net.coljate.table.MutableMatrix;
import net.coljate.util.Arrays;

/**
 *
 * @author Ollie
 */
public class ArrayBackedMatrix<T> implements Matrix<T> {

    public static <T> ArrayBackedMatrix<T> copyOf(final Iterable<? extends Cell<Integer, Integer, ? extends T>> cells) {
        int rows = -1, columns = -1;
        for (final Cell<Integer, Integer, ?> cell : cells) {
            rows = Math.max(rows, cell.rowKey());
            columns = Math.max(columns, cell.columnKey());
        }
        final Object[][] array = new Object[rows + 1][columns + 1];
        for (final Cell<Integer, Integer, ?> cell : cells) {
            array[cell.rowKey()][cell.columnKey()] = cell.value();
        }
        return new ArrayBackedMatrix<>(array);
    }

    private final Object[][] matrix;

    protected ArrayBackedMatrix(final Object[][] matrix) {
        this.matrix = matrix;
    }

    @Override
    public int rows() {
        return matrix.length == 0 ? 0 : matrix[0].length;
    }

    @Override
    public int columns() {
        return matrix.length;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int x, int y) {
        return (T) matrix[x][y];
    }

    @Override
    public Object[][] arrayCopy() {
        return Arrays.copy(matrix);
    }

    @Override
    public MutableMatrix<T> mutableCopy() {
        return new MutableArrayBackedMatrix<>(this.arrayCopy());
    }

    @Override
    public ImmutableMatrix<T> immutableCopy() {
        return new ImmutableArrayBackedMatrix<>(this.arrayCopy());
    }

}
