package pl.sternik.ss.zadania.zad9;


import java.util.Scanner;

public class Zad9 {

    public static void main(String[] args) {

        Integer[][] values = {{3, 8, 16}, {1, 22, 28, 24}, {3}, {41, 42}};
        printArray(values);
        Scanner scanner = new Scanner(System.in);
        int pos1 = scanner.nextInt(), pos2 = scanner.nextInt();
        swapRows(values, pos1, pos2);
        printArray(values);
    }

    public static <T> void swapRows(T[][] array, int pos1, int pos2) {
        T[] temp = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = temp;
    }


    public static <T> void printArray(T[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
    }
}
