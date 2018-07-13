package ru.job4j.perfom;

import java.util.*;

/**
 * Класс для измерения длительности опрераций добавления и удаления  в коллекциях
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class ComparPerfom {

    // множество символов для генерации строк
    private static final String CHAR_LIST = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    // длина генерируемой строки
    private static final int RANDOM_STRING_LENGTH = 10;
    // количество значений в тестовом массиве
    private static final int RANDOM_STRING_COUNT = 100000;

    // тестовый массив
    private static  final String[] VALUES = new String [RANDOM_STRING_COUNT];

    // заполняем тестовый массив случайными строками
    static {
            for (int i = 0; i < RANDOM_STRING_COUNT; i++) {
                VALUES[i] = genRandomString();
            }
    }

    /**
     * Генерация случайной строки
     * @return случайно сгенерированная строка
     */
    private static String genRandomString() {
        Random randomGenerator = new Random();
        StringBuffer randStr = new StringBuffer();
        for (int i = 0; i < RANDOM_STRING_LENGTH; i++) {
            randStr.append(CHAR_LIST.charAt(randomGenerator.nextInt(CHAR_LIST.length())));
        }
        return randStr.toString();
    }

    /**
     * Добавление элементов в коллекцию с подсчетом затраченного времени
     * @param collection коллекция
     * @param amount количество элементов для добавления
     * @return затраченное время в милисекундах
     * @throws NullPointerException передана пустая ссылка на коллекцию
     * @throws NoValidAmountException количество элементов меньше 1 или больше количества значений в тестовом массиве
     */
    public static long add(final Collection<String> collection, final int amount) throws NullPointerException, NoValidAmountException {
        long start = System.currentTimeMillis();
        if (amount < 1 || amount > RANDOM_STRING_COUNT) {
            throw new NoValidAmountException();
        }
        for (int i = 0; i < amount - 1; i++) {
            collection.add(VALUES[i]);
        }
        return System.currentTimeMillis() - start;
    }

    /**
     * Удаление элементов в коллекцию с подсчетом затраченного времени
     * @param collection коллекция
     * @param amount количество элементов для удаления
     * @return затраченное время в милисекундах
     * @throws NullPointerException передана пустая ссылка на коллекцию
     * @throws NoValidAmountException количество элементов меньше 1 или больше количества значений в тестовом массиве
     */
    public static long delete (final Collection<String> collection, final int amount) throws NullPointerException, NoValidAmountException {
        long start = System.currentTimeMillis();
        if (amount < 1 || amount > RANDOM_STRING_COUNT) {
            throw new NoValidAmountException();
        }
        for (int i = 0; i < amount - 1; i++) {
            collection.remove(VALUES[i]);
        }
        return System.currentTimeMillis() - start;
    }

    public static void main(final String args[] ) throws NullPointerException, NoValidAmountException {

        ArrayList<String> arlist = new ArrayList<>();
        LinkedList<String> lilist = new LinkedList<>();
        TreeSet<String> trset = new TreeSet<>();
        final int amount = 99000;

        System.out.println("ArrayList");
        System.out.println(String.format("Заливка %d строк занимает %d милисекунд", amount, add(arlist, amount)));
        System.out.println(String.format("Удаление %d строк занимает %d милисекунд", amount, delete(arlist, amount)));
        System.out.println("------------------");
        System.out.println("LinkedList");
        System.out.println(String.format("Заливка %d строк занимает %d милисекунд", amount, add(lilist, amount)));
        System.out.println(String.format("Удаление %d строк занимает %d милисекунд", amount, delete(lilist, amount)));
        System.out.println("------------------");
        System.out.println("TreeSet");
        System.out.println(String.format("Заливка %d строк занимает %d милисекунд", amount, add(trset, amount)));
        System.out.println(String.format("Удаление %d строк занимает %d милисекунд", amount, delete(trset, amount)));
        System.out.println("------------------");


    }
}
