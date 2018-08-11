package net.coljate.list.impl;

import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;

import net.coljate.collection.Empty;
import net.coljate.list.AbstractArray;
import net.coljate.list.ImmutableArray;
import net.coljate.list.ImmutableListIterator;

/**
 *
 * @author Ollie
 */
public class EmptyArray<T>
        extends AbstractArray<T>
        implements Empty<T>, ImmutableArray<T> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("rawtypes")
    private static final EmptyArray INSTANCE = new EmptyArray();

    @SuppressWarnings("unchecked")
    public static <T> EmptyArray<T> instance() {
        return INSTANCE;
    }

    @Override
    public boolean contains(final Object object) {
        return Empty.super.contains(object);
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int length() {
        return 0;
    }

    @Override
    public T get(final int index) {
        throw new IndexOutOfBoundsException("Empty");
    }

    @Override
    public EmptyArray<T> reversedCopy() {
        return this;
    }

    @Override
    public ImmutableListIterator<T> iterator() {
        return ImmutableListIterator.empty();
    }

    @Override
    public <R> EmptyArray<R> transform(final Function<? super T, ? extends R> transformation) {
        return instance();
    }

    @Override
    public EmptyArray<T> filter(final Predicate<? super T> predicate) {
        return this;
    }

    @Override
    public Spliterator<T> spliterator() {
        return Empty.super.spliterator();
    }

}
