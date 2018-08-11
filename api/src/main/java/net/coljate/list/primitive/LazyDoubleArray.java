package net.coljate.list.primitive;

import java.util.function.IntToDoubleFunction;

/**
 *
 * @author Ollie
 */
public class LazyDoubleArray implements DoubleArray {

    private static final byte INITIALIZED = 1;
    private final IntToDoubleFunction function;
    private final byte[] initialized;
    private final double[] values;

    public LazyDoubleArray(final IntToDoubleFunction function, final int length) {
        this.function = function;
        this.initialized = new byte[length];
        this.values = new double[length];
    }

    @Override
    public double getDouble(final int index) {
        return initialized[index] == INITIALIZED
                ? values[index]
                : this.setDouble(index);
    }

    private double setDouble(final int index) {
        final double d = function.applyAsDouble(index);
        values[index] = d;
        initialized[index] = INITIALIZED;
        return d;
    }

    @Override
    public int length() {
        return values.length;
    }

}
