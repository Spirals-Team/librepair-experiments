package net.coljate.list.primitive;

import java.util.Arrays;
import java.util.Objects;

import net.coljate.collection.primitive.DoubleCollection;

/**
 *
 * @author Ollie
 */
public class MutableNativeDoubleArray implements MutableDoubleArray {

    public static MutableNativeDoubleArray copyOf(final double[] array) {
        final double[] copy = Arrays.copyOf(array, array.length);
        return new MutableNativeDoubleArray(copy);
    }

    public static MutableNativeDoubleArray copyOf(final DoubleCollection collection) {
        return new MutableNativeDoubleArray(collection.doubleArrayCopy());
    }

    private double[] array;

    public MutableNativeDoubleArray(final double[] array) {
        this.array = Objects.requireNonNull(array, "array");
    }

    @Override
    public double set(int index, double d) {
        final double previous = array[index];
        array[index] = d;
        return previous;
    }

    @Override
    public double getDouble(int index) {
        return array[index];
    }

    @Override
    public int length() {
        return array.length;
    }

    @Override
    public void suffix(final double d) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public void prefix(final double d) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public boolean removeFirst(final double d) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public void resize(final int length) {
        array = Arrays.copyOf(array, length);
    }

}
