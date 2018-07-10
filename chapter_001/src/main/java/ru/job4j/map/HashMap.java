package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Iterable hash table based implementation of the <tt>Map</tt> interface.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class HashMap<K, V> implements Map<K, V>, Iterable<HashMap<K, V>.Item<K, V>> {

    private static final int DEFAULT_SIZE = 2;
    private static final double THRESHOLD = .075;
    private static final int MULTIPLIER = 2;

    private Object[] container;
    private Object[] prevContainer;
    private int count = 0;
    private int position = 0;
    private Item current;
    private boolean isResized = false;

    public HashMap() {
        this.container = new Object[DEFAULT_SIZE];
    }

    @Override
    public boolean insert(K key, V value) {
        if (!isResized) {
            resizeIfRequired();
        }
        int hash = getHash(key);
        int idx = getBucketPosition(hash);
        if (container[idx] == null) {
            return addFirstItemToBucket(hash, key, value, idx);
        } else {
            Item<K, V> item = (Item<K, V>) container[idx];
            while (item != null) {
                if (isKeyPresent(key, hash, item)) {
                    return false;
                }
                item = item.next;
            }
        }

        return addItemToBucket(hash, key, value, idx, (Item<K, V>) container[idx]);
    }

    @Override
    public V get(K key) {
        int idx = getBucketPosition(key);
        Item<K, V> item = (Item<K, V>) container[idx];
        checkBucketAilability(item);

        int hash = getHash(key);
        while (item != null) {
            if (isKeyPresent(key, hash, item)) {
                return item.value;
            }
            item = item.next;
        }
        throw new NoSuchElementException();
    }

    @Override
    public boolean delete(K key) {
        int idx = getBucketPosition(key);
        Item<K, V> item = (Item<K, V>) container[idx];
        checkBucketAilability(item);

        int hash = getHash(key);
        Item<K, V> prev = item;
        while (item != null) {
            if (isKeyPresent(key, hash, item)) {
                prev.next = item.next;
                count--;
                return true;
            }
            prev = item;
            item = item.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.count;
    }

    private void resizeIfRequired() {
        int capacity = container.length;
        if (size() >= capacity * THRESHOLD) {
            resize(capacity * MULTIPLIER);
        }
    }

    private void resize(int capacity) {
        isResized = true;
        Object[] temp = container;
        container = new Object[capacity];
        prevContainer = temp;
        count = 0;
        position = 0;
        transfer();
    }

    private void transfer() {
        for (Object bucket : prevContainer) {
            if (bucket == null) {
                continue;
            }
            Item<K, V> item = (Item<K, V>) bucket;
            while (item != null && item.hasNext()) {
                insert(item.key, item.value);
                item = item.next();
            }
        }
        prevContainer = null;
        isResized = false;
    }

    @Override
    public Iterator iterator() {
        position = 0;
        return new Iterator<Item>() {
            @Override
            public boolean hasNext() {
                switchIterator();
                if (size() == 0 || current == null) {
                    return false;
                }
                if (current.hasNext()) {
                    return true;
                }
                return false;
            }

            @Override
            public Item<K, V> next() {
                switchIterator();
                Item<K, V> result = current;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                current = current.next;
                return result;
            }
        };
    }

    private void switchIterator() {
        if (current == null && position < container.length) {
            for (int i = position; i < container.length; i++) {
                Item<K, V> el = (Item<K, V>) container[i];
                if (el != null) {
                    current = el;
                    position++;
                    return;
                }
                position++;
            }
        }
    }

    @Override
    public String toString() {
        Iterator it = this.iterator();
        String result = "";
        while (it.hasNext()) {
            Item<K, V> item = (Item<K, V>) it.next();
            result += item.getKey() + "=" + item.getValue() + ", ";
        }
        if (!result.isEmpty()) {
            result = result.substring(0, result.length() - 2);
        }
        return "{" + result + "}";
    }

    private boolean isKeyPresent(K key, int hash, Item<K, V> item) {
        return item.hash == hash && item.key.equals(key);
    }

    private boolean addItemToBucket(int hash, K key, V value, int idx, Item<K, V> next) {
        container[idx] = new Item<>(hash, key, value, next);
        count++;
        return true;
    }

    private boolean addFirstItemToBucket(int hash, K key, V value, int idx) {
        return addItemToBucket(hash, key, value, idx, null);
    }

    private void checkBucketAilability(Item<K, V> item) {
        if (item == null) {
            throw new NoSuchElementException();
        }
    }

    private int getBucketPosition(K key) {
        return getHash(key) & (container.length - 1);
    }

    private int getBucketPosition(int hash) {
        return hash & (container.length - 1);
    }

    private int getHash(K key) {
        return key.hashCode();
    }

    class Item<K, V> implements Iterator<Item<K, V>> {
        private final int hash;
        private final K key;
        private V value;
        private Item<K, V> next;
        private Item<K, V> current = this;

        public Item(int hash, K key, V value, Item<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return key.toString() + "=" + value.toString();
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item<K, V> next() {
            if (hasNext()) {
                current = current.next;
                return current;
            }
            throw new NoSuchElementException();
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }
}