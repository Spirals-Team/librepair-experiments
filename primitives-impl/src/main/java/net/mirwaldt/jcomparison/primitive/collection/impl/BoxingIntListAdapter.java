package net.mirwaldt.jcomparison.primitive.collection.impl;

import net.mirwaldt.jcomparison.primitive.collection.api.NonBoxingIntList;

import java.util.Iterator;
import java.util.List;

public class BoxingIntListAdapter implements NonBoxingIntList {
    private final List<Integer> intList;

    public BoxingIntListAdapter(List<Integer> intList) {
        this.intList = intList;
    }
    
    @Override
    public boolean contains(int element) {
        return intList.contains(element);
    }

    @Override
    public int getInt(int index) {
        return intList.get(index);
    }

    @Override
    public int indexOfInt(int element) {
        return intList.indexOf(element);
    }

    @Override
    public int lastIndexOfInt(int element) {
        return intList.lastIndexOf(element);
    }

    @Override
    public int size() {
        return intList.size();
    }

    @Override
    public IntIterator iterator() {
        return new IntIterator() {
            final Iterator<Integer> iterator = intList.iterator();
            
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public int next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

    @Override
    public void clear() {
        intList.clear();
    }

    @Override
    public boolean addInt(int element) {
        return intList.add(element);
    }

    @Override
    public void addInt(int index, int element) {
        intList.add(index, element);
    }

    @Override
    public int setInt(int index, int element) {
        return intList.set(index, element);
    }

    @Override
    public boolean removeInt(int element) {
        return intList.remove((Integer) element);
    }

    @Override
    public int removeIntAt(int index) {
        return intList.remove(index);
    }

    @Override
    public void trimToSize() {
        // we could trim the size of array lists 
        // by replacing it with a new one with the initial capacity == size
        // but then the iterator behavior might be confusing 
        // because it still might use the old list 
        // => we do not offer it here
        throw new UnsupportedOperationException("Not supported");
    }
}
