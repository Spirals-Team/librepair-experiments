package net.coljate.list.primitive;

import net.coljate.collection.primitive.MutableDoubleCollection;
import net.coljate.list.MutableList;

/**
 *
 * @author Ollie
 */
public interface MutableDoubleList
        extends DoubleList, MutableList<Double>, MutableDoubleCollection {

    void suffix(double d);

    void prefix(double d);

    @Override
    @Deprecated
    default void suffix(final Double d) {
        this.suffix(d.doubleValue());
    }

    @Override
    @Deprecated
    default void prefix(final Double d) {
        this.prefix(d.doubleValue());
    }

    @Override
    MutableDoubleList mutableCopy();

}
