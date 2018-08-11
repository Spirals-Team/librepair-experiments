package net.coljate.list.impl;

import net.coljate.collection.Collection;
import net.coljate.list.ImmutableList;
import net.coljate.list.ImmutableListIterator;

/**
 *
 * @author Ollie
 */
public class ImmutableWrappedList<T>
        extends WrappedList<T>
        implements ImmutableList<T> {

    public static <T> ImmutableWrappedList<T> copyOf(final java.util.Collection<? extends T> collection) {
        return new ImmutableWrappedList<>(new java.util.ArrayList<>(collection));
    }

    public static <T> ImmutableWrappedList<T> copyOf(final Collection<? extends T> collection) {
        final java.util.List<T> list = new java.util.ArrayList<>(collection.count());
        collection.forEach(list::add);
        return new ImmutableWrappedList<>(list);
    }

    protected ImmutableWrappedList(final java.util.List<T> delegate) {
        super(delegate);
    }

    @Override
    public ImmutableList<T> reversedCopy() {
        final java.util.List<T> list = this.mutableJavaCopy();
        java.util.Collections.reverse(list);
        return new ImmutableWrappedList<>(list);
    }

    @Override
    public ImmutableListIterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ImmutableListIterator<T> reverseIterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public ImmutableList<T> prefixed(final T element) {
        final java.util.List<T> list = this.mutableJavaCopy();
        list.add(element);
        return new ImmutableWrappedList<>(list);
    }

    @Override
    public ImmutableList<T> suffixed(final T element) {
        final java.util.List<T> list = this.mutableJavaCopy();
        list.add(0, element);
        return new ImmutableWrappedList<>(list);
    }

    @Override
    @Deprecated
    public ImmutableWrappedList<T> immutableCopy() {
        return this;
    }

}
