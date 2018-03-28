package ru.job4j.twoWords;

import java.util.Arrays;
import java.util.HashMap;

public class Compare {
    private String first;
    private String second;

    public Compare(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public boolean compareFirst() {
        boolean result = false;
        char[] first = this.first.toCharArray();
        char[] second = this.second.toCharArray();
        if (first.length == second.length) {
            for (int i = 0; i < first.length; i++) {
                for (int j = 0; j < first.length; j++) {
                    if (first[i] == second[j]) {
                        result = true;
                        break;
                    }
                    result = false;
                }
            }
        }
        return result;
    }

    public boolean compareSecond() {
        boolean result = true;
        if ( first.length() != second.length() ) {
            return false;
        }
        char[] c1 = first.toCharArray();
        char[] c2 = second.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        String str1 = new String(c1);
        String str2 = new String(c2);
        return str1.equals(str2);
    }

    public boolean compareThird() {
        boolean result = false;
        HashMap<Character, Integer> map = new HashMap<>();
        char[] first = this.first.toCharArray();
        char[] second = this.second.toCharArray();
        if (first.length != second.length)
            return result;
        for (int i = 0; i < first.length; i++) {
            if (!map.containsKey(first[i]))
                map.put(first[i], 1);
            else
                map.put(first[i], map.get(first[i]) + 1);
        }
        for (int i = 0; i < second.length; i++) {
            if (map.containsKey(second[i]))
                map.put(second[i], map.get(second[i]) - 1);
        }
        map.values().removeIf(e -> map.values().iterator().next() == 0);
        if (map.isEmpty())
            result = true;
        return result;
    }
}
