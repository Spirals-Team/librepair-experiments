package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashMap<K, V> implements Iterable<V> {
    private Entry<K, V>[] table;
    private int INITIAL_CAPACITY = 16;
    private int size;
    final private double loadFactor = 0.75;

    public SimpleHashMap() {
        table = new Entry[INITIAL_CAPACITY];
    }

    public V get(K key) {
        int hash = hash(key.hashCode());
        int index = hash % table.length;

        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (hash == entry.hash && (key == entry.key || (key.equals(entry.key)))) {
                return entry.value;
            }
        }
        return null;
    }

    private int hash(int hash) {
        if (hash < 0) {
            hash *= -1;
        }
        return hash;
    }

    private void resize() {
        Entry[] oldTable = table;
        table = new Entry[table.length * 2];

        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] != null) {
                int hash = hash(oldTable[i].key.hashCode());
                int index = hash % (table.length);
                table[index] = oldTable[i];
            }
        }
    }

    V put(K key, V value) {
        if ((table.length * loadFactor) == size) {
            resize();
        }

        int hash = hash(key.hashCode());
        int index = hash % table.length;

        for (Entry<K, V> entry = table[index]; entry != null; entry = entry.next) {
            if (hash == entry.hash && (key == entry.key || (key.equals(entry.key)))) {
                V oldValue = entry.value;
                entry.value = value;
                return oldValue;
            }
        }

        Entry<K, V> entry = new Entry(hash, key, value, table[index]);
        table[index] = entry;
        size++;
        return null;
    }

    void remove(Object key) throws UnsupportedOperationException {
        int hash = hash(key.hashCode());
        int index = hash % table.length;

        table[index] = null;
    }

    boolean containsKey(K key) {
        int hash = key.hashCode();
        if (hash < 0) {
            hash *= -1;
        }
        int index = hash % table.length;
        if (table[index] != null) {
            return table[index].key == key;
        }
        return false;
    }

    static class Entry<K, V> {
        private final int hash;
        private final K key;
        private V value;
        private Entry<K, V> next;

        Entry(int hash, K key, V value, Entry<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public K getKey() {
            return key;
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            Entry<K, V>[] t = table;
            int size = t.length;

            @Override
            public boolean hasNext() {
                boolean result = false;
                for (int i = 0; i < t.length; i++) {
                    if (t[i] != null) {
                        result = true;
                        break;
                    }
                }
                return result;
            }

            @Override
            public V next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                V result = null;
                for (int i = 0; i < t.length; i++) {
                    if (t[i] != null) {
                        int hash = hash(t[i].key.hashCode());
                        int index = hash % table.length;
                        result = t[i].value;
                        t[index] = null;
                        break;
                    }
                }
                return result;
            }
        };
    }

    public int hashCode() {
        int hash = (int) (Math.random() * 64000);
        return hash % size;
    }
}