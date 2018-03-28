package ru.job4j.set;

public class SimpleHashMap<K, V> {
    private Entry[] table;
    private int size;
    final private double loadFactor = 0.75;

    public SimpleHashMap(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Illegal capacity: " + capacity);
        }
        table = new Entry[capacity];
        size = 0;
    }

    public SimpleHashMap() {
        this(16);
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
        int hash = key.hashCode();
        if (hash < 0) {
            hash *= -1;
        }
        int index = hash % table.length;

        table[index] = null;
    }

    boolean containsValue(V value) {
        int hash = value.hashCode();
        if (hash < 0) {
            hash *= -1;
        }
        int index = hash % table.length;
        if (table[index] != null) {
            return table[index].value == value;
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
    }

    public int hashCode() {
        int hash = (int) (Math.random() * 64000);
        return hash % size;
    }

}