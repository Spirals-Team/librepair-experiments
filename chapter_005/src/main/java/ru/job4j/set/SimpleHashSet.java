package ru.job4j.set;

public class SimpleHashSet<E> {

    private Object[] hashSet;
    private int size;
    private int count = 0;

    public SimpleHashSet(int size) {
        this.size = size;
        this.hashSet = new Object[size];
    }
    //Hash function
    private int hash(E e) {
        return Math.abs(e.hashCode() % size);
    }
    //Add unique element
    public boolean add(E e) {
        int key = hash(e);
        if (hashSet[key] != null) {
            return false;
        }
        hashSet[key] = e;
        count++;
        return true;
    }
    //Contains specified element
    public boolean contains(E e) {
        int key = hash(e);
        return (hashSet[key] != null && hashSet[key].equals(e));
    }
    //Remove element
    public boolean remove(E e) {
        if (!contains(e)) {
            return false;
        }
        hashSet[hash(e)] = null;
        count--;
        return true;
    }
}
