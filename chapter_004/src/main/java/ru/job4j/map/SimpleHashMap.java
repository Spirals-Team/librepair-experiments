package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashMap<K, V> implements Iterable {
    private EntryItems[] array;
    private int defaultSize = 10;
    private int size = 0;
    private int modCount = 0;

    public SimpleHashMap(int capacity) {
        if (capacity < defaultSize) {
            capacity = defaultSize;
        }
        array = new EntryItems[capacity];
    }

    public SimpleHashMap() {
        array = new EntryItems[defaultSize];
    }

    public boolean insert(K key, V value) {
        int factor = array.length;
        if (size > factor * 2 / 3) {
            resize(0);
            factor = array.length;
        }
        int index = myHash(key, factor);
        if (array[index] != null) {
            return false;
        }
        array[index] = new EntryItems(key, value);
        modCount++;
        size++;
        return true;
    }

    private void resize(int bigProblem) {
        EntryItems[] newArray = new EntryItems[array.length * 2 + bigProblem];
        int factor = newArray.length;
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                int index = myHash((K) array[i].key, factor);
                if (newArray[index] != null) {
                    restart(factor);
                } else {
                    newArray[index] = array[i];
                }
            }
        }
        array = newArray;
    }

    private void restart(int factor) {
        resize(factor);
    }

    private int myHash(K key, int factor) {
        if (key == null) {
            return 0;
        }
        int h = key.hashCode();
        h ^= (h >>> 2) ^ (h >>> 3);
        h %= factor;
        return h >= 0 ? h : h * -1;
    }

    public V get(K key) {
        int index = myHash(key, array.length);
        if (array[index] != null && array[index].key.equals(key)) {
            return (V) array[index].value;
        }
        return null;
    }

    public boolean delete(K key) {
        modCount++;
        int index = myHash(key, array.length);
        if (array[index] != null && array[index].key.equals(key)) {
            array[index] = null;
            size--;
            return true;
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return array.length;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            private int iterIndex = 0;
            private int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                while (iterIndex < getCapacity()) {
                    if (array[iterIndex] != null) {
                        return true;
                    } else {
                        iterIndex++;
                    }
                }
                return false;
            }

            @Override
            public V next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("Iterator only for read.");
                }
                if (iterIndex == getCapacity()) {
                    throw new NoSuchElementException("Not more elements.");
                }
                hasNext();
                return (V) array[iterIndex++].value;
            }
        };
    }

    public void see() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                System.out.println("key: " + array[i].key + ", value: " + array[i].value);
            }
        }
    }

    class EntryItems<K, V> {
        private K key;
        private V value;

        public EntryItems(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EntryItems that = (EntryItems) o;
            return Objects.equals(key, that.key) && Objects.equals(value, that.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }
    }
}
