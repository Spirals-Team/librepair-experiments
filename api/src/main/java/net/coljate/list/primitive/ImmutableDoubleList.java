package net.coljate.list.primitive;

import net.coljate.collection.primitive.ImmutableDoubleCollection;
import net.coljate.collection.primitive.UnmodifiableDoubleIterator;
import net.coljate.list.ImmutableList;
import net.coljate.list.ImmutableListIterator;

/**
 *
 * @author Ollie
 */
public interface ImmutableDoubleList
        extends DoubleList, ImmutableList<Double>, ImmutableDoubleCollection {

    @Override
    @Deprecated
    default ImmutableDoubleList immutableCopy() {
        return this;
    }

    @Override
    ImmutableDoubleListIterator iterator();

    interface ImmutableDoubleListIterator extends DoubleListIterator, UnmodifiableDoubleIterator, ImmutableListIterator<Double> {

    }

}
