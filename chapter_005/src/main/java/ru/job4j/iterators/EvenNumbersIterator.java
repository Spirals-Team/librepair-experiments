package ru.job4j.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenNumbersIterator implements Iterator<Integer> {

    private final int[] numbers;
    private int index = 0;

    public EvenNumbersIterator(final int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public boolean hasNext() {
        if (this.numbers.length > this.index) {
            for (int i = this.index; i < this.numbers.length; i++) {
                if (this.numbers[i] % 2 == 0) {
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
