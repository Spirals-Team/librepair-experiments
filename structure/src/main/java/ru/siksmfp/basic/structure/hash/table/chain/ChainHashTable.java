package ru.siksmfp.basic.structure.hash.table.chain;

import ru.siksmfp.basic.structure.api.ArrayStructure;
import ru.siksmfp.basic.structure.api.HashTable;
import ru.siksmfp.basic.structure.api.ListStructure;
import ru.siksmfp.basic.structure.array.fixed.FixedArray;
import ru.siksmfp.basic.structure.list.linked.g.GLinkedList;
import ru.siksmfp.basic.structure.utils.math.Math;

import java.util.function.Function;

/**
 * @author Artem Karnov @date 1/30/2018.
 * @email artem.karnov@t-systems.com
 * <p>
 * Hash table with binary probing
 */
public class ChainHashTable<K, V> implements HashTable<K, V> {

    private static final int INITIAL_SIZE = 10000;

    /**
     * Structure for storing key-value structure
     *
     * @param <K> - key of structure
     * @param <V> - value of structure
     */
    private class Node<K, V> {
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<K, V> node = (Node<K, V>) o;
            return key.equals(node.key) && value.equals(node.value);
        }

        @Override
        public int hashCode() {
            return 31 * key.hashCode() + 31 * value.hashCode();
        }
    }

    private ArrayStructure<ListStructure<Node<K, V>>> array;
    private Function<K, Integer> hashFunction;
    private int size;
    /**
     * Default hash function is being applying whe custom function isn't given
     */
    private final Function<K, Integer> DEFAULT_HASH_FUNCTION = t -> {
        int i = t.hashCode();
        i = (i + 0x7ed55d16) + (i << 12);
        i = (i ^ 0xc761c23c) ^ (i >> 19);
        i = (i + 0x165667b1) + (i << 5);
        i = (i + 0xd3a2646c) ^ (i << 9);
        i = (i + 0xfd7046c5) + (i << 3);
        i = (i ^ 0xb55a4f09) ^ (i >> 16);
        return i > 0 ? i : -i;
    };

    /**
     * Constructor with size initialization
     */
    public ChainHashTable(int initialSize) {
        array = new FixedArray<>(Math.getFirstSimpleNumberAfter(initialSize));
        hashFunction = DEFAULT_HASH_FUNCTION;
    }

    /**
     * Constructor with predefined initial size see @INITIAL_SIZE
     */
    public ChainHashTable() {
        array = new FixedArray<>(Math.getFirstSimpleNumberAfter(INITIAL_SIZE));
        hashFunction = DEFAULT_HASH_FUNCTION;
    }

    /**
     * Add key - value structure to hash table
     *
     * @param key   key for adding
     * @param value value for adding
     */
    @Override
    public void add(K key, V value) {
        int index = applyHashing(key);
        if (array.get(index) == null) {
            array.add(index, new GLinkedList<>(new Node<>(key, value)));
        } else {
            array.get(index).add(new Node<>(key, value));
        }
        size++;
    }

    /**
     * Key hashing with given hash function
     *
     * @param key key for hashing
     * @return result of applying hash function on key
     */
    @Override
    public int getHash(K key) {
        return hashFunction.apply(key);
    }

    /**
     * Getting value by key from hash table
     *
     * @param key key for getting
     * @return stored value found by key
     */
    @Override
    public V get(K key) {
        int index = applyHashing(key);
        if (array.get(index) == null) {
            return null;
        } else {
            for (int i = 0; i < array.get(index).size(); i++) {
                if (array.get(index).get(i) != null && array.get(index).get(i).key.equals(key)) {
                    return array.get(index).get(i).value;
                }
            }
        }
        return null;
    }

    /**
     * Setting hash function for hash calculation
     * <p>
     * Be careful setting function after using initialized object of
     * hash table. You can loose your data.
     * Typical use case is set function before hash table using
     *
     * @param hashFunction hash function implementation
     */
    @Override
    public void setHashFunction(Function<K, Integer> hashFunction) {
        this.hashFunction = hashFunction;
    }

    /**
     * Remove key - value structure by key
     *
     * @param key key for deleting
     */
    @Override
    public void remove(K key) {
        int index = applyHashing(key);
        if (array.get(index) != null) {
            for (int i = 0; i < array.get(index).size(); i++) {
                if (array.get(index).get(i).key.equals(key)) {
                    array.get(index).remove(i);
                    size--;
                }
                if (array.get(index).size() == 0) {
                    array.add(index, null);
                    break;
                }
            }
        }
    }

    /**
     * Replacing value of key - value structure on NULL by key
     *
     * @param key key for replacing
     */
    @Override
    public void delete(K key) {
        int index = applyHashing(key);
        if (array.get(index) != null) {
            for (int i = 0; i < array.get(index).size(); i++) {
                if (array.get(index).get(i).key.equals(key)) {
                    array.get(index).delete(i);
                }
            }
        }
    }

    /**
     * Getting number of adding elements
     *
     * @return number of stored elements of hash table
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof ChainHashTable))
            return false;
        ChainHashTable<K, V> table1 = (ChainHashTable<K, V>) o;
        if (size != table1.size) return false;
        if (array.size() != table1.array.size()) return false;

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == null && table1.array.get(i) == null) {
                continue;
            }
            for (int j = 0; j < array.get(i).size(); j++) {
                if (array.get(i).get(j) != null && table1.array.get(i).get(j) == null) {
                    return false;
                }
                if (array.get(i).get(j) == null && table1.array.get(i).get(j) != null) {
                    return false;
                }
                if (array.get(i).get(j) != null) {
                    if (!array.get(i).get(j).equals(table1.array.get(i).get(j))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = 31 * size;
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) != null) {
                for (int j = 0; j < array.get(i).size(); j++) {
                    result += 31 * array.get(i).get(j).hashCode();
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) != null) {
                result.append("{");
                for (int j = 0; j < array.get(i).size(); j++) {
                    if (array.get(i).get(j) != null) {
                        result.append("(")
                                .append(array.get(i).get(j).key)
                                .append(";")
                                .append(array.get(i).get(j).value)
                                .append("), ");
                    }
                }
                result.append("} ,");
            } else {
                result.append("null, ");
            }
        }
        if (result.length() > 1) {
            result.delete(result.length() - 2, result.length());
        }
        return "ChainHashTable{" + result.toString() + "}";
    }

    /**
     * Calculating index of structure in allocated array
     *
     * @param key key for applying
     * @return index of key in allocated array
     */
    private int applyHashing(K key) {
        return hashFunction.apply(key) % array.size();
    }
}
