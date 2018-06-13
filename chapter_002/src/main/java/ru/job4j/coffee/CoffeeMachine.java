package ru.job4j.coffee;

public class CoffeeMachine {
    public int[] changes(int value, int price) {
        int[] temp = new int[100];
        int index = 0;
        int surrender = value - price;
        while (surrender != 0) {
            if (surrender >= 10) {
                temp[index++] = 10;
                surrender -= 10;
                continue;
            } else if (surrender >= 5) {
                temp[index++] = 5;
                surrender -= 5;
                continue;
            } else if (surrender >= 2) {
                temp[index++] = 2;
                surrender -= 2;
                continue;
            } else {
                temp[index++] = 1;
                surrender -= 1;
                continue;
            }
        }

        int[] result = new int[index];
        System.arraycopy(temp, 0, result, 0, index);
        return result;
    }
}