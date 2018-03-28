package ru.job4j.coffeeMachine;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;

public class coffeeMachine {
    private static final int capacity = 10;
    private static final int[] coins = new int[] {1, 2, 5, 10};
    private int[] result = new int[capacity];

    int[] changes(int value, int price) {
        int temp = value - price;
        int count = 0;
        while (temp != 0) {
            for (int i = coins.length - 1; i >= 0; i--) {
                if (temp % coins[i] == 0) {
                    result[count] = coins[i];
                    count++;
                    temp -= coins[i];
                    break;
                }
            }
        }
        return clean();
    }

    private int[] clean() {
        int capacity = 0;
        for (int elem : result) {
            if (elem != 0)
                capacity++;
        }
        int[] result = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            result[i] = this.result[i];
        }
        return result;
    }
}

