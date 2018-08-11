package net.coljate.collection.primitive;

import net.coljate.collection.primitive.LongIterable.LongIterator;

import gnu.trove.iterator.TLongIterator;

/**
 *
 * @author Ollie
 */
public class TroveLongIterator implements LongIterator {

    private final TLongIterator iterator;

    public TroveLongIterator(final TLongIterator iterator) {
        this.iterator = iterator;
    }

    @Override
    public long nextLong() {
        return iterator.next();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public void remove() {
        iterator.remove();
    }

}
