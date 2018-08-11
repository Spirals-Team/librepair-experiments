package net.coljate.set.primitive;

import gnu.trove.set.TLongSet;

/**
 *
 * @author Ollie
 */
public class ImmutableTroveLongSet extends TroveLongSet implements ImmutableLongSet {

    ImmutableTroveLongSet(final TLongSet collection) {
        super(collection);
    }

    @Override
    public UnmodifiableLongIterator iterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    @Deprecated
    public ImmutableTroveLongSet immutableCopy() {
        return this;
    }

}
