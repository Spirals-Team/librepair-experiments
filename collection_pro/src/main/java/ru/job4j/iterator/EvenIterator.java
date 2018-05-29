package ru.job4j.iterator;

import java.util.Iterator;

public class EvenIterator implements Iterator {
    private int[] array;
    private int index = 0;
    private int count = 0;

    public EvenIterator(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        while (count < array.length - 1 && array[count] % 2 != 0) {
            count++;
        }
        return array[count++] % 2 == 0;
    }

    @Override
    public Object next() {
        int result = -1;
            if (array[index] % 2 == 0) {
                result = array[index];
            }
                while (index < array.length - 1 && array[index++] % 2 != 0) {
                    result = array[index];
                }
        return result;
    }
}