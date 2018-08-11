package net.coljate.collection.primitive;

import gnu.trove.iterator.TDoubleIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableTroveDoubleIterator extends TroveDoubleIterator implements UnmodifiableDoubleIterator {

    public ImmutableTroveDoubleIterator(final TDoubleIterator iterator) {
        super(iterator);
    }

    @Override
    @Deprecated
    public void remove() {
        UnmodifiableDoubleIterator.super.remove();
    }

}
