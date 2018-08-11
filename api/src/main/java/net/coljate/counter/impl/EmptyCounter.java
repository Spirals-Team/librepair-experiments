package net.coljate.counter.impl;

import net.coljate.collection.Empty;
import net.coljate.counter.AbstractCounter;
import net.coljate.counter.ImmutableCounter;
import net.coljate.counter.MutableCounter;
import net.coljate.map.ImmutableMap;
import net.coljate.set.ImmutableSet;
import net.coljate.set.Set;
import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

/**
 *
 * @author Ollie
 */
public class EmptyCounter<T>
        extends AbstractCounter<T>
        implements ImmutableCounter<T>, Empty<T> {

    @Override
    @TimeComplexity(Complexity.CONSTANT)
    public int count(final Object element) {
        return 0;
    }

    @Override
    public boolean contains(final Object object) {
        return Empty.super.contains(object);
    }

    @Override
    public Set<T> elements() {
        return ImmutableSet.of();
    }

    @Override
    public ImmutableMap<T, Integer> countElements() {
        return ImmutableMap.of();
    }

    @Override
    @Deprecated
    public EmptyCounter<T> immutableCopy() {
        return this;
    }

    @Override
    public MutableCounter<T> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet."); //TODO
    }

}
