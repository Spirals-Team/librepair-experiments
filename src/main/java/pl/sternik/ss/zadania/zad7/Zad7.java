package pl.sternik.ss.zadania.zad7;

import java.util.Scanner;

public class Zad7 {


    //Moj wlasny adapter na finalna lase scanner
    //po to by sie dalo zrobic na niej mocka i lepiej potestowac.
    MyScanner input;
    //Jednoczesnie zasieg pakietowy a nie prywatny po to by test mogl to podmienic.


    public Zad7(Scanner input) {
        super();
        this.input = new MyScanner(input);
    }

    public static void main(String[] args) {

        int[] numbersArray = {9, 1, 2, 3, 4, 5};
        Scanner scanner = new Scanner(System.in);
        int x = Integer.valueOf(scanner.next());


    }


    public int getIndexOfNumberFor(int[] numbersArray) {
    int numberToFind = input.nextInt();
        for (int i = 0; i < numbersArray.length; i++) {
            if (numbersArray[i] % numberToFind == 0) {
                return i;
            }
        }
        return -1;
    }

    public int getIndexOfNumberWhile(int[] numbersArray) {
        int numberToFind = input.nextInt();
        int counter = 0;
        while (counter < numbersArray.length) {
            if (numbersArray[counter] % numberToFind == 0) {
                return counter;
            }
            counter++;
        }
        return -1;
    }


}
