package ru.job4j.map;


import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class SimpleMap.
 * @param <K> generic for key
 * @param <V> generic for value
 */
public class SimpleMap<K, V> implements Iterable {
    /**
     * @param DEFAULT_SIZE start size of array
     */
    private static final int DEFAULT_SIZE = 16;
    /**
     * @param LOAD_FACTOR when array is increase
     */
    private static final float LOAD_FACTOR = 0.75f;
    /**
     * @param map array for saving elements
     */
    private Node[] map;
    /**
     * @param size count elements
     */
    private int size;

    /**
     * Constructor.
     */
    public SimpleMap() {
        map = new Node[DEFAULT_SIZE];
    }

    /**
     * Method insert.
     * @param key key
     * @param value value
     * @return result
     */
    public boolean insert(K key, V value) {
        boolean result = false;
        int index = hash(key);
        if (size == (map.length * LOAD_FACTOR)) {
            increaseSize();
        }
        if (map[index] == null) {
            map[index] = new Node(key, value);
            size++;
            result = true;
        } else if (map[index] != null) {
            if (map[index].key.equals(key)) {
                map[index].value = value;
                result = true;
            }
        }
         return result;
    }

    /**
     * Method increaseSize.
     */
    public void increaseSize() {
        Node[] temp = map;
        map = new Node[temp.length * 2];
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == null) {
                continue;
            } else {
                int index = hash((K) temp[i].key);
                map[index] = temp[i];
            }
        }
    }

    /**
     * Method get.
     * @param key key
     * @return value
     */
    public V get(K key) {
       V result = null;
       if (map[hash(key)] != null) {
           result = (V) map[hash(key)].value;
       }
       return result;
    }

    /**
     * Method delete.
     * @param key key
     * @return result deleting
     */
    public boolean delete(K key) {
        boolean result = false;
        int index = hash(key);
        if (map[index] != null) {
            map[index] = null;
            size--;
            result = true;
        }
        return result;
    }

    /**
     * Method iterator.
     * @return Iterator
     */
    @Override
    public Iterator iterator() {
        Iterator result = new Iterator() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                boolean result = false;
                for (int i = index; i < map.length; i++) {
                    if (map[i] != null) {
                        result = true;
                        break;
                    }
                }
                return result;
            }

            @Override
            public V next() {
                V result = null;
                for (int i = index; index < map.length; i++) {
                    index++;
                    if (map[i] != null) {
                        result = (V) map[i].value;
                        break;
                    } else if (index == map.length) {
                        throw new NoSuchElementException();
                    }
                }
                return result;
            }
        };
        return result;
    }

    /**
     * Method hash.
     * @param key key
     * @return return position in array.
     */
    public int hash(K key) {
        int result = key.hashCode();
        result = Math.abs(result) % map.length;
        return result;
    }

    /**
     * Static nested class Node.
     * @param <K> generic for key
     * @param <V> generic for value
     */
    private static class Node<K, V> {
        /**
         * @param key key
         */
        private K key;

        /**
         * @param value value
         */
        private V value;

        /**
         * Constructor.
         * @param key key
         * @param value value
         */
        Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * Method size.
     * @return count of elements
     */
    public int size() {
        return size;
    }
}
