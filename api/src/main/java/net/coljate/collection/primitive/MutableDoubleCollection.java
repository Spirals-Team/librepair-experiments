package net.coljate.collection.primitive;

import net.coljate.collection.MutableCollection;

/**
 *
 * @author Ollie
 */
public interface MutableDoubleCollection extends DoubleCollection, MutableCollection<Double> {

    boolean removeFirst(double d);

    @Override
    default boolean removeFirst(final Object d) {
        return d instanceof Double
                && this.removeFirst(((Double) d).doubleValue());
    }

}
