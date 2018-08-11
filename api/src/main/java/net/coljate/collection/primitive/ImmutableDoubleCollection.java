package net.coljate.collection.primitive;

import net.coljate.collection.ImmutableCollection;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public interface ImmutableDoubleCollection
        extends DoubleCollection, ImmutableCollection<Double> {

    @Override
    @Deprecated
    default ImmutableDoubleCollection immutableCopy() {
        return this;
    }

    @Override
    UnmodifiableDoubleIterator iterator();


}
