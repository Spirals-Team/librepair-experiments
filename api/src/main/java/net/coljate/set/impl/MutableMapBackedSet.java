package net.coljate.set.impl;

import net.coljate.map.MutableMap;
import net.coljate.set.MutableSet;

/**
 *
 * @author Ollie
 */
public class MutableMapBackedSet<T>
        extends MapBackedSet<T>
        implements MutableSet<T> {

    private final MutableMap<T, Object> map;

    protected MutableMapBackedSet(final MutableMap<T, Object> map) {
        super(map);
        this.map = map;
    }

    @Override
    public boolean add(final T element) {
        return map.add(element, SENTINEL);
    }

    @Override
    public boolean remove(final Object element) {
        return map.remove(element, SENTINEL);
    }

    @Override
    public void clear() {
        map.clear();
    }

}
