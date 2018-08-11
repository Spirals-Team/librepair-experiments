package net.coljate.list.impl;

import net.coljate.collection.impl.ImmutableGuavaCollection;
import net.coljate.list.ImmutableList;
import net.coljate.list.ImmutableListIterator;
import net.coljate.list.MutableList;

/**
 *
 * @author Ollie
 */
public class ImmutableGuavaList<T>
        extends ImmutableGuavaCollection<T>
        implements ImmutableList<T> {

    private final com.google.common.collect.ImmutableList<? extends T> list;

    public ImmutableGuavaList(final com.google.common.collect.ImmutableList<? extends T> list) {
        super(list);
        this.list = list;
    }

    @Override
    public T last() {
        return list.isEmpty() ? null : list.get(list.size() - 1);
    }

    @Override
    public ImmutableListIterator<T> iterator() {
        return ImmutableListIterator.of(list.listIterator());
    }

    @Override
    public java.util.List<T> mutableJavaCopy() {
        return ImmutableList.super.mutableJavaCopy();
    }

    @Override
    public MutableList<T> mutableCopy() {
        return MutableList.copyIntoArray(this);
    }

    @Override
    @Deprecated
    public ImmutableGuavaList<T> immutableCopy() {
        return this;
    }

}
