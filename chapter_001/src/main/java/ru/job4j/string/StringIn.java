package ru.job4j.string;

public class StringIn {
    boolean contains(String origin, String sub) {
        char[] originArr = origin.toCharArray();
        char[] subArr = sub.toCharArray();
        int subLength = sub.length();
        boolean result = false;
        int count = 0;

        for (int i = 0; i < originArr.length; i++) {
            if (result == true) {
                if (count == subLength) {
                    break;
                }
            }
            result = false;
            if (originArr[i] == subArr[count]) {
                result = true;
                count++;
            }
        }
        return result;
    }
}
