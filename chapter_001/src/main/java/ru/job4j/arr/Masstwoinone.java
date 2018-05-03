package ru.job4j.arr;

/**
 */
public class Masstwoinone {

    public static int[] ssortirovkaMassivavodin(int[]ar1, int[]ar2) {
        int[]ar3 = new int[ar1.length + ar2.length];
        int k = 0;
        int n = 0;
        for (int i = 0; i < ar3.length; i++) {
            if (k < ar1.length && n < ar2.length) {
                if (ar1[k] <= ar2[n]) {
                    ar3[i] = ar1[k++];
                } else {
                    ar3[i] = ar2[n++];
                }
            } else if (k < ar1.length && n >= ar2.length) {
                ar3[i] = ar1[k++];
            } else if (n < ar2.length && k >= ar1.length) {
                ar3[i] = ar2[n++];
            }
        } return ar3;
    }
}
