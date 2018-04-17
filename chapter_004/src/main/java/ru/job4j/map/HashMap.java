package ru.job4j.map;

import java.util.Iterator;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class HashMap<K, V> implements Iterable<V> {
    private int arraySize;
    private Object[][] hashArray;
    private int elems = 0;

    public HashMap() {
        arraySize = 10;
        hashArray = new Object[arraySize][];
    }

    private HashMap(int arraySize) {
        this.arraySize = arraySize;
        hashArray = new Object[arraySize][];
    }

    public int getElems() {
        return elems;
    }

    public Object[][] getHashArray() {
        return hashArray;
    }

    private int hashFunc(int key) {
        return key % arraySize;
    }

    public boolean insert(K key, V value) {
        int hashValue = hashFunc(positiveKeyHash(key));
        if (hashArray[hashValue] != null) {
            return false;
        }
        hashArray[hashValue] = box(key, value);
        elems++;
        if (checkSize()) {
            System.gc();
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    public V get(K key) {
        int hashVal = hashFunc(positiveKeyHash(key));
        if (hashArray[hashVal] != null) {
            return (V) hashArray[hashVal][1];
        }
        return null;
    }

    public boolean delete(K key) {
        int hashValue = hashFunc(positiveKeyHash(key));
        if (hashArray[hashValue] != null) {
            hashArray[hashValue] = null;
            elems--;
            return true;
        }
        return false;
    }

    private Object[] box(K key, V value) {
        Object[] array = new Object[2];
        array[0] = key;
        array[1] = value;
        return array;
    }

    @SuppressWarnings("unchecked")
    private boolean checkSize() {
        boolean result = false;
        if (elems > 0.5 * arraySize) {
            arraySize *= 2;
            HashMap newHashMap = new HashMap(arraySize);
            for (Object[] item : hashArray) {
                if (item != null) {
                    newHashMap.insert(item[0], item[1]);
                }
            }
            Object[][] newTable = new Object[arraySize][];
            System.arraycopy(newHashMap.getHashArray(), 0, newTable, 0, this.arraySize);
            hashArray = newTable;
            result = true;
        }
        return result;
    }

    private int positiveKeyHash(K key) {
        int keyHash = key.hashCode();
        return keyHash < 0 ? -keyHash : keyHash;
    }

    @Override
    public Iterator<V> iterator() {
        return new HashMapIterator<>(this);
    }
}
