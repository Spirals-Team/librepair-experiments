package net.coljate.counter.impl;

import java.util.Iterator;
import java.util.NoSuchElementException;

import net.coljate.counter.AbstractCounter;
import net.coljate.counter.Counter;
import net.coljate.counter.ImmutableCounter;
import net.coljate.counter.MutableCounter;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.set.Set;
import net.coljate.util.suppliers.Suppliers;

/**
 * 
 *
 * @author Ollie
 */
public class HashCounter<T>
        extends AbstractCounter<T>
        implements Counter<T> {

    private final Map<T, Integer> map;

    protected HashCounter(final Map<T, Integer> count) {
        this.map = count;
    }

    @Override
    public int count(final Object object) {
        return Suppliers.firstNonNull(map.getIfPresent(object), 0);
    }

    @Override
    public Set<T> elements() {
        return map.keys();
    }

    @Override
    public Map<T, Integer> countElements() {
        return map;
    }

    @Override
    public boolean contains(final Object object) {
        return Counter.super.contains(object);
    }

    protected static boolean isPositive(final Integer integer) {
        return integer != null && integer > 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new MultisetIterator();
    }

    @Override
    public MutableCounter<T> mutableCopy() {
        return new MutableHashCounter<>(map.mutableCopy(), true);
    }

    @Override
    public ImmutableCounter<T> immutableCopy() {
        return ImmutableHashCounter.copyOf(this);
    }

    private final class MultisetIterator implements Iterator<T> {

        final Iterator<Entry<T, Integer>> mapIterator = map.iterator();
        T currentElement;
        int currentCount;

        @Override
        public boolean hasNext() {
            while (currentCount == 0 && mapIterator.hasNext()) {
                final Entry<T, Integer> entry = mapIterator.next();
                currentElement = entry.key();
                currentCount = entry.value();
            }
            return currentCount > 0;
        }

        @Override
        public T next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            currentCount--;
            return currentElement;
        }

    }

}
