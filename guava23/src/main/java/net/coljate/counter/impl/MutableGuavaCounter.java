package net.coljate.counter.impl;

import com.google.common.collect.Multiset;

import net.coljate.counter.MutableCounter;

/**
 *
 * @author Ollie
 */
public class MutableGuavaCounter<T> extends GuavaCounter<T> implements MutableCounter<T> {

    private final Multiset<T> multiset;

    public MutableGuavaCounter(final Multiset<T> multiset) {
        super(multiset);
        this.multiset = multiset;
    }

    @Override
    public void set(final T element, final int count) {
        multiset.setCount(element, count);
    }

    @Override
    public int getAndIncrement(final T element, final int amount) {
        return multiset.add(element, amount);
    }

    @Override
    public int incrementAndGet(final T element, final int amount) {
        return multiset.add(element, amount) + amount;
    }

    @Override
    public int decrementAndGet(final T element, final int amount) {
        final int before = multiset.remove(element, amount);
        return Math.max(before - amount, 0);
    }

}
