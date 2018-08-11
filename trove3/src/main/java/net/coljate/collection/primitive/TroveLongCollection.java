package net.coljate.collection.primitive;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;

import gnu.trove.TLongCollection;

/**
 *
 * @author Ollie
 */
public class TroveLongCollection
        extends AbstractCollection<Long>
        implements LongCollection {

    public static LongCollection viewOf(final TLongCollection collection) {
        return new TroveLongCollection(collection);
    }

    private final TLongCollection collection;

    protected TroveLongCollection(final TLongCollection collection) {
        this.collection = collection;
    }

    @Override
    protected boolean equals(Collection<?> that) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public LongIterator iterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public boolean contains(final long l) {
        return collection.contains(l);
    }

    @Override
    public int count() {
        return collection.size();
    }

    @Override
    public MutableLongCollection mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public ImmutableLongCollection immutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

}
