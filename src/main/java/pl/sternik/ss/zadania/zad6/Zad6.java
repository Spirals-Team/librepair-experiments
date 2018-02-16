package pl.sternik.ss.zadania.zad6;

public class Zad6 {

    public static void main(String[] args) {

        int[][] values = {{3, 8, 16}, {1, 22, 28, 24}, {3}, {41, 42}};

        System.out.printf("Min: %d \t max: %d", findMin(values), findMax(values));
    }

    public static int findMin(int[][] numbers) {
        if (numbers.length == 0)
            throw new IllegalArgumentException();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < numbers.length; i++)
            for (int j = 0; j < numbers[i].length; j++)
                if (numbers[i][j] < min)
                    min = numbers[i][j];
        return min;
    }


    public static int findMax(int[][] numbers) {
        if (numbers.length == 0)
            throw new IllegalArgumentException();
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length; i++)
            for (int j = 0; j < numbers[i].length; j++)
                if (numbers[i][j] > max)
                    max = numbers[i][j];
        return max;
    }


}
