package ru.job4j.arr;

/**
 * этот метод перегоняет один двухмерный массив в
 * два одномерных один чётный второй нет,
 * но вернуть я могу вроде как только 1 массив либо чётный либо не чётный,
 * вопрос можно ли как то получить от одного метода и чётный массив и нечётный,
 * т.к. у нас тут создаётся два необходимых массива
 */
public class Masstwomernarr {

    public static int[] arrmassDinODINod(int[][] arr4) {
        int l = 0;
        for (int i = 0; i < arr4.length; i++) {
            for (int j = 0; j < arr4[1].length; j++) {
                if (arr4[i][j] % 2 > 0) {
                    l++;
                }
            }
        }
        int k = 0;
        int n = 0;
        int[] arr1 = new int[l];
        int[] arr2 = new int[arr4.length * arr4[1].length - l];
        for (int i = 0; i < arr4.length; i++) {
            for (int j = 0; j < arr4[1].length; j++) {
                if (arr4[i][j] % 2 > 0) {
                    arr1[k++] = arr4[i][j];
                } else {
                    arr2[n++] = arr4[i][j];
                }
            }
        }
        return arr1;
    }
}
