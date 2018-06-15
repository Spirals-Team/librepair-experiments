package ru.job4j.array;

/**
 * author Daniil Emelyanov
 * @version $id$
 * @since  15/03/2018
 */
public class Turn {
    /**
     * Метод переворащивающий массив.
     *
     * @param array входящий массив
     * @return перевернутый массив
     */
    public int[] back(int[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            int temp = array[i];
                array[i] = array[array.length - 1 - i];
                array[array.length - 1 - i] = temp;
        }
        return array;
    }
}