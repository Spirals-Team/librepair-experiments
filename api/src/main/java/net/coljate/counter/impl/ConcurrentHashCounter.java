package net.coljate.counter.impl;

import net.coljate.counter.ConcurrentCounter;
import net.coljate.map.ConcurrentMap;

/**
 *
 * @author Ollie
 */
public class ConcurrentHashCounter<T>
        extends MutableHashCounter<T>
        implements ConcurrentCounter<T> {

    public static <T> ConcurrentHashCounter<T> create() {
        return new ConcurrentHashCounter<>(ConcurrentMap.createHashMap(), false);
    }

    private final ConcurrentMap<T, Integer> map;

    protected ConcurrentHashCounter(final ConcurrentMap<T, Integer> map, boolean evictZeros) {
        super(map, evictZeros);
        this.map = map;
    }

    @Override
    public ConcurrentCounter<T> mutableCopy() {
        return new ConcurrentHashCounter<>(map.mutableCopy(), this.evictZeros());
    }

}
