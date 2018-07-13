package ru.job4j.words;

import java.util.Objects;

/**
 * Класс сравнивает два слова между собой, сравнивая суммы хеш кодов символов, составляющих эти слова.
 */
public class words {
    public static boolean compareTwoWords(String word, String otherword) {
        int hashsum = 0;
        boolean result = false;
        char [] w = word.toCharArray();
        char [] ow = otherword.toCharArray();
        if (w.length == ow.length) {
            for ( int i = 0; i < w.length; i++) {
                hashsum = hashsum + Objects.hash(w[i]) - Objects.hash(ow[i]);
            }
            if (hashsum == 0) {
                result = true;
            }
        }
        return result;
    }
}
