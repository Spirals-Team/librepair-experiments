package ru.job4j.convert;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс для листа в 2д массив и обратно.
 * @author Alex Rumyantcev
 * @version $Id$
 */
public class ConvertList {

    /**
     * Метод для перевода списка массивов в единый список.
     * @param list список массивов
     * @return результирующий список
     */
    public static List<Integer> Convert ( final List<int[]> list) {
        List<Integer> result = new ArrayList<>();
        for (int[] array: list) {
            for (int i = 0; i < array.length; i++) {
                result.add(array[i]);
            }
        }
        return result;
    }

    /**
     * Метод для перевода 2д массива в список.
     * @param array 2д массив
     * @return полученный список
     */
    public static List<Integer> toList (final int[][] array) {
        // создаем список
        List<Integer> result = new ArrayList<Integer>();
        // перебераем элементы массива
        for (int[] i: array) {
            for (int j: i) {
                result.add(j);
            }
        }
        return result;
    }

    /**
     * Метод для перевода списка в 2д массив.
     * @param list список
     * @param rows количество строк в массиве
     * @return поученный массив
     */
    public static int[][] toArray (final List<Integer> list, final int rows) {
        // расчитываем размеры массива
        // количество строк
        final int sizer = rows;
        // количество столбцов
        final int sizec = (list.size() % rows) > 0 ? list.size() / rows + 1 : list.size() / rows;
        // создаем массив
        int[][] result = new int[sizer][sizec];
        // счетчик элементов в списке
        int counter = 0;
        // перебераем элементы в списке
        for (Integer i: list) {
            result[counter / sizec][counter % sizec] = i;
            counter++;
        }
        return result;
    }

}
