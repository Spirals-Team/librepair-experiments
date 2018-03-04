package ru.job4j.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimeIterator implements Iterator<Integer> {

    private final int[] numbers;
    private int index = 0;

    public PrimeIterator(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean hasNext() {
        if (this.numbers.length > this.index) {
            for (int i = this.index; i < this.numbers.length; i++) {
                //check on prime number
                if (this.numbers[i] == 1) {
                    continue;
                }
                boolean isPrime = true;
                for (int j = 2; j <= this.numbers[i] / 2; j++) {
                    if (this.numbers[i] % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    this.index = i;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Integer next() {
        if (this.hasNext()) {
            return this.numbers[this.index++];
        }
        throw new NoSuchElementException();
    }
}
