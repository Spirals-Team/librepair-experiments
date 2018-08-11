package net.coljate.counter.impl;

import java.util.Iterator;

import net.coljate.counter.MutableCounter;
import net.coljate.map.MutableMap;

/**
 *
 * @author Ollie
 */
public class MutableHashCounter<T>
        extends HashCounter<T>
        implements MutableCounter<T> {

    public static <T> MutableHashCounter<T> create() {
        return new MutableHashCounter<>(MutableMap.createHashMap(), true);
    }

    public static <T> MutableHashCounter<T> copyOf(final Iterable<? extends T> iterable) {
        final MutableHashCounter<T> set = create();
        iterable.forEach(set::incrementAndGet);
        return set;
    }

    private final MutableMap<T, Integer> map;
    private final boolean evictZeros;

    protected MutableHashCounter(final MutableMap<T, Integer> map, boolean evictZeros) {
        super(map);
        this.map = map;
        this.evictZeros = evictZeros;
    }

    protected boolean evictZeros() {
        return evictZeros;
    }

    @Override
    public MutableMap<T, Integer> countElements() {
        return map;
    }

    @Override
    public void set(final T element, final int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Tried to set negative count [" + count + "] on [" + element + "]!");
        } else if (count > 0 || !evictZeros) {
            map.put(element, count);
        } else {
            map.evict(element);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public int removeAll(final Object element) {
        return isPositive(map.evict(element)) ? 1 : 0;
    }

    @Override
    public int incrementAndGet(final T element, final int amount) {
        return map.compute(element, (k, count) -> increment(count, amount));
    }

    @Override
    public int decrementAndGet(final T element, final int amount) {
        final int decremented = map.compute(element, (k, count) -> decrement(count, amount));
        if (decremented == 0 && evictZeros) {
            map.remove(element, 0);
        }
        return decremented;
    }

    @Override
    public void clear() {
        map.clear();
    }

    private static int increment(final Integer integer, final int amount) {
        return integer == null ? amount : integer + amount;
    }

    private static int decrement(final Integer integer, final int amount) {
        return isPositive(integer) ? Math.max(0, integer - amount) : 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new MutableHashMultisetIterator(super.iterator());
    }

    @Override
    public MutableCounter<T> mutableCopy() {
        return new MutableHashCounter<>(map.mutableCopy(), evictZeros);
    }

    @Override
    public ImmutableHashCounter<T> immutableCopy() {
        return ImmutableHashCounter.copyOf(this);
    }

    private class MutableHashMultisetIterator implements Iterator<T> {

        final Iterator<T> delegate;
        private T current;

        MutableHashMultisetIterator(Iterator<T> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean hasNext() {
            return delegate.hasNext();
        }

        @Override
        public T next() {
            current = delegate.next();
            return current;
        }

        @Override
        public void remove() {
            //TODO check current state
            MutableHashCounter.this.decrementAndGet(current);
        }

    }

}
