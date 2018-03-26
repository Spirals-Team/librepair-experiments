package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimeNumberIterator implements Iterator<Integer> {
    private final int[] array;
    private int position = 0;
    private int delta = 0;
    private int result = 0;
    private boolean flag;

    public PrimeNumberIterator(int[] array) {
        this.array = array;
    }

    boolean isPrime(int n) {
        if (n == 1)
            return false;
        for (int i = 2; i < n; i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

    public Integer next() {
        if (!hasNext())
            throw new NoSuchElementException();
        position = delta;
        return result;
    }

    public boolean hasNext() {
        flag = false;
        for (int i = position; i < array.length; i++) {
            if (isPrime(array[i])) {
                delta = i + 1;
                result = array[i];
                flag = true;
                break;
            }
        }
        return flag;
    }
}
