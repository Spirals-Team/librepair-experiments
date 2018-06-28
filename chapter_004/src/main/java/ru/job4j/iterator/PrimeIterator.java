package ru.job4j.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */

public class PrimeIterator implements Iterator {

    private ArrayList<Integer> list = new ArrayList<>();
    private int index = 0;

    /**
     * Конструктор класса.
     * @param  values - массив чисел, в котором будет итератор по простым числам.
     */
    public PrimeIterator(final int[] values) {
        for (int i = 0; i < values.length; i++) {
            if (checkSimple(values[i])) {
                list.add(values[i]);
            }
        }
    }

    /**
     * Метод определят простое ли число ему передано в качестве параметра.
     * Алгоритм проверки на простое число взят en.wikipedia.org/wiki/Primality_test
     * @param  i - проверяемое число.
     * @return возврашется true, если  число простое.
     */
    public static boolean checkSimple(int i) {
        if (i <= 1) {
            return false;
        } else if (i <= 3) {
            return true;
        } else if (i % 2 == 0 || i % 3 == 0) {
            return false;
        }
        int n = 5;
        while (n * n <= i) {
            if (i % n == 0 || i % (n + 2) == 0) {
                return false;
            }
            n = n + 6;
        }
        return true;
    }

    @Override
    public boolean hasNext() {
        return index < list.size();
    }

    @Override
    public Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        return list.get(index++);
    }
}
