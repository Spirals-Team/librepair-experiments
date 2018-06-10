package ru.job4j.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashMap<K, V>  implements Iterable<V> {

    private Node<K, V>[] hashTable;
    public int size = 0;
    private float threshold;

    public SimpleHashMap() {
        hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }

    /**
     * Метод добавляет элемент с ключем и значением в мапу, если с таким хэщем уже содержится элемент в
     * мапе, то добавления не происходит
     * @param  key, value- ключ, по которому будет высчитываться хэш и значение элемента.
     * @return true если такого элемента еще не было в хэштаблице и его добавление в мапу.
     */
    public boolean insert(K key, V value) {
        boolean result = false;
        Node<K, V> newNode = new Node<>(key, value);
        int index = newNode.hash;
        if (hashTable[index] == null) {
            hashTable[index] = newNode;
            size++;
            result = true;
            if (size + 1 >= threshold) {
                threshold *= 2;
                arrayDoubling();
            }
        }
        return result;
    }

    /**
     * Метод выдает значение элемента по ключу. Сначала рассчитывается хэш ключа, помто проверяется
     * есть ли в мапе элемент с таким хэшем, если есть, то выдает его значение, иначе null
     * @param  key- ключ, по которому будет высчитываться хэш.
     * @return значение элемента, если он найден с мапе, иначе null.
     */
    public V get(K key) {
        V result = null;
        Node<K, V> newNode = new Node<>(key, null);
        int index = newNode.hash;
        if (hashTable[index] != null) {
            result = hashTable[index].value;
        }
        return result;
    }

    /**
     * Метод удаляет элемент с заданным ключем из мапы. Проверяется есть ли элемент с хэшем ключа в
     * мапе, если  есть, то этот элемент зануляем и уменьшаем размер мапы
     * @param  key- ключ, по которому будет высчитываться хэш.
     * @return true, если такой элемент найден в мапе и удален.
     */
    public boolean delete(K key) {
        Node<K, V> newNode = new Node<>(key, null);
        int index = newNode.hash;
        if (hashTable[index] == null) {
            return false;
        } else {
            hashTable[index] = null;
            size--;
            return true;
        }
    }

    /**
     * Метод увеличивает размер мапы в 2 раза. Создаем новый массив большего размера и в него
     * копируем старый с новым распределением элементов
     */
    private void arrayDoubling() {
        Node<K, V>[] oldHashTable = hashTable;
        hashTable = new Node[oldHashTable.length * 2];
        size = 0;
        for (Node<K, V> node : oldHashTable) {
            if (node != null) {
                insert(node.key, node.value);
            }
        }
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            int count = 0;

            @Override
            public boolean hasNext() {
                boolean res = false;
                for (int i = count; i < hashTable.length; i++) {
                    if (hashTable[i] != null) {
                        res = true;
                        count = i;
                        break;
                    }
                }
                return res;
            }

            @Override
            public V next() {
                if (this.hasNext()) {
                    V res = hashTable[count].value;
                    count++;
                    return res;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    private class Node<K, V> {
        private int hash;
        private K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.hash = hash(key);
        }

        private int hash(K key) {
            int hash = 31;
            hash = hash * 17 + hashCode();
            return hash % hashTable.length;
        }

        @Override
        public int hashCode() {
            hash = 31;
            hash = hash * 17 + key.hashCode();
            return hash;
        }
    }
}
