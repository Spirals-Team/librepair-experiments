package net.coljate.table.primitive;

import net.coljate.table.MutableMatrix;

/**
 *
 * @author Ollie
 */
public interface MutableDoubleMatrix extends DoubleMatrix, MutableMatrix<Double> {

    void set(int x, int y, double value);

    @Override
    @Deprecated
    default Double put(final int x, final int y, final Double value) {
        final double d = this.getValue(x, y);
        this.set(x, y, value);
        return d;
    }

    @Override
    @Deprecated
    default Double evict(final int x, final int y) {
        final double d = this.getValue(x, y);
        this.set(x, y, 0.0d);
        return d;
    }

}
