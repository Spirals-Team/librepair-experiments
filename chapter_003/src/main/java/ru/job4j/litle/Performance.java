package ru.job4j.litle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *performance .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 16.09.17;
 * @version $Id$
 * @since 0.1
 */
public class Performance {
    /**
     * Вставка элементов.
     * @param collection коллекция
     * @param amount колличество элементов для добовления.
     * @return время выполнения.
     */
    public long add(Collection<String> collection, int amount) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.add("Item" + i);
        }
        long time = System.currentTimeMillis();
        return time - timeStart;

    }

    /**
     * Удаление элементов.
     * @param collection коллекция.
     * @param amount колличество элемментов которые нужно удалить.
     * @return время выполнения.
     */
    public long delete(Collection<String> collection, int amount) {
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.remove("Item" + i);

        }
        return System.currentTimeMillis() - timeStart;
    }

    /**
     * Майн.
     * @param args нету.
     */
    public static void main(String[] args) {
        Performance performance = new Performance();
        performance.init();
    }

    /**
     * все операции.
     */

    public void init() {
        List linkedList = new LinkedList<String>();
        List arrayList = new ArrayList<String>();
        Set treeSet = new TreeSet<String>();
        long linked = add(linkedList, 1000000);
        long array = add(arrayList, 1000000);
        long tree = add(treeSet, 1000000);
        System.out.println(String.format("LinkedList %d ms, ArrayList %d ms, TreeSet %d ms.", linked, array, tree));
        long lin = delete(linkedList, 5000);
        long ar = delete(arrayList, 5000);
        long tre = delete(treeSet, 5000);
        System.out.println(String.format("LinkedList %d ms, ArrayList %d ms, TreeSet %d ms.", lin, ar, tre));
    }
}
