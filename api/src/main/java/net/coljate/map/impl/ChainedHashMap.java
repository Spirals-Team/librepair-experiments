package net.coljate.map.impl;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.list.MutableList;
import net.coljate.list.impl.MutableLinkedList;
import net.coljate.map.AbstractEntry;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.MutableEntry;
import net.coljate.map.MutableMap;
import net.coljate.set.AbstractSet;
import net.coljate.set.Set;
import net.coljate.util.functions.Functions;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author Ollie
 * @see java.util.HashMap
 */
public class ChainedHashMap<K, V>
        extends AbstractMap<K, V>
        implements MutableMap<K, V>, HashMap<K, V> {

    public static <K, V> ChainedHashMap<K, V> create(final int initialCapacity) {
        return new ChainedHashMap<>(initialCapacity);
    }

    public static <K, V> ChainedHashMap<K, V> copyOf(final java.util.Collection<? extends Entry<K, V>> entries) {
        final ChainedHashMap<K, V> map = create(entries.size());
        map.addAll(entries);
        return map;
    }

    @Nonnull
    private MutableList<MutableEntry<K, V>>[] buckets;
    private Set<K> keys;
    private Collection<V> values;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public ChainedHashMap(final int initialCapacity) {
        this.buckets = new MutableList[initialCapacity];
    }

    private static <T> int indexOf(final Object key, final T[] array) {
        return key == null
                ? 0
                : key.hashCode() % array.length;
    }

    private MutableList<MutableEntry<K, V>> getBucket(final Object key) {
        return getBucket(key, buckets);
    }

    private static <K, V> MutableList<MutableEntry<K, V>> getBucket(
            final Object key,
            final MutableList<MutableEntry<K, V>>[] array) {
        return array.length == 0
                ? null
                : array[indexOf(key, array)];
    }

    private static <K, V> MutableList<MutableEntry<K, V>> getOrCreateBucket(
            final Object key,
            final MutableList<MutableEntry<K, V>>[] array) {
        final int index = indexOf(key, array);
        MutableList<MutableEntry<K, V>> list = array[index];
        if (list == null) {
            list = new MutableLinkedList<>();
            array[index] = list;
        }
        return list;
    }

    @Override
    public MutableEntry<K, V> getEntry(final Object key) {
        final MutableList<MutableEntry<K, V>> bucket = this.getBucket(key);
        return bucket == null
                ? null
                : bucket.first(entry -> Objects.equals(key, entry.key()));
    }

    @Override
    public V put(final K key, final V value) {
        this.ensureCapacity(buckets.length + 1);
        final MutableList<MutableEntry<K, V>> bucket = getOrCreateBucket(key, buckets);
        MutableEntry<K, V> entry = bucket.first(e -> Objects.equals(e.key(), key));
        if (entry == null) {
            bucket.suffix(new ChainedEntry<>(key, value));
            return null;
        } else {
            return entry.getAndSetValue(value);
        }
    }

    @Override
    public boolean remove(final Object key, final Object value) {
        return this.remove(new ChainedEntry<>(key, value));
    }

    @Override
    public boolean remove(final Entry<?, ?> entry) {
        final MutableList<MutableEntry<K, V>> bucket = entry == null
                ? null
                : this.getBucket(entry.key());
        return bucket != null
                && bucket.removeFirst(entry);
    }

    private void ensureCapacity(final int size) {
        if (size > buckets.length * 0.75) {
            this.resize(Math.max(2, buckets.length * 2));
        }
    }

    private void resize(final int newSize) {
        @SuppressWarnings("unchecked") final MutableList<MutableEntry<K, V>>[] newBuckets = new MutableList[newSize];
        for (final EntryIterator iterator = new EntryIterator(); iterator.hasNext(); ) {
            final MutableEntry<K, V> entry = iterator.next();
            getOrCreateBucket(entry.key(), newBuckets).suffix(entry);
        }
        this.buckets = newBuckets;
    }

    @Override
    public void clear() {
        this.buckets = new MutableList[buckets.length];
    }

    @Override
    public Set<K> keys() {
        return keys == null ? (keys = new Keys()) : keys;
    }

    @Override
    public Collection<V> values() {
        return values == null ? (values = new Values()) : values;
    }

    @Override
    public CovariantIterator<Entry<K, V>, MutableEntry<K, V>> iterator() {
        return new EntryIterator();
    }

    private final class EntryIterator implements CovariantIterator<Entry<K, V>, MutableEntry<K, V>> {

        int index = 0;
        Iterator<MutableEntry<K, V>> entryIterator;

        @Override
        public boolean hasNext() {
            while ((entryIterator == null || !entryIterator.hasNext()) && index < buckets.length) {
                entryIterator = Functions.ifNonNull(buckets[index++], Collection::iterator);
            }
            return entryIterator != null && entryIterator.hasNext();
        }

        @Override
        @SuppressWarnings("dereference.of.nullable")
        public MutableEntry<K, V> next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return entryIterator.next();
        }

        @Override
        public void remove() {
            if (entryIterator == null) {
                throw new NoSuchElementException();
            }
            entryIterator.remove();
        }

    }

    private final class Keys extends AbstractSet<K> {

        @Override
        public boolean contains(final Object object) {
            final MutableList<MutableEntry<K, V>> bucket = getBucket(object);
            return bucket != null
                    && bucket.first(entry -> Objects.equals(object, entry.key())) != null;
        }

        @Override
        public Iterator<K> iterator() {
            return Iterators.transform(ChainedHashMap.this.iterator(), Entry::key);
        }

    }

    private final class Values extends AbstractCollection<V> {

        @Override
        public Iterator<V> iterator() {
            return Iterators.transform(ChainedHashMap.this.iterator(), Entry::value);
        }

        @Override
        protected boolean equals(final Collection<?> that) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private static final class ChainedEntry<K, V>
            extends AbstractEntry<K, V>
            implements MutableEntry<K, V> {

        private final K key;
        private V value;

        ChainedEntry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K key() {
            return key;
        }

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
