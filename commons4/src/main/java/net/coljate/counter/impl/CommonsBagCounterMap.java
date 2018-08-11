package net.coljate.counter.impl;

import net.coljate.collection.Collection;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.impl.ViewEntry;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 * @since 1.0
 * @see org.apache.commons.collections4.Bag
 * @see CommonsBagCounter
 */
public class CommonsBagCounterMap<T> extends AbstractMap<T, Integer> {

    private final org.apache.commons.collections4.Bag<T> bag;
    private Set<T> keys;

    protected CommonsBagCounterMap(final org.apache.commons.collections4.Bag<T> bag) {
        this.bag = bag;
    }

    @Override
    @SuppressWarnings("element-type-mismatch")
    public boolean containsKey(final Object key) {
        return bag.contains(key);
    }

    @Override
    @SuppressWarnings(value = "unchecked")
    public Entry<T, Integer> getEntry(final Object key) {
        return ViewEntry.viewOf(key, this);
    }

    @Override
    public Set<T> keys() {
        return keys == null
                ? keys = Set.viewOf(bag.uniqueSet())
                : keys;
    }

    @Override
    public Collection<Integer> values() {
        return this.keys().transform(bag::getCount);
    }

}
