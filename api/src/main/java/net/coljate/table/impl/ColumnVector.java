package net.coljate.table.impl;

import java.util.Iterator;

import net.coljate.list.Array;
import net.coljate.table.Cell;
import net.coljate.table.ImmutableMatrix;
import net.coljate.table.Matrix;
import net.coljate.table.MutableMatrix;

/**
 *
 * @author Ollie
 */
public class ColumnVector<T> implements Matrix<T> {

    private final Array<? extends T> array;

    protected ColumnVector(final Array<? extends T> array) {
        this.array = array;
    }

    @Override
    public int rows() {
        return 1;
    }

    @Override
    public int columns() {
        return array.length();
    }

    @Override
    public T get(final int x, final int y) {
        if (x != 0) {
            throw new IndexOutOfBoundsException(x + " != 0");
        }
        return array.get(y);
    }

    @Override
    public MutableMatrix<T> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableMatrix<T> immutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<Cell<Integer, Integer, T>> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
