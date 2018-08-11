package net.coljate.map.impl;

import net.coljate.collection.AbstractCollection;
import net.coljate.collection.Collection;
import net.coljate.list.MutableList;
import net.coljate.map.AbstractMap;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.map.MutableEntry;
import net.coljate.map.MutableMap;
import net.coljate.set.AbstractSet;
import net.coljate.set.Set;
import net.coljate.util.iterator.Iterators;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class StringTrie<V>
        extends AbstractMap<String, V>
        implements MutableMap<String, V> {

    public static <V> StringTrie<V> copyOf(final Map<String, ? extends V> map) {
        final StringTrie<V> trie = new StringTrie<>();
        trie.putAll(map);
        return trie;
    }

    private TrieEntry<V> root = new TrieEntry<>("");
    private TrieKeys keys;
    private TrieValues values;

    public StringTrie() {
    }

    public StringTrie(final Entry<String, V> entry) {
        this.root.put(entry.key(), entry.value());
    }

    @CheckForNull
    @Override
    public V put(final String key, final V value) {
        Objects.requireNonNull(key);
        return root.put(key, value);
    }

    @Override
    public boolean remove(@Nullable final Object key, @Nullable final Object value) {
        Objects.requireNonNull(key);
        if (!(key instanceof String)) {
            return false;
        }
        return root.remove((String) key, 0, value);
    }

    @Override
    public boolean containsKey(@Nullable Object key) {
        return key instanceof String
                && root.contains((String) key);
    }

    @CheckForNull
    @Override
    public V get(@Nullable final String key) {
        return root.get(key);
    }

    @CheckForNull
    @Override
    public MutableEntry<String, V> getEntry(@Nullable final Object key) {
        return ViewEntry.viewOf(key, this);
    }

    @Override
    public void clear() {
        root = new TrieEntry<>("");
    }

    @Override
    public boolean isEmpty() {
        return root == null || root.isEmpty();
    }

    @Nonnull
    @Override
    public Set<String> keys() {
        return keys == null
                ? (keys = new TrieKeys())
                : keys;
    }

    @Nonnull
    @Override
    public Collection<V> values() {
        return values == null
                ? (values = new TrieValues())
                : values;
    }

    @Override
    public StringTrie<V> mutableCopy() {
        return copyOf(this);
    }

    private static class TrieEntry<V> {

        private final MutableMap<Character, TrieEntry<V>> children = MutableMap.createHashMap();

        private final String word;
        private boolean end;
        private V value;

        private TrieEntry(final String word) {
            this.word = word;
        }

        boolean contains(final String key) {
            TrieEntry<V> entry = this;
            for (int i = 0; i < key.length(); i++) {
                final char c = key.charAt(i);
                entry = entry.children.get(c);
                if (entry == null) {
                    return false;
                }
            }
            assert Objects.equals(entry.word, key) : entry.word + " != " + key;
            return entry.end;
        }

        V get(final String key) {
            TrieEntry<V> entry = this;
            for (int i = 0; i < key.length(); i++) {
                final char c = key.charAt(i);
                entry = entry.children.get(c);
                if (entry == null) {
                    return null;
                }
            }
            assert Objects.equals(entry.word, key) : entry.word + " != " + key;
            return entry.end ? entry.value : null;
        }

        V put(final String key, final V value) {
            TrieEntry<V> entry = this;
            for (int i = 0; i < key.length(); i++) {
                final char c = key.charAt(i);
                final int j = i;
                entry = entry.children.computeIfAbsent(c, cc -> new TrieEntry<>(key.substring(0, j + 1)));
            }
            final V previous = entry.value;
            entry.end = true;
            entry.value = value;
            return previous;
        }

        boolean remove(final String key, final int i, final Object value) {
            if (this.end && i == word.length()) {
                assert word.equals(key) : word + " != " + key;
                if (Objects.equals(this.value, value)) {
                    this.end = false;
                    this.value = null;
                    return true;
                } else {
                    return false;
                }
            }
            final char c = key.charAt(i);
            final TrieEntry<V> child = children.get(c);
            final boolean removed = child != null && child.remove(key, i + 1, value);
            if (removed && this.isEmpty()) {
                children.remove(c);
            }
            return removed;
        }

        boolean isEmpty() {
            if (end) {
                return false;
            }
            if (children.isEmpty()) {
                return true;
            }
            return !children.anyMatch(e -> !e.value().isEmpty());
        }

        void collectWords(final Consumer<TrieEntry<V>> consumer) {
            if (end) {
                consumer.accept(this);
            }
            children.values().forEach(child -> child.collectWords(consumer));
        }

    }

    private class TrieKeys extends AbstractSet<String> {

        @Override
        public boolean contains(final Object object) {
            return StringTrie.this.containsKey(object);
        }

        @Override
        public boolean isEmpty() {
            return StringTrie.this.isEmpty();
        }

        @Override
        public Iterator<String> iterator() {
            final MutableList<TrieEntry<V>> wordEntries = MutableList.create(10);
            root.collectWords(wordEntries::add);
            return Iterators.transform(wordEntries.iterator(), e -> e.word);
        }

    }

    private class TrieValues extends AbstractCollection<V> {

        @Override
        protected boolean equals(Collection<?> that) {
            throw new UnsupportedOperationException(); //TODO
        }

        @Override
        public Iterator<V> iterator() {
            final MutableList<TrieEntry<V>> wordEntries = MutableList.create(10);
            root.collectWords(wordEntries::add);
            return Iterators.transform(wordEntries.iterator(), e -> e.value);
        }

    }

}
