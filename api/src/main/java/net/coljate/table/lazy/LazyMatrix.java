package net.coljate.table.lazy;

import net.coljate.table.ImmutableMatrix;
import net.coljate.table.Matrix;
import net.coljate.table.MutableMatrix;

/**
 *
 * @author Ollie
 */
public interface LazyMatrix<T> extends Matrix<T>, LazyTable<Integer, Integer, T> {

    @Override
    default MutableMatrix<T> mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    default ImmutableMatrix<T> immutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

}
