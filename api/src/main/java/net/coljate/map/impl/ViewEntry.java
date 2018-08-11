package net.coljate.map.impl;

import net.coljate.map.AbstractEntry;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.map.MutableEntry;
import net.coljate.map.MutableMap;

import javax.annotation.CheckForNull;

/**
 * @author Ollie
 */
public abstract class ViewEntry<K, V> extends AbstractEntry<K, V> {

    @SuppressWarnings("unchecked")
    @CheckForNull
    public static <K, V> Entry<K, V> viewOf(final Object key, final Map<K, ? extends V> map) {
        return map.containsKey(key)
                ? new ReadOnlyViewEntry<>((K) key, map)
                : null;
    }

    @SuppressWarnings("unchecked")
    @CheckForNull
    public static <K, V> MutableEntry<K, V> viewOf(final Object key, final MutableMap<K, V> map) {
        return map.containsKey(key)
                ? new MutableViewEntry<>((K) key, map)
                : null;
    }

    private final K key;

    protected ViewEntry(final K key) {
        this.key = key;
    }

    @Override
    public K key() {
        return key;
    }

    protected abstract Map<K, ? extends V> map();

    @Override
    public V value() {
        return this.map().get(key);
    }

    static class ReadOnlyViewEntry<K, V> extends ViewEntry<K, V> {

        private final Map<K, ? extends V> map;

        ReadOnlyViewEntry(final K key, final Map<K, ? extends V> map) {
            super(key);
            this.map = map;
        }

        @Override
        protected Map<K, ? extends V> map() {
            return map;
        }

    }

    static class MutableViewEntry<K, V> extends ViewEntry<K, V> implements MutableEntry<K, V> {

        private final MutableMap<K, V> map;

        MutableViewEntry(final K key, final MutableMap<K, V> map) {
            super(key);
            this.map = map;
        }

        @Override
        protected MutableMap<K, V> map() {
            return map;
        }

        @Override
        public void setValue(final V value) {
            map.put(this.key(), value);
        }

    }

}
