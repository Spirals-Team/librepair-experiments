package net.coljate.list.primitive;

import net.coljate.list.ImmutableArray;

/**
 *
 * @author Ollie
 */
public interface ImmutableDoubleArray
        extends DoubleArray, ImmutableDoubleList, ImmutableArray<Double> {

    @Override
    default ImmutableDoubleListIterator iterator() {
        return new ImmutableDoubleArrayIterator(this);
    }

    @Override
    default ImmutableDoubleListIterator reverseIterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    @Deprecated
    default ImmutableDoubleArray immutableCopy() {
        return this;
    }

    class ImmutableDoubleArrayIterator extends DoubleArrayIterator implements ImmutableDoubleListIterator {

        public ImmutableDoubleArrayIterator(final DoubleArray array) {
            super(array);
        }

    }

}
