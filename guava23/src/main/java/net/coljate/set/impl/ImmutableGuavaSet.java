package net.coljate.set.impl;

import net.coljate.collection.impl.ImmutableGuavaCollection;
import net.coljate.set.ImmutableSet;
import net.coljate.set.MutableSet;

/**
 *
 * @author Ollie
 */
public class ImmutableGuavaSet<T>
        extends ImmutableGuavaCollection<T>
        implements ImmutableSet<T> {

    public ImmutableGuavaSet(final com.google.common.collect.ImmutableSet<? extends T> set) {
        super(set);
    }

    @Override
    public java.util.Set<T> mutableJavaCopy() {
        return ImmutableSet.super.mutableJavaCopy();
    }

    @Override
    public MutableSet<T> mutableCopy() {
        return MutableSet.copyIntoHashSet(this);
    }

    @Override
    @Deprecated
    public ImmutableGuavaSet<T> immutableCopy() {
        return this;
    }

}
