package ru.job4j.twoWords;

import java.util.HashSet;
import java.util.Set;

public class Converter {
    private String first;
    private String second;

    public Converter(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public boolean compareFirstMethod() {
        return first.compareTo(second) == 0;
    }

    public boolean compareSecondMethod() {
        boolean result = false;
        char[] first = this.first.toCharArray();
        char[] second = this.second.toCharArray();
        if (first.length == second.length) {
            for (int i = 0; i < first.length; i++) {
                if (first[i] != second[i]) {
                    result = false;
                    break;
                }
            }
            result = true;
        }
        return result;
    }

    public boolean compareThirdMethod() {
        boolean result = false;
        Set<String> set = new HashSet<>();
        set.add(first);
        set.add(second);
        return set.size() == 1;
    }
}
