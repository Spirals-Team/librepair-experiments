package ru.job4j.array;

public class ArrayStringCheck {
    int count;

    boolean contains(String origin, String sub) {
        boolean result = true;
        char[] word = origin.toCharArray();
        char[] value = sub.toCharArray();
            for (int index = 0; index < word.length; index++) {
                if (word[index] != value[index - count]) {
                    count++;
                    if (index == value.length - 1) {
                        result = false;
                        break;

                    }
                }
            }
        return result;
    }
    }
