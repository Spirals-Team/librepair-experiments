package net.coljate.collection.primitive;

import java.util.Iterator;
import java.util.function.BinaryOperator;
import java.util.function.DoubleBinaryOperator;

import net.coljate.collection.Collection;
import net.coljate.list.primitive.ImmutableNativeDoubleArray;
import net.coljate.list.primitive.MutableNativeDoubleArray;

/**
 *
 * @author Ollie
 */
public interface DoubleCollection extends DoubleContainer, Collection<Double> {

    @Override
    default boolean contains(final double d) {
        for (final DoubleIterator iterator = this.iterator(); iterator.hasNext();) {
            if (iterator.nextDouble() == d) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Deprecated
    default boolean contains(final Object object) {
        return DoubleContainer.super.contains(object);
    }

    /**
     * @return the doubles in this collection copied into a new array.
     */
    default double[] doubleArrayCopy() {
        return toArray(this);
    }

    @Override
    DoubleIterator iterator();

    @Override
    @Deprecated
    default Double reduce(final BinaryOperator<Double> operator) {
        return Collection.super.reduce(operator);
    }

    default double reduce(final DoubleBinaryOperator operator, final double initialValue) {
        final DoubleIterator iterator = this.iterator();
        double current = initialValue;
        while (iterator.hasNext()) {
            current = operator.applyAsDouble(current, iterator.nextDouble());
        }
        return current;
    }

    default double sum() {
        return this.reduce((final double d1, final double d2) -> d1 + d2, 0d);
    }

    default double product() {
        return this.reduce((final double d1, final double d2) -> d1 + d2, 1d);
    }

    @Override
    default MutableDoubleCollection mutableCopy() {
        return MutableNativeDoubleArray.copyOf(this);
    }

    @Override
    default ImmutableDoubleCollection immutableCopy() {
        return ImmutableNativeDoubleArray.copyOf(this);
    }

    static DoubleCollection copyOf(final double[] array) {
        return MutableNativeDoubleArray.copyOf(array);
    }

    static double[] toArray(final DoubleCollection collection) {
        final double[] array = new double[collection.count()];
        int index = 0;
        for (final DoubleIterator iterator = collection.iterator(); iterator.hasNext();) {
            array[index++] = iterator.nextDouble();
        }
        return array;
    }

    interface DoubleIterator extends Iterator<Double> {

        double nextDouble();

        @Override
        @Deprecated
        default Double next() {
            return this.nextDouble();
        }

    }

}
