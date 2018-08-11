package net.coljate.counter.impl;

import com.google.common.collect.ConcurrentHashMultiset;

import net.coljate.counter.ConcurrentCounter;

/**
 *
 * @author Ollie
 */
public class ConcurrentGuavaCounter<T> extends MutableGuavaCounter<T> implements ConcurrentCounter<T> {

    public ConcurrentGuavaCounter(final ConcurrentHashMultiset<T> multiset) {
        super(multiset);
    }

    @Override
    public ConcurrentCounter<T> mutableCopy() {
        return new ConcurrentGuavaCounter<>(this.concurrentHashCopy());
    }

}
