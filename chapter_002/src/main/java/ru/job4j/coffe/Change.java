package ru.job4j.coffe;

import java.util.ArrayList;

/**
 * Класс размены  купюр.
 */

public  class Change {
    /**
     * Метод возвращающий массив купюр.
     * @param value номинал купюры.
     * @param price цена кофе.
     * @return массив купюр.
     */
    public int[] changes(int value, int price) {
    int[] coins = new int[] {1, 2, 5, 10};
    int diff = value - price;
    int index = coins.length - 1;
    ArrayList<Integer> list = new ArrayList<>();

    while (diff > 0) {
        while (diff >= coins[index]) {
            list.add(coins[index]);
            diff -= coins[index];
        }
        index--;
    }
    int[] result = new int[list.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}
