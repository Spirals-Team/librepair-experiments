package ru.job4j.dvumernmass;

public class Ctnyienechetnie {

    public void sortmassiv(int[][] arr, int[] a, int[] b) {
        int indexA = 0;
        int indexB = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (i % 2 == 0 && j % 2 == 0) {
                    a[indexA++] = arr[i][j];
                } else {
                    b[indexB++] = arr[i][j];
                }
            }
        }
    }
}
