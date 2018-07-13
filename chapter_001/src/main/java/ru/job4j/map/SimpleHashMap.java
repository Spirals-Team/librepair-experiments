package ru.job4j.map;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс представляет собой реализацию хешмэп с базовыми функциями
 * @param <K> обобщенный тип данных ключа
 * @param <V> обобщенный тип данных значения
 */
public class SimpleHashMap<K, V> implements Iterable<K> {

    // начальное количество "корзин"
    private static final int BACKETS_COUNT = 16;
    // хеш таблица
    private Node<K, V> [] table = new Node [BACKETS_COUNT];
    // количество элементов в хранилище
    private int size = 0;
    // счетчик модификации хранилища
    private int modCount = 0;
    // количество "корзин" в текущий момент
    private int realbackets = BACKETS_COUNT;

    /**
     * Класс описывает базовый узел хранилища
     * @param <K> обобщенный тип данных ключа
     * @param <V> обобщенный тип данных значения
     */
    static class Node<K, V> {
        private K key;
        private V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /**
     * Метод конвертирует значение хеш функции ключа в индекс массива хеш таблицы
     * @param key ключ
     * @param backets величина хеш таблицы
     * @return индекс в таблице
     */
    private int convertHash(K key, int backets) {
        return Math.abs(key.hashCode() % backets);
    }

    /**
     * Метод возвращает текущий размер хешмепа
     * @return размер
     */
    public int size() {
        return size;
    }

    /**
     * Метод конвертирует хеш таблицу в таблицу с другим размером
     * @param newsize новый размер
     */
    public void convertTable(int newsize) {
        Node<K, V> [] newtable = new Node [newsize];
        for (Node<K, V> node: table) {
            if (node != null) {
                newtable [convertHash(node.key, newsize)] = node;
            }
        }
        table = newtable;
        modCount++;
    }

    /**
     * Метод вставляет новый элемент в хранилище
     * @param key ключ элемента
     * @param value значение элемента
     * @return true если элемент вставлен, false если элемент с таким значением индекса хеш таблицы уже существует
     */
    public boolean insert(K key, V value) {
        boolean result = false;
        if (table[convertHash(key, realbackets)] == null) {
            table[convertHash(key, realbackets)] = new Node(key, value);
            modCount++;
            size++;
            result = true;
        }
        if (size > table.length-2) {
            realbackets*=2;
            convertTable(realbackets);
        }
        return result;
    }

    /**
     * Метод возвращает значение элемента по его ключу
     * @param key ключ
     * @return значение элемента, у которого значение индекса хеш таблицы совпадает с полученным из key
     * или null если значение не найдено
     */
    public V get(K key) {
         return table[convertHash(key, realbackets)] != null ? table[convertHash(key, realbackets)].value : null;
    }


    /**
     * Метод удаляет элемент по его ключу (реально - по индексу хеш таблицы)
     * @param key ключ
     * @return успешность удаления
     */
    public boolean delete(K key){
        boolean result = false;
        if (table[convertHash(key, realbackets)] != null ) {
            table [convertHash(key, realbackets)] = null;
            modCount++;
            size--;
            result = true;
        }
        return result;
    }

    @Override
    /**
     * Класс итератора по ключам
     */
    public Iterator<K> iterator() {
        return new Iterator<K>() {
            // счетчик элементов в хешмепе
            int pointer = 0;
            // индекс в хеш таблице с которого надо искать след элемент
            int last = 0;
            int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return pointer < size;
            }

            @Override
            public K next() {
                K result = null;
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                for (int i = last; i < table.length;i++) {
                    if (table [i] != null) {
                        result = table [i].key;
                        pointer++;
                        last = i + 1;
                        break;
                    }
                }
                return result;
            }
        };
    }
}
