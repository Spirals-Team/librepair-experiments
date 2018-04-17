package ru.job4j.coffee;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class CoffeeMachine {
    private static final int[] RASING = {1, 2, 5, 10};

    private int[] converter(List<Integer> list) {
        int[] array = new int[list.size()];
        int i = 0;
        for (Integer item : list) {
            array[i++] = item;
        }
        return array;
    }

    public int[] changes(int value, int price) {
        int cur = value - price;
        List<Integer> list = new ArrayList<>();
        for (int out = RASING.length - 1; out >= 0; out--) {
            int div = cur / RASING[out];
            cur = cur % RASING[out];
            for (int i = 0; i < div; i++) {
                list.add(RASING[out]);
            }
        }
        return converter(list);
    }
}
