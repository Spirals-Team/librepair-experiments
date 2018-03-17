package ru.job4j.collections;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Word.
 * @author Hincu Andrei (andreih1981@gmail.com)on 21.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class ContainsWord {
    /**
     * Метод проверяет состоит ли первое слово из букв второго.
     * @param a первое слово.
     * @param w ыторое слово.
     * @return да или нет.
     */
    public boolean contains(String a, String w) {
        boolean contains = false;
        if (a.length() == w.length()) {
            HashMap<String, Integer> map = new HashMap<>();
            String[] charsA = a.split("");
            for (String s : charsA) {
                Integer absent = map.putIfAbsent(s, 1);
                if (null != absent) {
                    map.put(s, map.get(s) + 1);
                }
            }
            String[] charsW = w.split("");
            for (String s : charsW) {
                Integer i = map.get(s);
                if (i  == null) {
                    break;
                } else {
                    if (i > 1) {
                        map.put(s, map.get(s) - 1);
                    } else {
                        map.remove(s);
                    }
                }
            }
            if (map.isEmpty()) {
                contains = true;
            }
        }
        return contains;
    }

    /**
     * Поиск аннограмм.
     * @param a 1 слово.
     * @param b 2 слово.
     * @return да нет.
     */
    public boolean contains2(String a, String b) {
        return sort(a).equals(sort(b));
    }

    /**
     * Отсортированное слово.
     * @param s слово.
     * @return .
     */
    public String sort(String s) {
        char[]a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
