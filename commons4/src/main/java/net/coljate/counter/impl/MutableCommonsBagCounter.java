package net.coljate.counter.impl;

import java.util.Comparator;

import net.coljate.counter.MutableCounter;
import net.coljate.util.Arrays;

import org.apache.commons.collections4.bag.HashBag;

/**
 *
 * @author Ollie
 */
public class MutableCommonsBagCounter<T>
        extends CommonsBagCounter<T>
        implements MutableCounter<T> {

    public static <T> MutableCommonsBagCounter<T> createHashCounter() {
        return new MutableCommonsBagCounter<>(new org.apache.commons.collections4.bag.HashBag<>());
    }

    @SafeVarargs
    public static <T> MutableCommonsBagCounter<T> createHashCounter(final T... elements) {
        final HashBag<T> bag = new org.apache.commons.collections4.bag.HashBag<>();
        Arrays.consume(elements, bag::add);
        return new MutableCommonsBagCounter<>(bag);
    }

    public static <T extends Comparable<? super T>> MutableCommonsBagCounter<T> createTreeCounter() {
        return new MutableCommonsBagCounter<>(new org.apache.commons.collections4.bag.TreeBag<>());
    }

    public static <T> MutableCommonsBagCounter<T> createTreeCounter(final Comparator<? super T> comparator) {
        return new MutableCommonsBagCounter<>(new org.apache.commons.collections4.bag.TreeBag<>(comparator));
    }

    protected MutableCommonsBagCounter(final org.apache.commons.collections4.Bag<T> bag) {
        super(bag);
    }

    @Override
    public void set(final T element, final int count) {
        if (count < 0) {
            throw new IllegalArgumentException();
        }
        final int current = this.count(element);
        if (count > current) {
            bag.add(element, count - current);
        } else if (count < current) {
            bag.remove(element, current - count);
        }
    }

    @Override
    public int incrementAndGet(final T element, final int amount) {
        bag.add(element, amount);
        return this.count(element);
    }

    @Override
    public int decrementAndGet(final T element, final int amount) {
        bag.remove(amount, amount);
        return this.count(element);
    }

    @Override
    public void clear() {
        bag.clear();
    }

}
