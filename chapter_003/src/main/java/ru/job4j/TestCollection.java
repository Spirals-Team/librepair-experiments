package ru.job4j;

import java.util.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class TestCollection {
    private static final int TIMES = 1_000_000;
    private static final String ABC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random(0);
    private static final String[] ARRAY = rString(TIMES);

    private static String[] rString(int sizeArray) {
        String[] array = new String[sizeArray];
        for (int out = 0; out < array.length; out++) {
            char[] ch = new char[RANDOM.nextInt(10) + 1];
            for (int i = 0; i < ch.length; i++) {
                ch[i] = ABC.charAt(RANDOM.nextInt(ABC.length()));
            }
            array[out] = new String(ch);
        }
        return array;
    }

    public long add(Collection<String> collection, int amount) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.add(ARRAY[i]);
        }
        long finish = System.currentTimeMillis();
        return (finish - start);
    }

    public long delete(Collection<String> collection, int amount) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < amount; i++) {
            collection.remove(ARRAY[i]);
        }
        long finish = System.currentTimeMillis();
        return (finish - start);
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> arrayL = new ArrayList<>();
        List<String> linkL = new LinkedList<>();
        Set<String> treeS = new TreeSet<>();
        TestCollection list = new TestCollection();

        System.out.println(String.format("%d ms ArrayList (add)", list.add(arrayL, 100_000)));
        System.out.println(String.format("%d ms LinkedList (add)", list.add(linkL, 100_000)));
        System.out.println(String.format("%d ms TreeSet (add)", list.add(treeS, 100_000)));
        System.out.println(String.format("%d ms ArrayList (delete)", list.delete(arrayL, 50_000)));
        System.out.println(String.format("%d ms LinkedList (delete)", list.delete(linkL, 50_000)));
        System.out.println(String.format("%d ms TreeSet (delete)", list.delete(treeS, 50_000)));
    }
}
