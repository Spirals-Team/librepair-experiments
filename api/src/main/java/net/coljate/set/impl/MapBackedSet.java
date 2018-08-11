package net.coljate.set.impl;

import java.util.Iterator;

import net.coljate.map.Map;
import net.coljate.set.AbstractSet;
import net.coljate.set.ImmutableSet;

/**
 *
 * @author Ollie
 */
public class MapBackedSet<T>
        extends AbstractSet<T> {

    protected static final Object SENTINEL = new Object();
    private final Map<T, Object> map;

    protected MapBackedSet(final Map<T, Object> map) {
        this.map = map;
    }

    @Override
    public boolean contains(final Object object) {
        return map.containsKey(object);
    }

    @Override
    public Iterator<T> iterator() {
        return map.keys().iterator();
    }

    @Override
    public MutableMapBackedSet<T> mutableCopy() {
        return new MutableMapBackedSet<>(map.mutableCopy());
    }

    @Override
    public ImmutableSet<T> immutableCopy() {
        return new ImmutableMapBackedSet<>(map.immutableCopy());
    }

}
