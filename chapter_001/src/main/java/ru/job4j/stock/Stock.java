package ru.job4j.stock;

import java.util.HashMap;

/**
 * Класс реализует простейший функционал биржи
 */
public class Stock {
    // хранилище стаканов биржи
    private HashMap<String, Glass> glasses = new HashMap<>();

    /**
     * Метод заводит стакан нового эммитента
     * @param book название эммитента
     */
    public void  addBook(String book) {
        if (! glasses.containsKey(book)) {
            glasses.put(book, new Glass(book));
        }
    }

    /**
     * Метод ищет стакан заданного эммитента
     * @param book название эммитента
     * @return найденный стакан
     */
    public Glass getGlassByBook(String book) {
        return glasses.get(book);
    }

}
