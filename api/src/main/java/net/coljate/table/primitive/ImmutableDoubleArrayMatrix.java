package net.coljate.table.primitive;

/**
 *
 * @author Ollie
 */
public class ImmutableDoubleArrayMatrix extends DoubleArrayMatrix implements ImmutableDoubleMatrix {

    public static ImmutableDoubleMatrix copyOf(final DoubleMatrix matrix) {
        return new ImmutableDoubleArrayMatrix(matrix.array2DCopy());
    }

    ImmutableDoubleArrayMatrix(final double[][] array) {
        super(array);
    }

}
