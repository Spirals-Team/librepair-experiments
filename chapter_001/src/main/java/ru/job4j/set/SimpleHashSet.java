package ru.job4j.set;

import ru.job4j.List.LinkList;

/**
 * Класс представляет собой множество с хеш таблицей типа E с базовыми функциями
 * Коллизии решаются методом цепочек
 * @param <E> необходимый тип данных
 */
public class SimpleHashSet<E> {

    // количество ячеек в хеш-таблице
    private static final int BUCKETS = 10;
    // хеш таблица
    private LinkList<E> [] hashtable = new LinkList[BUCKETS];
    // размер множества
    private int size = 0;

    /**
     * Метод определяет в какую ячейку хеш таблицы нужно разместить текущий объект
     * @param value текущий объект
     * @return номер ячейки в хеш таблице
     */
    private int convertHash(E value) {
        return Math.abs(value.hashCode() % BUCKETS);
    }

    public int size() {
        return size;
    }

    /**
     * Метод добавляет объект хранилище, делая проверку на уникальность
     * @param value текущий объект
     * @return успех добавления
     */
     public boolean add (E value) {
        boolean result = false;
        LinkList <E> temp = new LinkList<>();
        if (! contains(value)){
            if (hashtable[convertHash(value)] != null) {
                hashtable[convertHash(value)].add(value);
            } else {
                temp.add(value);
                hashtable[convertHash(value)] = temp;
            }
            size++;
            result = true;
        }
        return result;
    }

    /**
     * Метод проверяет, содержиться ли объект в хранилище
     * @param value текущий объект
     * @return успех нахождения объекта
     */
    public boolean contains (E value) {
        boolean result = false;
        if (hashtable[convertHash(value)] != null) {
            for (E obj: hashtable[convertHash(value)]) {
                if (obj.equals(value)) {
                    result = true;
                    break;
               }
            }
        }
        return result;
    }

    /**
     * Метод удаляет объект из хранилища
     * @param value текущий объект
     * @return успех удаления объекта
     */
    public boolean remove (E value) {
        boolean result = false;
        if (contains(value)){
            hashtable[convertHash(value)].remove(value);
            size--;
            result = true;
        }
        return result;
    }

}
