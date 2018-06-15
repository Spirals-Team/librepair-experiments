package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimeIterator implements Iterator {
    private int[] array;
    private int count = 0;

    public PrimeIterator(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        while (count < array.length) {
                if (isPrime(this.array[this.count])) {
                    result = true;
                    break;
                }
                count++;
        }
        return result;
    }

    @Override
    public Object next() {
           if (!hasNext()) {
               throw new NoSuchElementException("no such prime element");
        }
        return array[count++];
    }
    private boolean isPrime(int value) {
        boolean prime = true;
        if (value <= 1) {
            prime = false;
        } else {
            for (int index = 2; index < value; index++) {
                if (value % index == 0) {
                    prime = false;
                    break;
                }
            }
        }
        return prime;
    }
}
