package net.coljate.set.primitive;

import net.coljate.collection.primitive.TroveDoubleCollection;

import gnu.trove.set.TDoubleSet;
import gnu.trove.set.hash.TDoubleHashSet;

/**
 *
 * @author Ollie
 */
public class TroveDoubleSet
        extends TroveDoubleCollection
        implements DoubleSet {

    public static DoubleSet viewOf(final TDoubleSet set) {
        return new TroveDoubleSet(set);
    }

    protected TroveDoubleSet(final TDoubleSet set) {
        super(set);
    }

    @Override
    public TDoubleSet mutableTroveCopy() {
        return this.mutableTroveCopy(TDoubleHashSet::new);
    }

    @Override
    public MutableTroveDoubleSet mutableCopy() {
        return MutableTroveDoubleSet.copyIntoHashSet(this);
    }

    @Override
    public ImmutableTroveDoubleSet immutableCopy() {
        return ImmutableTroveDoubleSet.copyIntoHashSet(this);
    }

}
