package ru.job4j.collections.map;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Класс справочник.
 * @author Hincu Andrei (andreih1981@gmail.com)on 16.10.2017.
 * @version $Id$.
 * @since 0.1.
 * @param <T> тип ключа.
 * @param  <V> тип значения.
 */
public class Catalog<T, V> implements Iterable {
    /**
     * Начальный размер каталога.
     */
    private int size = 16;
    /**
     * Каталог пары ключь значение.
     */
    private Entry<T, V>[] table;
    /**
     * колличество занятых ячеек.
     */
    private int pozition = 0;
    /**
     * Коэффициент загрузки. Без него сплошные колизии.
     */
    private int temp = (int) (size * 0.75);
    /**
     * Конструктор.
     */

    public Catalog() {
        this.table = new Entry[size];
    }

    /**
     * Итератор.
     * @return возможность перебора.
     */
    @Override
    public Iterator iterator() {
        return new Iterator() {
            private Entry<T, V> e;
            private int index = 0;
            private int i = 0;

            /**
             * Есть ли еще элементы.
             * @return true or false.
             */
            @Override
            public boolean hasNext() {
                return index < pozition;
            }

            /**
             * Следующий элемент.
             * @return элемент.
             */
            @Override
            public Catalog.Entry<T, V> next() {
                if (hasNext()) {
                    for (; i < table.length; i++) {
                        if (table[i] != null) {
                            e = table[i];
                            index++;
                            break;
                        }
                    }
                    this.i =  i + 1;
                    return e;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    /**
     * Внутренний класс.
     * @param <T> тип ключа.
     * @param <V> тип значения.
     */
    static class Entry<T, V> {
        /**
         * хэш рассчитанный из хэшкода ключа.
         */
        private int hash;
        /**
         * Ключ.
         */
        private T key;
        /**
         * Значение.
         */
        private V val;

        /**
         * Конструктор.
         * @param hash хэш.
         * @param key ключ.
         * @param val значение.
         */
        private Entry(int hash, T key, V val) {
            this.hash = hash;
            this.key = key;
            this.val = val;
        }

        /**
         * геттер.
         * @return ключ.
         */
        public T getKey() {
            return key;
        }

        /**
         * геттер.
         * @return значение.
         */
        public V getValue() {
            return val;
        }
    }

    /**
     * Медод добовляет новую пару ключь и значение.
     * @param key ключ.
     * @param value значение.
     * @return true если такой элемент содержится тогда false.
     */
    public boolean insert(T key, V value) {
        boolean success = false;
        if (temp == pozition) {
            transfer(table);
        }
        if (key == null) {
            putForNullKey(table, value);
            success = true;
            pozition++;
        } else {
            int hash = hash(key.hashCode());
            int index = indexFor(hash, size);
            if (table[index] == null) {
                addNewEntry(hash, key, value, index, table);
                success = true;
                pozition++;
            } else {
                Entry<T, V> oldElement = table[index];
                if (oldElement.key.equals(key)) {
                    oldElement.val = value;
                    success = true;
                }
            }
        }

        return success;
    }

    /**
     * При заполнении увеличивает размер хранилища в два раза.
     * @param entry прежнее хранилище.
     */
    private void transfer(Entry<T, V>[] entry) {
        int newSize = this.size * 2;
        Entry[] newTable = new Entry[newSize];
        for (int i = 0; i < entry.length; i++) {
            if (entry[i] != null) {
                Entry<T, V> element = entry[i];
                if (element.key == null) {
                    putForNullKey(newTable, element.val);
                } else {
                    int newIndex = indexFor(element.hash, newSize);
                    addNewEntry(element.hash, element.key, element.val, newIndex, newTable);
                }
            }
        }
        this.size = newSize;
        this.table = newTable;
        this.temp = (int) (size * 0.75);
    }

    /**
     * Метод добовляет новуя ячейку.
     * @param hash хэш .
     * @param key ключ.
     * @param value значение.
     * @param index позиция в хранилище.
     * @param table хранилище.
     */
    private void addNewEntry(int hash, T key, V value, int index, Entry[]table) {
        Entry<T, V> newElement = new Entry<>(hash, key, value);
        table[index] = newElement;
    }

    /**
     * Метод добалвяет элемент с ключом null.
     * @param table хранилище.
     * @param val значение.
     */
    private void putForNullKey(Entry[]table, V val) {
        Entry<T, V> newNullKeyElement = new Entry<>(0, null, val);
        table[0] = newNullKeyElement;
    }

    /**
     * Получаем значение по ключу.
     * @param key ключ.
     * @return значение.
     */
    public V get(T key) {
        V val = null;
        if (key == null) {
            if (table[0] != null) {
                val = table[0].val;
            }
        } else {
            int hash = hash(key.hashCode());
            int index = indexFor(hash, size);
            if (table[index] != null) {
                val = table[index].val;
            }
        }
        return val;
    }

    /**
     * Удаляем элемент по ключу.
     * @param key ключ.
     * @return true or false если такой элемент не содержится.
     */
    public boolean delete(T key) {
        boolean f = false;
        if (key == null) {
            if (table[0] != null) {
                table[0] = null;
                f = true;
                pozition--;
            }
        } else {
            int hash = hash(key.hashCode());
            int index = indexFor(hash, size);
            if (table[index] != null) {
                table[index] = null;
                f = true;
                pozition--;
            }
        }
        return f;
    }
    /**
     * Метод hash(key) гарантирует что полученные хэш-коды,
     * будут иметь только ограниченное количество коллизий
     * (примерно 8, при дефолтном значении коэффициента загрузки).
     * @param h hashCode элемента.
     * @return хэш.
     */
    private static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Определяется позиция в массиве, куда будет помещен элемент.
     * @param h хэш из метода hash().
     * @param length длинна массива.
     * @return позиция в массиве.
     */
    private static int indexFor(int h, int length) {
        return h & (length - 1);
    }

}
