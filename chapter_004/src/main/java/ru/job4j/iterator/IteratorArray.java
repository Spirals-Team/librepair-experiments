package ru.job4j.iterator;

import java.util.Iterator;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class IteratorArray implements Iterator {

    private final int[][] values;
    private int iOut = 0;
    private int iIn = 0;

    public IteratorArray(final int[][] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
    	int maxIndexOut = values.length - 1;
        return (!(iOut == maxIndexOut & iIn == values[maxIndexOut].length));
    }

    @Override
    public Object next() {
    	int result = 0;
    	if (iOut < values.length) {
    		if (iIn < values[iOut].length) {
				result = iIn++;
			} else {
				iOut++;
				iIn = 0;
				result = iIn++;
			}
		}
        return values[iOut][result];
    }
}
