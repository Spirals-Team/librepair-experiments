package ru.job4j.array;

/**
 * Class RotateArray. Переворчиваем массив по часовой стрелке.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 15.09.2017
 */
public class RotateArray {
    /**
     * Method rotate. Принимает на вход массив на выходе вернет массив перевернутый по часвой стрелке.
     *
     * @param array двумерный массив
     * @return Переернутый массив
     */
    public int[][] rotate(int[][] array) {
        int length = array.length;
        for (int i = 0; i < length / 2; i++) {
            for (int j = 0; j < (length + 1) / 2; j++) {
                int temp = array[j][i];
                array[j][i] = array[length - 1 - i][j];
                array[length - 1 - i][j] = array[length - 1 - j][length - 1 - i];
                array[length - 1 - j][length - 1 - i] = array[i][length - 1 - j];
                array[i][length - 1 - j] = temp;
            }
        }
        return array;
    }
}
