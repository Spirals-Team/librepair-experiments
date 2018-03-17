package ru.job4j.collections.set;

/**
 * Попытка написать свой HashSet.
 * @author Hincu Andrei (andreih1981@gmail.com) by 13.10.17;
 * @version $Id$
 * @since 0.1
 * @param <E> тип данных.
 */
public class SimpleHashSet<E> extends SimpleSet<E> {
    /**
     * Массив корзин.
     */
    private Entry[] table;
    /**
     * Начальный размер.
     */
    private int size = 16;
    /**
     * Коэфициент загрузки.
     */
    private int threshold = (int) (size * 0.75);
    /**
     * Колличество содержащихся элементов.
     */
    private int position;

    /**
     * Конструктор.
     */
    public SimpleHashSet(int size) {
        super(size);
        this.table = new Entry[size];
    }



    /**
     * Вложенный класс для создания связанных списков при кализии.
     * @param <E>
     */
    static class Entry<E> {
        /**
         * Хэш элемента.
         */
        private int hash;
        /**
         * Значение элемента.
         */
       private E value;
        /**
         * Ссылка на следующий элемент в корзине.
         */
       private Entry<E> next;

        /**
         * Конструктор.
         * @param hash хэш.
         * @param value значение.
         * @param next следующий элемент.
         */
       private Entry(int hash, E value, Entry<E> next) {
            this.hash = hash;
            this.value = value;
            this.next = next;
        }
    }

    /**
     * Метод добавления нового элемента.
     * @param value новый элемент.
     */
    public void put(E value) {
        if (position == threshold) {
            int newSize = size * 2;
            Entry[] newTable = new Entry[newSize];
            transfer(newTable);
            table = newTable;
            size = newSize;
        }
        if (value == null) {
            putForNullKey(table);
        } else {
            int hash = hash(value.hashCode());
            int index = indexFor(hash, size);
            addEntry(hash, value, index, table);
        }
        position++;
    }

    /**
     * Метод hash(key) гарантирует что полученные хэш-коды,
     * будут иметь только ограниченное количество коллизий
     * (примерно 8, при дефолтном значении коэффициента загрузки).
     * @param h hashCode элемента.
     * @return хэш.
     */
    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Определяется позиция в массиве, куда будет помещен элемент.
     * @param h хэш из метода hash().
     * @param length длинна массива.
     * @return позиция в массиве.
     */
    static int indexFor(int h, int length) {
        return h & (length - 1);
    }

    /**
     * Метод добавляет ключ со значением null в корзину с нулевым индексом.
     * @param table массив корзин.
     */
    public void putForNullKey(Entry[] table) {
        if (table[0] == null) {
            table[0] = new Entry(0, null, null);
        } else {
            Entry<E> e = table[0];
            if (!found(e, 0, null)) {
                table[0] = new Entry<>(0, null, e);
            }
        }
    }

    /**
     * Метод проверяет нет ли в связанном списке такого же элемента.
     * @param e Список элементов.
     * @param hash хэш проверяемого элемента.
     * @param val значение проверяемого элемента.
     * @return содержится или нет.
     */
    public boolean found(Entry<E> e, int hash, E val) {
        Entry<E> element = e;
        if (element.value == null) {
            if (element.hash == hash && val == null) {
                return  true;
            }
        } else if (element.hash == hash && (element.value == val || element.value.equals(val))) {
            return  true;
        }
        if (element.next != null) {
            element = element.next;
            this.found(element, hash, val);
        }
        return false;
    }

    /**
     * Метод добавляет новый список в хранилише.
     * @param hash хэш добовляемого элемента.
     * @param value  сам элемент.
     * @param index индекс в массиве.
     * @param table мвассив в который добовляем данные.
     */
    public void addEntry(int hash, E value, int index, Entry<E>[]table) {
        if (table[index] == null) {
            table[index] = new Entry<>(hash, value, null);
        } else {
            Entry<E> e = table[index];
            if (!found(e, hash, value)) {
                table[index] = new Entry<>(hash, value, e);
            }
        }
    }

    /**
     * Метод расшиляет массив при достижении коэфициента загрузки в два раза.
     * @param newTable массив нового размера с перераспределенными элементами.
     */
    public void transfer(Entry<E>[] newTable) {
        for (int i = 0; i < size; i++) {
            if (table[i] != null) {
                Entry<E> e = table[i];
                while (true) {
                    if (e.hash == 0 && e.value == null) {
                        putForNullKey(newTable);
                    } else {
                        int index = indexFor(e.hash, newTable.length);
                        addEntry(e.hash, e.value, index, newTable);
                    }
                    if (e.next != null) {
                        e = e.next;
                    } else {
                        break;
                    }
                }
            }
        }
    }

}
