package net.coljate.set.impl;

import com.hazelcast.core.ISet;

import net.coljate.set.ConcurrentSet;

/**
 *
 * @author ollie
 */
public class HazelcastSet<T> extends MutableWrappedSet<T> implements ConcurrentSet<T> {

    public HazelcastSet(final ISet<T> delegate) {
        super(delegate);
    }

    @Override
    public ConcurrentSet<T> mutableCopy() {
        return ConcurrentSet.super.mutableCopy();
    }

}
