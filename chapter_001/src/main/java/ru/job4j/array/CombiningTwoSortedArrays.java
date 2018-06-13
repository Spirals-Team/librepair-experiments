package ru.job4j.array;

public class CombiningTwoSortedArrays {
    public int[] array(int[] first, int[] second) {
        int[] result = new int[first.length + second.length];
        int firstIndex = 0;
        int secondIndex = 0;
        int index = 0;

        while (index < result.length) {
            result[index] = first[firstIndex] < second[secondIndex] ? first[firstIndex++] : second[secondIndex++];
            if (firstIndex == first.length) {
                System.arraycopy(second, secondIndex, result, ++index, second.length - secondIndex);
                break;
            }
            if (secondIndex == second.length) {
                System.arraycopy(first, firstIndex, result, ++index, first.length - firstIndex);
                break;
            }
            index++;
        }
        return result;
    }
}