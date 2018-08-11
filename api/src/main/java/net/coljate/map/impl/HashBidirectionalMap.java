package net.coljate.map.impl;

import net.coljate.map.BidirectionalMap;
import net.coljate.map.Map;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public abstract class HashBidirectionalMap<K, V, I extends HashBidirectionalMap<V, K, ?>>
        extends DelegatedMap<K, V>
        implements BidirectionalMap<K, V> {

    private final Map<K, V> map;
    private I inverse;

    protected HashBidirectionalMap(final Map<K, V> forward) {
        this(forward, null);
    }

    protected HashBidirectionalMap(final Map<K, V> forward, final I inverse) {
        this.map = forward;
        this.inverse = inverse;
    }

    @Override
    protected Map<K, V> delegate() {
        return map;
    }

    @Override
    public I inverseView() {
        return inverse == null
                ? inverse = this.createInverseView()
                : inverse;
    }

    protected abstract I createInverseView();

    @Override
    public Set<V> values() {
        return BidirectionalMap.super.values();
    }

    @Override
    public MutableHashBidirectionalMap<K, V> mutableCopy() {
        return new MutableHashBidirectionalMap<>(map.mutableCopy());
    }

}
