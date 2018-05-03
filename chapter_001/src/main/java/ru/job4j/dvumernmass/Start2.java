package ru.job4j.dvumernmass;

public class Start2 {
    public static void main(String[] args) {
        int[][] arr = new int[][]{
                {1, 2, 3, 5, 6},
                {2, 4, 5, 6, 8},
                {1, 2, 5, 6, 6},
                {2, 5, 6, 8, 9},
                {5, 6, 8, 6, 2}
        };
        int[] a = new int[25];
        int[] b = new int[25];
        new Ctnyienechetnie().sortmassiv(arr, a, b);
        new Start2().outputMass(a);
        new Start2().outputMass(b);

    }

    public void outputMass(int[] arr) {
        for (int k : arr) {
            if (k != 0) {
                System.out.print(k + "  ");
            }
        }
        System.out.println("  ");
    }
}
