package ru.job4j.array;
/**
 * RotateArray.
 * @author Hincu Andrei (andreih1981@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class RotateArray {
    /**
     * Метод прокручивает массив на 90 градусов.
     *  [0][0] -> [0][2] -> [2][2] -> [2][0] -> [0][0].
     * @param array входящий двумерный массив.
     * @return прокрученный массив.
     */
    public int[][] rotate(int[][] array) {
        int[][] a = array;
        for (int i = 0; i < a.length / 2; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                int temp = a[i][j];
                a[i][j] = a[a.length - 1 - j][i];
                a[a.length - 1 - j][i] = a[a.length - 1 - i][a.length - 1 - j];
                a[a.length - 1 - i][a.length - 1 - j] = a[j][a.length - 1 - i];
                a[j][a.length - 1 - i] = temp;
            }
        }
        return  a;
    }
}
