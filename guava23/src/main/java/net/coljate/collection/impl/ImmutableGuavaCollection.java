package net.coljate.collection.impl;

import net.coljate.collection.ImmutableCollection;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableGuavaCollection<T>
        extends WrappedCollection<T>
        implements ImmutableCollection<T> {

    protected ImmutableGuavaCollection(final com.google.common.collect.ImmutableCollection<? extends T> collection) {
        super(collection);
    }

    @Override
    @Deprecated
    public ImmutableGuavaCollection<T> immutableCopy() {
        return this;
    }

    @Override
    public UnmodifiableIterator<T> iterator() {
        return UnmodifiableIterator.wrap(super.iterator());
    }

}
