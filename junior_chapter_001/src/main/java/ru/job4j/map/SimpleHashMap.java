package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashMap<K, V> implements Iterable {
    private Node<K, V>[] arrayNode;
    private int counts = 0;

    public SimpleHashMap() {
        this.arrayNode = new Node[50];
    }

    public boolean insert(K key, V value) {
        checkContainerSize();
        if (get(key) == null) {
            arrayNode[hash(key)] = new Node<>(key.hashCode(), key, value);
            counts++;
            return true;
        }
        return false;
    }

    public V get(K key) {
        if (arrayNode[hash(key)] != null) {
            return arrayNode[hash(key)].getValue();
        }
        return null;
    }

    public boolean delete(K key) {
        if (arrayNode[hash(key)] != null) {
            arrayNode[hash(key)] = null;
            return true;
        }
        return false;
    }

    private int hash(K key) {
        return key.hashCode() % arrayNode.length;
    }

    private void checkContainerSize() {
        if (this.counts == this.arrayNode.length) {
            Node<K, V>[] newArrayNode = new Node[arrayNode.length * 2];
            for (int i = 0; i < arrayNode.length; i++) {
                int position = arrayNode[i].value.hashCode() % newArrayNode.length;
                newArrayNode[position] = arrayNode[i];
            }
            arrayNode = newArrayNode;
        }
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            int iteratorIndex = 0;

            @Override
            public boolean hasNext() {
                for (int i = iteratorIndex; i < arrayNode.length; i++) {
                    if (arrayNode[i] != null) {
                        iteratorIndex = i;
                        return true;
                    }
                }
                return false;
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    return arrayNode[iteratorIndex++].getHash();
                } else {
                    throw new NoSuchElementException("NoSuchElementException!");
                }
            }
        };
    }

    private class Node<K, V> {
        private int hash;
        private K key;
        private V value;

        public Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }

        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }
}
