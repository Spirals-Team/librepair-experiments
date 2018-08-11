package net.coljate.collection.primitive;

import net.coljate.collection.primitive.DoubleCollection.DoubleIterator;

import gnu.trove.iterator.TDoubleIterator;

/**
 *
 * @author Ollie
 */
public class TroveDoubleIterator implements DoubleIterator {

    private final TDoubleIterator iterator;

    public TroveDoubleIterator(final TDoubleIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public double nextDouble() {
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }

}
