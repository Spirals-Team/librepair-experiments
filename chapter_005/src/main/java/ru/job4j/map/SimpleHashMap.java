package ru.job4j.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashMap<K, V> implements Iterable<SimpleHashMap.Node<K, V>> {

    private Node<K, V>[] table;
    private int capacity;
    private int size = 0;
    private final float loadFactor = 0.75f;

    @Override
    public Iterator<SimpleHashMap.Node<K, V>> iterator() {
        return new Iterator<Node<K, V>>() {

            int countNode = 0;
            int index = 0;
            Node<K, V> node = null;

            @Override
            public boolean hasNext() {
                return size > countNode;
            }

            @Override
            public Node<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                if (node != null && node.next != null) {
                    node = node.next;
                } else {
                    for (; index < table.length; index++) {
                        if (table[index] != null) {
                            node = table[index++];
                            break;
                        }
                    }
                }
                countNode++;
                return node;
            }
        };
    }

    @SuppressWarnings("unchecked")
    public SimpleHashMap(int capacity) {
        this.capacity = capacity;
        this.table = new Node[capacity];
    }

    // hash function
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        return Math.abs(key.hashCode()) % capacity;
    }

    // expand array of table
    @SuppressWarnings("unchecked")
    private void expandTable() {
        capacity += capacity;
        Node<K, V>[] newTable = new Node[capacity];
        size = 0;
        for (int i = 0; i < table.length; i++) {
            int index;
            if (table[i] != null) {
                index = hash(table[i].key);

                if (newTable[index] == null) {
                    newTable[index] = table[i];
                    size++;
                } else {
                    addNode(table[i], index, newTable);
                }
            }
        }
        table = newTable;
    }

    // replace value of node if keys are equal
    private V replaceNode(Node<K, V> node, int index, Node<K, V>[] table) {
        for (Node<K, V> e = table[index]; e != null; e = e.next) {
            V oldValue = e.value;
            if (e.key == node.key || node.key.equals(e.key)) {
                e.value = node.value;
                return oldValue;
            }
        }
        return null;
    }

    // add node in start of linked list
    private V addNodeInStart(Node<K, V> node, int index, Node<K, V>[] table) {
        V oldValue = table[index].value;
        node.next = table[index];
        table[index] = node;
        return oldValue;
    }

    // add node in list
    private V addNode(Node<K, V> node, int index, Node<K, V>[] table) {
        V oldValue = replaceNode(node, index, table);

        if (oldValue == null) {
            oldValue = addNodeInStart(node, index, table);
            size++;
        }
        return oldValue;
    }

    // add element in map
    public V insert(K key, V value) {
        Node<K, V> node = new Node<>(key, value, null);
        int index = hash(key);

        if (size >= (int) (capacity * loadFactor)) {
            expandTable();
        }

        if (table[index] == null) {
            table[index] = node;
            size++;
        } else {
            return addNode(node, index, table);
        }
        return null;
    }

    // get value by key
    public V get(K key) {
        Node<K, V> node = table[hash(key)];

        if (node != null) {
            if (node.next == null && (node.key == key || node.key.equals(key))) {
                return node.value;
            } else {
                for (Node<K, V> e = node; e != null; e = e.next) {
                    if (e.key == key || e.key.equals(key)) {
                        return e.value;
                    }
                }
            }
        }
        return null;
    }

    // delete element by key
    public boolean delete(K key) {
        int index = hash(key);
        Node<K, V> node = table[index];

        if (node != null) {
            if (node.next == null && (node.key == key || node.key.equals(key))) {
                table[index] = null;
                size--;
                return true;
            } else {
                Node<K, V> prev = node;
                for (Node<K, V> e = node; e != null; e = e.next) {
                    if (e.key == key || e.key.equals(key)) {
                        prev.next = e.next;
                        e.next = null;
                        size--;
                        return true;
                    }
                    prev = e;
                }
            }
        }
        return false;
    }

    static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Node<?, ?> node = (Node<?, ?>) o;

            if (value != null ? !value.equals(node.value) : node.value != null) {
                return false;
            }
            return key != null ? !key.equals(node.key) : node.key != null;
        }

        @Override
        public int hashCode() {
            int result = value != null ? value.hashCode() : 0;
            result = 31 * result + (key != null ? key.hashCode() : 0);
            return result;
        }
    }
}
