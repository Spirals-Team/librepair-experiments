package net.coljate.set.primitive;

import net.coljate.collection.primitive.TroveLongCollection;

import gnu.trove.set.TLongSet;

/**
 *
 * @author Ollie
 */
public class TroveLongSet
        extends TroveLongCollection
        implements LongSet {

    public static LongSet viewOf(final TLongSet set) {
        return new TroveLongSet(set);
    }

    protected TroveLongSet(final TLongSet collection) {
        super(collection);
    }

    @Override
    public MutableLongSet mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public ImmutableLongSet immutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

}
