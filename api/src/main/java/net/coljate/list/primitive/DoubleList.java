package net.coljate.list.primitive;

import net.coljate.collection.primitive.DoubleCollection;
import net.coljate.list.List;
import net.coljate.list.ListIterator;

/**
 *
 * @author Ollie
 */
public interface DoubleList extends DoubleCollection, List<Double> {

    @Override
    DoubleListIterator iterator();

    @Override
    default MutableDoubleList mutableCopy() {
        return MutableNativeDoubleArray.copyOf(this);
    }

    @Override
    ImmutableDoubleList immutableCopy();

    interface DoubleListIterator extends DoubleIterator, ListIterator<Double> {

        double previousDouble();

        @Override
        @Deprecated
        default Double previous() {
            return this.previousDouble();
        }

    }

}
