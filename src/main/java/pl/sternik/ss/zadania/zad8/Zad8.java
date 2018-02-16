package pl.sternik.ss.zadania.zad8;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zad8 {

    public static void main(String[] args) {


        printArray(createArray());
    }


    public static String[][] createArray() {
        Scanner scanner = new Scanner(System.in);
        List<Integer> wszystkieWymiary = new ArrayList<>();
        int wymiar = 0;
        String[][] twoDimArray;

        System.out.println("Podaj wymiary, aby zakonczyc wpisz -1");
        while ((wymiar = scanner.nextInt()) != -1)
            wszystkieWymiary.add(wymiar);

        twoDimArray = new String[wszystkieWymiary.size()][];

        for (int i = 0; i < wszystkieWymiary.size(); i++) {
            twoDimArray[i] = new String[wszystkieWymiary.get(i)];
            for (int j = 0; j < twoDimArray[i].length; j++)
                twoDimArray[i][j] = i + "-" + j + " ";
        }
        return twoDimArray;

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
