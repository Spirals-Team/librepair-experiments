package ru.job4j.collection;

import java.util.*;

/**
 * Класс TimeCollection.
 */
public class TimeCollection {
    /**
     * Хреним начальное время.
     */
    private long startTime = 0;
    /**
     * Храним время окончания.
     */
    private long endTime = 0;

    /**
     * Метод добаления данных в Collection.
     * @param collection принимаем реализацию коллекции.
     * @param amount принимаем количество добалений в коллекцию.
     * @return вернем время операции.
     */
    public long add(Collection<String> collection, int amount) {
        this.startTime = new Date().getTime();
        for (int i = 0; i < amount; i++) {
            collection.add(i + "Тест.");
        }
        this.endTime = new Date().getTime();
        return endTime - startTime;
    }

    /**
     * Метод удаления данных из Collection.
     * @param collection принимаем реализацию коллекции.
     * @param amount принимаем количество добалений в коллекцию.
     * @return вернем время операции.
     */
    public long delete(Collection<String> collection, int amount) {
        this.startTime = new Date().getTime();
        for (int i = 0; i < amount; i++) {
            collection.remove(i + "Тест.");
        }
        this.endTime = new Date().getTime();
        return endTime - startTime;
    }

    /**
     * Старт программы.
     * @param args аргуметы.
     */
    public static void main(String[] args) {
        //Объект класса TimeCollection.
        TimeCollection timeCollection = new TimeCollection();
        //Обьекты коллекции.
        Collection<String> stringArrayList = new ArrayList<>();
        Collection<String> stringLinkedList = new LinkedList<>();
        Collection<String> stringTreeSet = new TreeSet<>();
        //Вывод на в консоль.
        System.out.println("ArrayList");
        System.out.println(timeCollection.add(stringArrayList, 100000));
        System.out.println(timeCollection.delete(stringArrayList, 100000));
        System.out.println("LinkedList");
        System.out.println(timeCollection.add(stringLinkedList, 100000));
        System.out.println(timeCollection.delete(stringLinkedList, 100000));
        System.out.println("TreeSet");
        System.out.println(timeCollection.add(stringTreeSet, 100000));
        System.out.println(timeCollection.add(stringTreeSet, 100000));
    }
}
