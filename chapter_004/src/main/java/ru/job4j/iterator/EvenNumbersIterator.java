package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {
    private final int[] array;
    private int position = 0;
    private int delta = 0;
    private int result = 0;
    private boolean flag = false;

    public EvenNumbersIterator(int[] array) {
        this.array = array;
    }

    public Integer next() throws NoSuchElementException {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        position = delta;
        return result;
    }

    private boolean check(int elem) {
        if (elem % 2 == 0)
            flag = true;
        else
            flag = false;
        return flag;
    }

    public boolean hasNext() {
        boolean flag = false;
        for (int i = position; i < array.length; i++) {
            if (check(array[i])) {
                result = array[i];
                delta = ++i;
                flag = true;
                break;
            }
        }
        return flag;
    }
}
