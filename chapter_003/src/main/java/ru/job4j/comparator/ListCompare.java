package ru.job4j.comparator;

import java.util.Comparator;

public class ListCompare implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        int index = Math.min(left.length(), right.length());
        int result = 0;
        for (int i = 0; i < index; i++) {
            result = Integer.compare(left.charAt(i), right.charAt(i));
            if (result != 0) {
                break;
            } else {
                result = left.length() - right.length();
            }
        }
        return result;
    }
}
            
