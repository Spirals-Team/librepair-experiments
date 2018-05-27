package ru.job4j.set;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashSet<T> {
    private int size;
    private int defaultSize = 10;
    private Object[] hashArray;

    public SimpleHashSet() {
        this.size = 0;
        this.hashArray = new Object[defaultSize];
    }

    public SimpleHashSet(int capacity) {
        if (capacity < defaultSize) {
            capacity = defaultSize;
        }
        this.size = 0;
        this.hashArray = new Object[capacity];
    }

    public boolean add(T t) {
        int index = newHash(t, hashArray.length);
        if (t == null || (hashArray[index] != null && hashArray[index].equals(t))) {
            return false;
        }
        if (size >= hashArray.length * 2 / 3) {
            resize(t, 0);
        }
        hashArray[index] = t;
        size++;
        return true;
    }

    private int newHash(T t, int size) {
        int h = t.hashCode();
        h = h >= 0 ? h : h * -1;
        return h % size;
    }

    private void restartResize(T t, int capacity) {
        resize(t, capacity);
    }

    private void resize(T t, int bigProblem) {
        Object[] newHash = new Object[hashArray.length * 3 / 2 + 1 + bigProblem];
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                int index = newHash((T) hashArray[i], newHash.length);
                if (newHash[index] != null) {
                    restartResize(t, newHash.length);
                    return;
                }
                newHash[index] = hashArray[i];
            }
        }
        hashArray = newHash;
        add(t);
    }

    public boolean contains(T t) {
        int index = newHash(t, hashArray.length);
        if (hashArray[index] != null && hashArray[index].equals(t)) {
            return true;
        }
        return false;
    }

    boolean remove(T t) {
        int index = newHash(t, hashArray.length);
        if (hashArray[index] != null && hashArray[index].equals(t)) {
            hashArray[index] = null;
            size--;
            return true;
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public int getCapacityAllItems() {
        int sum = 0;
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                sum++;
            }
        }
        return sum;
    }
}
