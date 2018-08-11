package net.coljate.map.impl;

import net.coljate.collection.Collection;
import net.coljate.list.Array;
import net.coljate.map.AbstractEntry;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.MutableEntry;
import net.coljate.map.MutableMap;
import net.coljate.set.Set;
import net.coljate.util.Arrays;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class OpenAddressedHashMap<K, V>
        extends AbstractMap<K, V>
        implements MutableMap<K, V>, HashMap<K, V> {

    public static <K, V> OpenAddressedHashMap<K, V> create(final int capacity) {
        return new OpenAddressedHashMap<>(capacity);
    }

    private MutableEntry<K, V>[] entries;

    protected OpenAddressedHashMap(final int buckets) {
        this.entries = new MutableEntry[buckets];
    }

    protected int hash(final Object key) {
        return key == null ? 0 : key.hashCode();
    }

    private int find(final Object key) {
        if (entries.length == 0) {
            return -1;
        }
        final int hash = this.hash(key);
        int index = hash % entries.length;
        for (int i = index; i < entries.length; i++) {
            final MutableEntry<K, V> entry = entries[i];
            if (entry == null || Objects.equals(entry.key(), key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public MutableEntry<K, V> getEntry(final Object key) {
        final int index = this.find(key);
        return index >= 0 ? entries[index] : null;
    }

    @CheckForNull
    @Override
    public V put(final K key, final V value) {
        int index = this.find(key);
        if (index < 0) {
            index = this.expand();
        }
        final MutableEntry<K, V> entry = entries[index];
        if (entry == null) {
            entries[index] = new RegularMutableEntry<>(key, value);
            return null;
        } else {
            return entry.getAndSetValue(value);
        }
    }

    private int expand() {
        if (entries.length == 0) {
            entries = new MutableEntry[10];
            return 0;
        }
        throw new UnsupportedOperationException("Expand"); //TODO expand
    }

    @Override
    public boolean remove(@Nullable final Object key, @Nullable final Object value) {
        final int index = this.find(key);
        if (index < 0) {
            return false;
        }
        final MutableEntry<K, V> entry = entries[index];
        if (entry == null) {
            return false;
        }
        if (!Objects.equals(entry.value(), value)) {
            return false;
        }
        entries[index] = null;
        //TODO resize
        return true;
    }

    @Override
    public boolean isEmpty() {
        return entries.length == 0 || !Arrays.any(entries, Objects::nonNull);
    }

    @Override
    public int count() {
        return Arrays.count(entries, Objects::nonNull);
    }

    @Override
    public void clear() {
        this.entries = new MutableEntry[0];
    }

    @Nonnull
    @Override
    public Set<K> keys() {
        return Array.viewOf(entries)
                .filter(Objects::nonNull)
                .transform(Entry::key)
                .distinct();
    }

    @Nonnull
    @Override
    public Collection<V> values() {
        return Array.viewOf(entries)
                .filter(Objects::nonNull)
                .transform(Entry::value);
    }

    private static class RegularMutableEntry<K, V> extends AbstractEntry<K, V> implements MutableEntry<K, V> {

        private final K key;
        private V value;

        private RegularMutableEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        @CheckForNull
        @Override
        public K key() {
            return key;
        }

        @CheckForNull
        @Override
        public V value() {
            return value;
        }

        @Override
        public void setValue(final V value) {
            this.value = value;
        }

    }

}
