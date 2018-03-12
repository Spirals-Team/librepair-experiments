package ru.job4j.array;

/**
 * Class MergeSort. Добавляем 2 сортированных массива в один сортированный.
 *
 * @author CyMpak1989 (cympak2009@mail.ru)
 * @version 1.0
 * @since 24.09.2017
 */
public class MergeSort {
    /**
     * Method mergeSort. Принимает на вход 2 массива на выходе вернем один сортированный массив.
     *
     * @param arrayA массив
     * @param arrayB массив
     *
     * @return сортированный массив
     */
    public int[] mergeSort(int[] arrayA, int[] arrayB) {
        int[] resault = new int[arrayA.length + arrayB.length];
        int indexA = 0;
        int indexB = 0;
        while (indexA + indexB < resault.length) {
            if (indexB == arrayB.length || indexA != arrayA.length && arrayA[indexA] < arrayB[indexB]) {
                resault[indexA + indexB] = arrayA[indexA];
                indexA++;
            } else {
                resault[indexA + indexB] = arrayB[indexB];
                indexB++;
            }
        }
        return resault;
    }
}
