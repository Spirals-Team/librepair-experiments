package net.coljate.list.primitive;

import net.coljate.list.MutableArray;

/**
 *
 * @author Ollie
 */
public interface MutableDoubleArray
        extends DoubleArray, MutableDoubleList, MutableArray<Double> {

    double set(int index, double d);

    @Override
    @Deprecated
    default Double set(final int index, final Double d) {
        return this.set(index, d.doubleValue());
    }

    @Override
    default MutableDoubleArray mutableCopy() {
        return MutableNativeDoubleArray.copyOf(this);
    }

}
