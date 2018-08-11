package net.coljate.collection.impl;

import java.util.Iterator;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.collection.ImmutableCollection;
import net.coljate.collection.MutableCollection;
import net.coljate.util.iterator.Iterators;

import org.eclipse.collections.api.RichIterable;

/**
 *
 * @author ollie
 */
public class EclipseRichIterableCollection<T> extends AbstractCollection<T> {

    private final RichIterable<? extends T> iterable;

    public EclipseRichIterableCollection(final RichIterable<? extends T> iterable) {
        this.iterable = iterable;
    }

    @Override
    public boolean contains(Object object) {
        return iterable.contains(object);
    }

    @Override
    public int count() {
        return iterable.size();
    }

    @Override
    protected boolean equals(final Collection<?> that) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public MutableCollection<T> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableCollection<T> immutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<T> iterator() {
        return Iterators.covariant(iterable.iterator());
    }

}
