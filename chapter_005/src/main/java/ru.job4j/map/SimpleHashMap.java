package ru.job4j.map;

//Ассоциативный массив на базе хэш-таблицы должен быть унифицирован через генерики и иметь методы:
//        boolean insert(K key, V value);
//        V get(K key);
//        boolean delete(K key);
//
//        Реализовывать итератор.
//        Внутренняя реализация должна использовать массив. Нужно обеспечить фиксированное время вставки и получение. Предусмотрите возможность роста хэш-таблицы при нехватке места для нового элемента.

//WORK ON BUGS
//Сергей, обратите внимание что вычисление индекса через поразрядное И эквивалентно, оператору деления по модулю,
//        только в том случае если, делитель является степенью двойки. В текущем варианте ваша реализация генерирует
//        большое число коллизий. Поэтому приведите размер массива к степени двойки и в дальнейшем при увеличении всегда его поддерживайте.
//        Либо используйте оператор деления по модулю. Кроме того, увеличивать хэш-таблицу умножая размер на 31, это расточительно.
//        Увеличивайте на степень двойки и поддерживайте loadFactor тогда.

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

//        Методы разрешения коллизий реализовывать не надо. Например: если при добавлении ключ уже есть, то возвращать false.
public class SimpleHashMap<K, V> implements Iterable<K>{

    private int sizeOfTable = 1;
    private int size = 0;

    final float loadfactor = 0.5f;


    private Node<K, V>[] table;

    @Override
    public Iterator iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<K> {

        int cursor = -1;

        @Override
        public boolean hasNext() {
            return getNextCursor(cursor) < sizeOfTable;
        }

        @Override
        public K next() {

            if (!this.hasNext()) {
                throw new NoSuchElementException();
            } else {
                cursor = getNextCursor(cursor);
                return (K) table[cursor].key;
            }
        }
    }

    int getNextCursor(int cursor) {

        for (int ind = cursor + 1; ind < sizeOfTable; ind++) {

            if (table[ind] != null) {
                return ind;
            }
        }

        return  sizeOfTable;
    }


    class Node<K, V> {
        final int hash;
        K key;
        V value;

        public Node(int hash, K key, V value) {
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
    }

    final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    Node<K, V> newNode(int hash, K key, V value) {
        return new Node<>(hash, key, value);
    }

    boolean insert(K key, V value) {

        boolean returnValue = false;

        int hash = hash(key);

        Node<K,V>[] tab; Node<K,V> p; int n, i;

        if ((tab = table) == null || (n = tab.length) == 0) {
            n = (tab = resize()).length;
        } else if (size >= sizeOfTable * loadfactor) {
            n = (tab = resize()).length;
        }

        if ((p = tab[i = (n - 1) & hash]) == null) {
            tab[i] = newNode(hash, key, value);

            returnValue = true;

        } else {
            Node<K,V> e; K k;

            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k)))) {
                e = p;
                if (e != null) {
                    V oldValue = e.value;
                    e.value = value;

                    returnValue = true;
                }
            }
        }

//        if (++size >= sizeOfTable) {
//            resize();
//        }

        if (returnValue) {
            size++;
        }

        if (size >= sizeOfTable * loadfactor) {
            n = (tab = resize()).length;
        }

        return returnValue;
    }

    final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
        int newCap = (oldCap == 0) ? 1 : oldCap << 1;
//        int newCap = (oldCap == 0) ? 1 : oldCap * 2;
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
        if (oldTab != null) {
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {
                    oldTab[j] = null;
                    newTab[e.hash & (newCap - 1)] = e;
                }
            }
        }
        sizeOfTable = newCap;
        return newTab;
    }

    V get(K key) {
        Node<K,V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
    }

    final Node<K,V> getNode(int hash, Object key) {

        Node<K,V>[] tab; Node<K,V> first, e; int n; K k;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (first = tab[(n - 1) & hash]) != null) {
            if (first.hash == hash && // always check first node
                    ((k = first.key) == key || (key != null && key.equals(k)))) {
                return first;
            }
        }
        return null;
    }

    boolean delete(K key) {
        Node<K,V> e;
//        return (e = removeNode(hash(key), key, null, false, true)) == null ?
//                null : e.value;
        return (e = removeNode(hash(key), key, null, false, true)) == null ?
                false : true;
    }

    final Node<K,V> removeNode(int hash, Object key, Object value,
                               boolean matchValue, boolean movable) {
        Node<K,V>[] tab; Node<K,V> p; int n, index;
        if ((tab = table) != null && (n = tab.length) > 0 &&
                (p = tab[index = (n - 1) & hash]) != null) {
            Node<K, V> node = null, e;K k; V v;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k)))) {
                node = p;
                tab[index] = null;//node.next;
                --size;
                return node;
            }
        }
        return null;
    }
}