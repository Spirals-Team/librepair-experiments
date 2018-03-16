package ru.job4j.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * Collections.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class Collections {
    /*** Array of strings.*/
    private String[] stringsArray = new String[100_000];
    /*** Constructor.*/
    public Collections() {
        for (int i = 0; i < this.stringsArray.length; i++) {
            this.stringsArray[i] = String.valueOf(i);
        }
    }
    /**
     * Add strings.
     * @param collection - collection
     * @param amount - amount strings
     * @return - execution time
     */
    public long add(Collection<String> collection, int amount) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.add(this.stringsArray[i]);
        }
        return System.currentTimeMillis() - startTime;
    }
    /**
     * Delete strings.
     * @param collection - collection
     * @param amount - amount strings
     * @return - execution time
     */
    public long delete(Collection<String> collection, int amount) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.remove(this.stringsArray[i]);
        }
        return System.currentTimeMillis() - startTime;
    }
    /**
     * Main.
     * @param args - args
     */
    public static void main(String[] args) {
        Collection<String> linkedList = new LinkedList<>();
        Collection<String> arrayList = new ArrayList<>();
        Collection<String> treeSet = new TreeSet<>();

        Collections coll = new Collections();

        System.out.println(coll.add(linkedList, 50_000)); //  6
        System.out.println(coll.delete(linkedList, 10_000)); //  2

        System.out.println(coll.add(arrayList, 50_000)); //  7
        System.out.println(coll.delete(arrayList, 10_000)); //  1000

        System.out.println(coll.add(treeSet, 50_000)); // 80
        System.out.println(coll.delete(treeSet, 10_000)); // 20
    }
}
