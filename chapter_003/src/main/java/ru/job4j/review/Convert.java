package ru.job4j.review;


import java.util.*;

public class Convert {
    /**
     * /**
     * Конструктор для класса Convert.
     * В классе Convert уже определен неявный конструктор по умолчанию.
     * Наличие данного конструктора при отсутсвии других явных конструкторов не обязательно.
     */

    public Convert() {

    }

    /**
     * Метод двумерного массива в лист.
     * Вместо двух цмклов for можно использовать один foreach/
     *
     * @param array входящий массмв.
     * @return лист с данными массива.
     */
    List<Integer> makeList(int[][] array) {
        /*
                ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++)
                list.add(array[i][j]);
        }
        return list;
         */
        List<Integer> list = new ArrayList<>();
        for (int[] in : array) {
            for (Integer out : in) {
                    list.add(out);
            }
        }
        return list;
    }

    /**
     * Метод конввертации листа в двумерный массив.
     * Вместо  переменной rws, лучше использовать более читаемую rows.
     * Вместо  переменной cls, лучше использовать более читаемую cells.
     * Вместо двух циклов в стиле for, удобнее использовать один цикл foreach.
     *
     * @param list входящий лист
     * @param rows переменная количства строк.
     * @return двумерный массив.
     */
    /*
        Iterator<Integer> iterator = list.iterator();
        int cls = list.size() / rows + (list.size() % rows == 0 ? 0 : 1);
        int[][] array = new int[rows][cls];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cls; j++) {
                if (iterator.hasNext())
                    array[i][j] = iterator.next();
                else
                    array[i][j] = 0;
            }
        }
        return array;
    }
     */
    public int[][] makeArray(List<Integer> list, int rows) {
        int cells = list.size() % rows != 0 ? (list.size() / rows) + 1 : list.size() / rows;
        int count = 0;
        int index = 0;
        int[][] array = new int[rows][cells];
        for (Integer in : list) {
            array[count][index++] = in;
            if (index == cells) {
                index = 0;
                count++;
            }
        }
        return array;

    }
}