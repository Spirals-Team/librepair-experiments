package ru.job4j.array;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class GlueArray {

    /**
     * Метод объединения двух отсартированных массивов в один.
     * @param arrayOne - первый массив.
     * @param arrayTwo - второй массив.
     * @return - возврщает новый отсартированный массив содержаший два массива.
     */
    public int[] glueArray(int[] arrayOne, int[] arrayTwo) {
        int[] arrayNew = new int[arrayOne.length + arrayTwo.length];
        int indexOneArray = 0;
        int indexTwoArray = 0;
        boolean flagNotEndArrayOne = true;
        boolean flagNotEndArrayTwo = true;
        for (int i = 0; i <= arrayNew.length - 1; i++) {
            if (flagNotEndArrayOne && flagNotEndArrayTwo) {
                if (arrayOne[indexOneArray] <= arrayTwo[indexTwoArray]) {
                    arrayNew[i] = arrayOne[indexOneArray];
                    indexOneArray++;
                } else {
                    arrayNew[i] = arrayTwo[indexTwoArray];
                    indexTwoArray++;
                }
            } else {
                if (flagNotEndArrayOne) {
                    arrayNew[i] = arrayOne[indexOneArray];
                    indexOneArray++;
                } else {
                    arrayNew[i] = arrayTwo[indexTwoArray];
                    indexTwoArray++;
                }
            }
            if (indexOneArray == arrayOne.length && flagNotEndArrayOne) {
                flagNotEndArrayOne = false;
            } else if (indexTwoArray == arrayTwo.length && flagNotEndArrayTwo) {
                flagNotEndArrayTwo = false;
            }
        }
        return arrayNew;
    }
}
