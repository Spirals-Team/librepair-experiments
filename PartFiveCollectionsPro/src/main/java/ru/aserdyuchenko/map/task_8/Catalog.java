package ru.aserdyuchenko.map.task_8;

import java.util.Iterator;

/**
 * @author anton.
 */
public class Catalog<T, V> implements Iterable<V> {
    private Object[] values;
    private Object[] keys;

    Catalog() {
        this.values = new Object[0];
        this.keys = new Object[0];
    }

    public boolean insert(T key, V value) {
        try {
            int arrayIndex = key.hashCode();
            if (needNewSize(arrayIndex, this.keys) || needNewSize(arrayIndex, this.values)) {
                createNewSizeForKey(arrayIndex);
                createNewSizeForValue(arrayIndex);
            }
            if (isOrigin(key)) {
                keys[arrayIndex] = key;
                values[arrayIndex] = value;
            } else return false;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isOrigin(T key) {
        int valueIndex = key.hashCode();
        return (keys[valueIndex] != null && keys[valueIndex].equals(key)) ? false : true;
    }

    /**
     * Check size.
     * @return result of ckeck.
     */
    private boolean needNewSize(int arrayIndex, Object[] objects) {
        return arrayIndex > objects.length - 1;
    }

    /**
     * Create new size for container.
     */
    private void createNewSizeForKey(int newSize) {
        Object[] newObject = new Object[newSize + 2];
        System.arraycopy(this.keys, 0, newObject, 0, this.keys.length);
        this.keys = newObject;
    }

    private void createNewSizeForValue(int newSize) {
        Object[] newObject = new Object[newSize + 2];
        System.arraycopy(this.values, 0, newObject, 0, this.values.length);
        this.values = newObject;
    }

    public V get(T key) {
        return (V) values[key.hashCode()];
    }

    public boolean delete(T key) {
        try {
            keys[key.hashCode()] = null;
            values[key.hashCode()] = null;
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                while (values[currentIndex] == null && currentIndex < values.length - 1) {
                    currentIndex++;
                }
                return values[currentIndex++] != null ? true : false;
            }

            @Override
            public V next() {
                while (values[currentIndex] == null && currentIndex < values.length - 1) {
                    currentIndex++;
                }
                return (V) values[currentIndex++];
            }
        };
    }
}
