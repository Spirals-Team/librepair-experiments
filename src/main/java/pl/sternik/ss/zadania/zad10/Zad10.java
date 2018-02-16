package pl.sternik.ss.zadania.zad10;

import java.util.Arrays;

public class Zad10 {

    public static void main(String[] args) {

        int[][] numbersArray = {{1, 1, 1, 2}, {2, 1, 2, 2}};
        System.out.println(calcArray(numbersArray));
    }


    public static String calcArray(int[][] numbersArray) {

        char[] sings = {'+', 'x', '-', '*'};
        int[] resultsArray = new int[numbersArray[0].length];
        for (int i = 0; i < sings.length; i++) {
            switch (sings[i]) {
                case '+':
                    resultsArray[i] = numbersArray[0][i] + numbersArray[1][i];
                    break;
                case '-':
                    resultsArray[i] = numbersArray[0][i] - numbersArray[1][i];
                    break;
                case '*':
                    resultsArray[i] = numbersArray[0][i] * numbersArray[1][i];
                    break;
                case '/':
                    resultsArray[i] = numbersArray[0][i] / numbersArray[1][i];
                    break;
                default:
                    resultsArray[i] = numbersArray[0][i] + numbersArray[1][i];
                    break;
            }
        }
        return Arrays.toString(resultsArray);

    }

}
