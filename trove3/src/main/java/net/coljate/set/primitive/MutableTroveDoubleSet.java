package net.coljate.set.primitive;

import net.coljate.collection.primitive.DoubleCollection;
import net.coljate.collection.primitive.TroveDoubleCollection;

import gnu.trove.impl.unmodifiable.TUnmodifiableDoubleSet;
import gnu.trove.set.TDoubleSet;
import gnu.trove.set.hash.TDoubleHashSet;

/**
 *
 * @author Ollie
 */
public class MutableTroveDoubleSet
        extends TroveDoubleSet
        implements MutableDoubleSet {

    public static MutableTroveDoubleSet createHashSet() {
        return new MutableTroveDoubleSet(new TDoubleHashSet());
    }

    public static MutableTroveDoubleSet copyIntoHashSet(final TroveDoubleCollection collection) {
        return new MutableTroveDoubleSet(collection.mutableTroveCopy(TDoubleHashSet::new));
    }

    public static MutableTroveDoubleSet copyIntoHashSet(final DoubleCollection collection) {
        if (collection instanceof TroveDoubleCollection) {
            return copyIntoHashSet((TroveDoubleCollection) collection);
        }
        final TDoubleSet set = new TDoubleHashSet(collection.count());
        for (final DoubleIterator iterator = collection.iterator(); iterator.hasNext();) {
            set.add(iterator.nextDouble());
        }
        return new MutableTroveDoubleSet(set);
    }

    private final TDoubleSet set;

    protected MutableTroveDoubleSet(final TDoubleSet set) {
        super(set);
        if (set instanceof TUnmodifiableDoubleSet) {
            throw new IllegalArgumentException("Set is not modifiable!");
        }
        this.set = set;
    }

    @Override
    public boolean add(final double d) {
        return set.add(d);
    }

    @Override
    public boolean remove(final double d) {
        return set.remove(d);
    }

    @Override
    public void clear() {
        set.clear();
    }

}
