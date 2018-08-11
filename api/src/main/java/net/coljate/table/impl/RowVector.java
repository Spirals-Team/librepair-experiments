package net.coljate.table.impl;

import net.coljate.list.Array;
import net.coljate.table.ImmutableMatrix;
import net.coljate.table.Matrix;
import net.coljate.table.MutableMatrix;

/**
 *
 * @author Ollie
 */
public class RowVector<T> implements Matrix<T> {

    private final Array<? extends T> array;

    protected RowVector(final Array<? extends T> array) {
        this.array = array;
    }

    @Override
    public int rows() {
        return array.length();
    }

    @Override
    public int columns() {
        return 1;
    }

    @Override
    public T get(int x, int y) {
        if (y != 0) {
            throw new IndexOutOfBoundsException(y + " != 0");
        }
        return array.get(x);
    }

    @Override
    public MutableMatrix<T> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableMatrix<T> immutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
