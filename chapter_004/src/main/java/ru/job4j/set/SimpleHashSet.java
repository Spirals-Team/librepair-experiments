package ru.job4j.set;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleHashSet<E> {

    private HashTable<E> hashTable = new HashTable<>();

    public boolean add(E e) {
        boolean result;
        if (hashTable.find(e)) {
            result = false;
        } else {
            hashTable.insert(e);
            result = true;
        }
        return result;
    }

    public boolean contains(E e) {
        return hashTable.find(e);
    }

    public boolean remove(E e) {
        return hashTable.delete(e);
    }
}
