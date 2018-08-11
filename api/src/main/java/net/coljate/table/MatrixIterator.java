package net.coljate.table;

import java.util.Iterator;

/**
 *
 * @author Ollie
 */
public class MatrixIterator<T> implements Iterator<Cell<Integer, Integer, T>> {

    private final Matrix<? extends T> matrix;
    private int x, y;

    public MatrixIterator(final Matrix<? extends T> matrix) {
        this.matrix = matrix;
    }

    @Override
    public boolean hasNext() {
        return x < matrix.rows() && y < matrix.columns();
    }

    @Override
    public Cell<Integer, Integer, T> next() {
        final int y = this.y++, x = this.x;
        final T next = matrix.get(x, y);
        if (this.y == matrix.columns()) {
            this.x += 1;
            this.y = 0;
        }
        return new ImmutableCell<>(x, y, next);
    }

}
