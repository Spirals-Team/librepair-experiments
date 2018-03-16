package ru.job4j.stringcompare;

// Дополнительное тестовое задание.
// Сравнить две строки на одинаковый набор букв, используя коллекцию.

import java.util.HashMap;

public class StringCompare {

    public boolean compare(String first, String second) {

        HashMap<Character, Integer> map = new HashMap<>();
        char[] firstStr = first.toCharArray();
        char[] secondStr = second.toCharArray();

        for (char ch : firstStr) {
            map.merge(ch, 1, (a, b) -> a + b);
        }

        for (char ch : secondStr) {
            if (map.get(ch) == null) {
                return false;
            }
            if (map.get(ch) - 1 == 0) {
                map.remove(ch);
            } else {
                map.put(ch, map.get(ch) - 1);
            }
        }
         return map.isEmpty();
    }
}