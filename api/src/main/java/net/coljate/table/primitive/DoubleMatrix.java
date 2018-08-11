package net.coljate.table.primitive;

import net.coljate.table.Matrix;

/**
 *
 * @author Ollie
 */
public interface DoubleMatrix extends Matrix<Double> {

    double getValue(int x, int y);

    default double[][] array2DCopy() {
        final int rows = this.rows();
        final int cols = this.columns();
        final double[][] copy = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                copy[i][j] = this.getValue(i, j);
            }
        }
        return copy;
    }

    @Override
    @Deprecated
    default Double get(final int x, final int y) {
        return this.getValue(x, y);
    }

    default DoubleMatrix product(final DoubleMatrix that) {
        final int n = this.rows();
        final int m = this.columns();
        final double[][] product = new double[n][that.columns()];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                double sum = 0;
                for (int k = 0; k < m; k++) {
                    sum += this.getValue(i, k) * that.getValue(k, j);
                }
                product[i][j] = sum;
            }
        }
        return new DoubleArrayMatrix(product);
    }

    @Override
    default MutableDoubleMatrix mutableCopy() {
        return MutableDoubleArrayMatrix.copyOf(this);
    }

    @Override
    default ImmutableDoubleMatrix immutableCopy() {
        return ImmutableDoubleArrayMatrix.copyOf(this);
    }

}
