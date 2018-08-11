package net.coljate.table.primitive;

import net.coljate.table.ImmutableMatrix;

/**
 *
 * @author Ollie
 */
public interface ImmutableDoubleMatrix extends DoubleMatrix, ImmutableMatrix<Double> {

    @Override
    @Deprecated
    default ImmutableDoubleMatrix immutableCopy() {
        return this;
    }

}
