package net.coljate.collection.primitive;

import java.util.Objects;
import java.util.function.IntFunction;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;

import gnu.trove.TDoubleCollection;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.list.array.TDoubleArrayList;

/**
 *
 * @author Ollie
 */
public class TroveDoubleCollection
        extends AbstractCollection<Double>
        implements DoubleCollection {

    public static DoubleCollection viewOf(final TDoubleCollection collection) {
        return new TroveDoubleCollection(collection);
    }

    private final TDoubleCollection collection;

    protected TroveDoubleCollection(final TDoubleCollection collection) {
        this.collection = collection;
    }

    @Override
    public boolean contains(final double d) {
        return collection.contains(d);
    }

    @Override
    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public int count() {
        return collection.size();
    }

    @Override
    public double[] doubleArrayCopy() {
        return collection.toArray();
    }

    protected TDoubleIterator troveIterator() {
        return collection.iterator();
    }

    public TDoubleCollection mutableTroveCopy() {
        return this.mutableTroveCopy(TDoubleArrayList::new);
    }

    public <C extends TDoubleCollection> C mutableTroveCopy(final IntFunction<C> createCollection) {
        final C collection = createCollection.apply(this.count());
        collection.addAll(this.collection);
        return collection;
    }

    @Override
    public DoubleIterator iterator() {
        return new TroveDoubleIterator(this.troveIterator());
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        return that instanceof TroveDoubleCollection
                && Objects.equals(collection, ((TroveDoubleCollection) that).collection);
    }

    @Override
    public MutableDoubleCollection mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public ImmutableDoubleCollection immutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

}
