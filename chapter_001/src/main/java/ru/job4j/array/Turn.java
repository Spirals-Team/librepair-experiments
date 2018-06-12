package ru.job4j.array;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Turn {
    public int[] back(int[] array) {
        int current;
        for (int frontIndex = 0, backIndex = array.length - 1; frontIndex < array.length / 2; frontIndex++, backIndex--) {
            current = array[frontIndex];
            array[frontIndex] = array[backIndex];
            array[backIndex] = current;
        }
        return array;
    }
}