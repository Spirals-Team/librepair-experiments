package ru.job4j.lite.collections;

import java.util.Collection;

/**
 *Класс CollectionsTime.
 *@author ifedorenko
 *@since 06.09.2017
 *@version 1
 */
public class CollectionsTime {
    /**
     * Метод проверяет за сколько происходит операция add в ArrayList, LinkedList, TreeSet.
     * @param collection Коллекция для проверки
     * @param amount кол-во элементов для проверки
     * @return время работы
     */
    public long add(Collection<String> collection, int amount) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            String s = String.valueOf(i);
            collection.add(s);
        }
        long finish = System.currentTimeMillis();
        long result = finish - start;
        return result;
    }
    /**
     * Метод проверяет за сколько происходит операция delete в ArrayList, LinkedList, TreeSet.
     * @param collection Коллекция для проверки
     * @param amount кол-во элементов для проверки
     * @return время работы
     */
    public long delete(Collection<String> collection, int amount) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            String s = String.valueOf(i);
            collection.remove(s);
        }
        long finish = System.currentTimeMillis();
        long result = finish - start;
        return result;
    }
}