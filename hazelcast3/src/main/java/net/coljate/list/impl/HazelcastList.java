package net.coljate.list.impl;

import com.hazelcast.core.IList;

import net.coljate.list.ConcurrentList;

/**
 *
 * @author ollie
 */
public class HazelcastList<T> extends MutableWrappedList<T> implements ConcurrentList<T> {

    public HazelcastList(final IList<T> delegate) {
        super(delegate);
    }

    @Override
    public ConcurrentList<T> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
