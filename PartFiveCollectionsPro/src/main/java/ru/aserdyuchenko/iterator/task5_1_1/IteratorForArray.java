package ru.aserdyuchenko.iterator.task5_1_1;

import java.util.Iterator;

/**
 * @author anton.
 */
public class IteratorForArray implements Iterator<Integer> {
    /**
     * @param externalArray - Исходный двумерный массив.
     */
    private int[][] externalArray;
    /**
     * @param innerArray - Внутренний одномерный массив.
     */
    private int[] innerArray;
    /**
     * @param innerIndex - индекс для внутреннего массива.
     */
    private int innerIndex = 0;
    /**
     * @param exernalIndex - индекс для внешнего массива.
     */
    private int externalIndex = 0;

    /**
     * Конструктор.
     * @param values - исходный двумерный массив.
     */
    IteratorForArray(int[][] values) {
        this.externalArray = values;
        this.innerArray = externalArray[externalIndex++];
    }

    @Override
    public boolean hasNext() {
        if (innerArray.length <= innerIndex) {
            if (externalArray.length > externalIndex) {
                innerArray = externalArray[externalIndex++];
                innerIndex = 0;
            }
        }
        return innerArray.length > innerIndex;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            if (externalArray.length > externalIndex) {
                innerArray = externalArray[externalIndex++];
                innerIndex = 0;
            }
        }
        return innerArray[innerIndex++];
    }
}
