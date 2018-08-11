package net.coljate.counter.impl;

import java.util.Iterator;

import net.coljate.counter.AbstractCounter;
import net.coljate.counter.Counter;
import net.coljate.map.Map;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 * @see org.apache.commons.collections4.Bag
 * @since 1.0
 */
public class CommonsBagCounter<T>
        extends AbstractCounter<T>
        implements Counter<T> {

    public static <T> CommonsBagCounter<T> viewOf(final org.apache.commons.collections4.Bag<T> bag) {
        return new CommonsBagCounter<>(bag);
    }

    final org.apache.commons.collections4.Bag<T> bag;

    protected CommonsBagCounter(final org.apache.commons.collections4.Bag<T> bag) {
        this.bag = bag;
    }

    @Override
    public org.apache.commons.collections4.Bag<T> mutableJavaCopy() {
        return bag instanceof org.apache.commons.collections4.bag.TreeBag
                ? new org.apache.commons.collections4.bag.TreeBag<>(bag)
                : new org.apache.commons.collections4.bag.HashBag<>(bag);
    }

    @Override
    public int count(final Object element) {
        return bag.getCount(element);
    }

    @Override
    public Set<T> elements() {
        return Set.viewOf(bag.uniqueSet());
    }

    @Override
    public Map<T, Integer> countElements() {
        return new CommonsBagCounterMap<>(bag);
    }

    @Override
    public Iterator<T> iterator() {
        return bag.iterator();
    }

    @Override
    public boolean isEmpty() {
        return bag.isEmpty();
    }

    @Override
    public MutableCommonsBagCounter<T> mutableCopy() {
        return new MutableCommonsBagCounter<>(this.mutableJavaCopy());
    }

}
