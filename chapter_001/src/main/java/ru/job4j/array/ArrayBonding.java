package ru.job4j.array;

public class ArrayBonding {
    public int[] bond(int[] first, int[] second) {
        int firstLength = first.length;
        int secondLength = second.length;
        int result[] = new int[firstLength + secondLength];
        int i = 0, j = 0;
        for (int k = 0; k < result.length; k++) {
            if (i > firstLength - 1) {
                int buffer = second[j];
                result[k] = buffer;
                j++;
            }
            else if (j > secondLength - 1) {
                int buffer = first[i];
                result[k] = buffer;
                i++;
            }
            else if (first[i] < second[j]) {
                int buffer = first[i];
                result[k] = buffer;
                i++;
            }
            else {
                int buffer = second[j];
                result[k] = buffer;
                j++;
            }
        }
        return result;
    }
}
