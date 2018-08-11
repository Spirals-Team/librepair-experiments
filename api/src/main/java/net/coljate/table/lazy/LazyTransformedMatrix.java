package net.coljate.table.lazy;

import java.util.function.Function;

import net.coljate.table.Matrix;

/**
 *
 * @author Ollie
 */
public class LazyTransformedMatrix<F, T>
        implements LazyMatrix<T> {

    private final Matrix<? extends F> delegate;
    private final Function<? super F, ? extends T> transformation;

    public LazyTransformedMatrix(
            final Matrix<? extends F> delegate,
            final Function<? super F, ? extends T> transformation) {
        this.delegate = delegate;
        this.transformation = transformation;
    }

    @Override
    public int rows() {
        return delegate.rows();
    }

    @Override
    public int columns() {
        return delegate.columns();
    }

    @Override
    public T get(int x, int y) {
        return transformation.apply(delegate.get(x, y));
    }

}
