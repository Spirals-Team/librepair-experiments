package ru.job4j.words;

import java.util.HashMap;

/**
 * Класс сравнивает два слова между собой на наличие одинаковых букв, раскидав первое слово в hashmap, где ключ это символ, а значение -
 * количество этих символов в слове
 */
public class HashWords {

    /**
     * Метод сравнивает два слова
     * @param word первое словаа
     * @param otherword второе слово
     * @return результат сравнения
     */
    public static boolean compareTwoWords(final String word, final String otherword) {
        HashMap<Character, Integer> array = new HashMap<>();
        word.chars().mapToObj(c -> (char) c).forEach(s -> array.put(s, array.containsKey(s) ? array.get(s) + 1 : 1));
        otherword.chars().mapToObj(c -> (char) c).forEach(s -> array.put(s, array.get(s) - 1));
        return array.values().stream().filter(s -> s != 0).findFirst().orElse(0) == 0;
    }

    }
