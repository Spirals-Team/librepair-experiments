package net.coljate.set.primitive;


import net.coljate.collection.primitive.DoubleCollection;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public interface DoubleSet extends Set<Double>, DoubleCollection {

    @Override
    MutableDoubleSet mutableCopy();

    @Override
    ImmutableDoubleSet immutableCopy();

}
