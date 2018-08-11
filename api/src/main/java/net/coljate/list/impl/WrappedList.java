package net.coljate.list.impl;

import net.coljate.collection.impl.WrappedCollection;
import net.coljate.list.ImmutableList;
import net.coljate.list.List;
import net.coljate.list.ListIterator;
import net.coljate.list.MutableList;

/**
 *
 * @author Ollie
 */
public class WrappedList<T>
        extends WrappedCollection<T>
        implements List<T> {

    public static <T> List<T> copyOf(final java.util.Collection<? extends T> collection) {
        return new WrappedList<>(new java.util.ArrayList<>(collection));
    }

    private final java.util.List<? extends T> list;

    public WrappedList(final java.util.List<? extends T> delegate) {
        super(delegate);
        this.list = delegate;
    }

    @Override
    public List<T> reversedCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public java.util.List<T> mutableJavaCopy() {
        return new java.util.ArrayList<>(list);
    }

    @Override
    public ListIterator<T> iterator() {
        return ListIterator.wrap(list.listIterator());
    }

    @Override
    public ListIterator<T> reverseIterator() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public T last() {
        return list.get(list.size() - 1);
    }

    @Override
    public MutableList<T> mutableCopy() {
        return new MutableWrappedList<>(this.mutableJavaCopy());
    }

    @Override
    public ImmutableList<T> immutableCopy() {
        return new ImmutableWrappedList<>(this.mutableJavaCopy());
    }

}
