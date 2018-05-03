package ru.job4j.forum;
/**
 * Конвертация ArrayList в двухмерный массив
 */

import java.util.List;

public class ConvertList2Array {
    public int[][] twoArray(List<Integer> list, int rows) {
        int cells = list.size() / rows;
        while (cells * rows < list.size()) {
            cells++;
        }
        int[][] array = new int[rows][cells];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cells; j++) {
                if (index >= list.size()) {
                    array[i][j] = 0;
                } else {
                    array[i][j] = list.get(index++);
                }
            }
        }
        return array;
    }

    public int test() {
        int[][] arr = new int[4][4];
        int max = 0;
        int stroka = 0;
        //аполняем массив может тебе это не понадобится делал для наглядности
        for (int i = 0; i < arr.length; i++) {
            System.out.println(" " + "\n");
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = 0 + (int) (Math.random() * 10);
                System.out.print(arr[i][j] + "    ");
            }
        }
        //ищем максимальное число в диаганале матрицы

        for (int i = 0, j = arr.length - 1; i < arr.length && j >= 0; i++, j--) {
            if (arr[i][i] > max && arr[i][i] > arr[j][i]) {
                max = arr[i][i];
                stroka = i;
            } else if (arr[j][i] > max && arr[j][i] > arr[i][i]) {
                max = arr[j][i];
                stroka = j;
            }
        }
        System.out.println("\n" + "Вывожу строку в которой находится максимум" + "\n");

        for (int i = 0; i < arr[stroka].length; i++) {
            System.out.print(arr[stroka][i] + "  ");
        }
        System.out.println("\n" + "Вывожу максимальное значение " + max);
        return max;
    }

    public static void main(String[] args) {
        ConvertList2Array convertList2Array = new ConvertList2Array();
        convertList2Array.test();
    }
}
