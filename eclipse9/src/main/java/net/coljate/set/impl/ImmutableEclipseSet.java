package net.coljate.set.impl;

import net.coljate.collection.impl.EclipseRichIterableCollection;
import net.coljate.set.ImmutableSet;
import net.coljate.set.MutableSet;
import net.coljate.util.iterator.UnmodifiableIterator;

import org.eclipse.collections.impl.set.mutable.UnifiedSet;

/**
 *
 * @author ollie
 */
public class ImmutableEclipseSet<T>
        extends EclipseRichIterableCollection<T>
        implements ImmutableSet<T> {

    public ImmutableEclipseSet(final org.eclipse.collections.api.set.ImmutableSet<T> set) {
        super(set);
    }

    @Override
    public UnmodifiableIterator<T> iterator() {
        return UnmodifiableIterator.wrap(super.iterator());
    }

    public UnifiedSet<T> unifiedSetCopy() {
        return this.mutableJavaCopy(UnifiedSet::newSet);
    }

    @Override
    public MutableSet<T> mutableCopy() {
        return new EclipseUnifiedSet<>(this.unifiedSetCopy());
    }

    @Override
    @Deprecated
    public ImmutableSet<T> immutableCopy() {
        return this;
    }

}
