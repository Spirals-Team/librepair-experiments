package net.coljate.set.primitive;

import net.coljate.collection.primitive.MutableDoubleCollection;
import net.coljate.set.MutableSet;

/**
 * @author Ollie
 */
public interface MutableDoubleSet
        extends DoubleSet, MutableDoubleCollection, MutableSet<Double> {

    boolean add(double d);

    @Override
    @Deprecated
    default boolean add(final Double d) {
        return d != null
                && this.add(d.doubleValue());
    }

    boolean remove(double d);

    @Override
    @Deprecated
    default boolean remove(final Object element) {
        return element instanceof Double
                && this.remove(((Double) element).doubleValue());
    }

    @Override
    @Deprecated
    default boolean removeFirst(double d) {
        return this.remove(d);
    }

    @Override
    @Deprecated
    default boolean removeFirst(final Object element) {
        return MutableSet.super.removeFirst(element);
    }

    @Override
    MutableDoubleSet mutableCopy();

    @Override
    ImmutableDoubleSet immutableCopy();

}
