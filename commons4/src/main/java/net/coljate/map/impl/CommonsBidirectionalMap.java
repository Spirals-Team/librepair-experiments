package net.coljate.map.impl;

import net.coljate.map.AbstractMap;
import net.coljate.map.BidirectionalMap;
import net.coljate.map.Entry;
import net.coljate.map.MutableBidirectionalMap;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class CommonsBidirectionalMap<K, V>
        extends AbstractMap<K, V>
        implements BidirectionalMap<K, V> {

    final org.apache.commons.collections4.BidiMap<K, V> map;
    private BidirectionalMap<V, K> inverse;
    private Set<K> keys;

    protected CommonsBidirectionalMap(final org.apache.commons.collections4.BidiMap<K, V> map) {
        this(map, null);
    }

    protected CommonsBidirectionalMap(final org.apache.commons.collections4.BidiMap<K, V> map, final BidirectionalMap<V, K> inverse) {
        this.map = map;
        this.inverse = inverse;
    }

    @Override
    public org.apache.commons.collections4.BidiMap<K, V> mutableJavaMapCopy() {
        return map instanceof org.apache.commons.collections4.bidimap.DualTreeBidiMap
                ? new org.apache.commons.collections4.bidimap.DualTreeBidiMap<>(map)
                : new org.apache.commons.collections4.bidimap.DualHashBidiMap<>(map);
    }

    @Override
    public boolean containsKey(final Object key) {
        return map.containsKey(key);
    }

    @Override
    public Entry<K, V> getEntry(final Object key) {
        return ViewEntry.viewOf(key, this);
    }

    @Override
    public Set<K> keys() {
        return keys == null
                ? keys = Set.viewOf(map.keySet())
                : keys;
    }

    @Override
    public BidirectionalMap<V, K> inverseView() {
        return inverse == null
                ? inverse = new CommonsBidirectionalMap<>(map.inverseBidiMap(), this)
                : inverse;
    }

    @Override
    public MutableBidirectionalMap<K, V> mutableCopy() {
        return new MutableCommonsBidirectionalMap<>(this.mutableJavaMapCopy());
    }

}
