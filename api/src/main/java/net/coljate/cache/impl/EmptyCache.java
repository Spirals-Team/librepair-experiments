package net.coljate.cache.impl;

import net.coljate.cache.Cache;
import net.coljate.map.impl.EmptyMap;

/**
 *
 * @author ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class EmptyCache<K, V>
        extends EmptyMap<K, V>
        implements Cache<K, V> {

    private static final long serialVersionUID = 1L;
    private static final EmptyCache INSTANCE = new EmptyCache();

    public static <K, V> EmptyCache<K, V> instance() {
        return INSTANCE;
    }

    protected EmptyCache() {
    }

    @Override
    @Deprecated
    public EmptyMap<K, V> immutableCopy() {
        return super.immutableCopy();
    }

}
