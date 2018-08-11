package net.coljate.set.primitive;

import net.coljate.collection.primitive.ImmutableDoubleCollection;
import net.coljate.set.ImmutableSet;

/**
 *
 * @author Ollie
 */
public interface ImmutableDoubleSet
        extends DoubleSet, ImmutableDoubleCollection, ImmutableSet<Double> {

    @Override
    @Deprecated
    default ImmutableDoubleSet immutableCopy() {
        return this;
    }

}
