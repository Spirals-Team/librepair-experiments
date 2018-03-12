package ru.job4j.collection;

import java.util.*;

/**
 * ConvertList.
 */
public class ConvertList {
    /**
     * Конвертируем в List.
     *
     * @param array принимаем массив int[][].
     * @return вернем ArrayList.
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> integerList = new ArrayList<>();
        for (int[] a : array) {
            for (int x : a) {
                integerList.add(x);
            }
            System.out.println();
        }
        return integerList;
    }

    /**
     * Метод toArray. Конвертирует из List в двмерный массив в n количество строк.
     * @param list List<Integer>.
     * @param rows количество строк.
     * @return двумерный массив.
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int length;
        Iterator<Integer> iterator = list.iterator();
        if (list.size() % rows == 0) {
            length = list.size() / rows;
        } else {
            length = (list.size() / rows) + 1;
        }

        int[][] array = new int[rows][length];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < length; j++) {
                if (iterator.hasNext()) {
                    Integer temp = iterator.next();
                    if (temp != null) {
                        array[i][j] = temp;
                    } else {
                        array[i][j] = 0;
                    }
                }
            }
        }
        return array;
    }

    /**
     * Метод convert. Конвертирует из списка массивов. В обычный список.
     * @param list List<int[]>.
     * @return List<Integer>.
     */
    public List<Integer> convert(List<int[]> list) {
        List<Integer> resault = new ArrayList<>();
        Iterator<int[]> iterator = list.iterator();
        while (iterator.hasNext()) {
            int[] array = iterator.next();
            if (array != null) {
                for (int i = 0; i < array.length; i++) {
                    resault.add(array[i]);
                }
            }
        }
        return resault;
    }
}
