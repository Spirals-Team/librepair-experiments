package ru.job4j.startcollection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Класс будет содржать два метода
 * Конвертация двумерного массива в ArrayList и наоборот [#10035]
 *
 * @author Alexander KAleganov
 * @since V1
 */
public class ConvertList {

    /**
     * Идём по циклу форэйч и суём все элементы в лист
     *
     * @param array
     * @return
     */
    public List<Integer> toList(int[][] array) {
        List<Integer> result = new ArrayList<>();
        for (int[] circleOne : array) {
            for (Integer circleTwo : circleOne) {
                result.add(circleTwo);
            }
        }
        return result;
    }

    /**
     * Лист в массив, если элементов больше то заполним нулями
     * чтото просто... может это затишье перед бурей?)) на массивах я вроде ручку поднабил..
     *
     * @param list
     * @param rows
     * @return
     */
    public int[][] toArray(List<Integer> list, int rows) {
        int[][] result = new int[rows][rows];
        if (list.size() - (int) Math.pow(rows, 2) > 0) {
            for (int j = 0; j < (list.size() - (int) Math.pow(rows, 2)); j++) {
                list.add(0);
            }
        }
        Iterator<Integer> iterator = list.iterator();
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = iterator.next();
            }
        }
        return result;
    }
}
