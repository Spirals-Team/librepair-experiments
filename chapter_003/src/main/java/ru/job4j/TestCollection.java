package ru.job4j;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

public class TestCollection {

    public BigDecimal add(Collection<String> collection, int amount) {
        BigDecimal start = new BigDecimal(System.nanoTime());
        for (int i = 0; i < amount; i++) {
            collection.add(String.valueOf(i));
        }
        return new BigDecimal(System.nanoTime()).subtract(start);
    }

    public BigDecimal delete(Collection<String> collection, int amount) {
        BigDecimal start = new BigDecimal(System.nanoTime());
        while (amount >= 0) {
            collection.remove(String.valueOf(amount--));
        }
        return new BigDecimal(System.nanoTime()).subtract(start);
    }

    public static void main(String[] args) {
        TestCollection testCollection = new TestCollection();
        List<String> arrayList = new ArrayList<String>();
        List<String> linkedList = new LinkedList<String>();
        Set<String> treeSet = new TreeSet<String>();
        int[] arrMaxSize = {1000, 10000, 50000, 100000, 300000, 600000, 1000000, 3000000};
        int[] arrDel = {1, 10, 50, 100, 300, 600, 1000, 3000};
        int index = 0;
        int maxTry = 5;
        while (index < arrMaxSize.length) {
            int itry = 0;
            double countAdd = 0;
            double countDel = 0;
            double[] arrDAdd = new double[maxTry];
            double[] arrDDel1 = new double[maxTry];
            while (itry < maxTry) {
                BigDecimal rezult = testCollection.add(arrayList, arrMaxSize[index]);
                rezult = rezult.divide(new BigDecimal(1000000));
                countAdd += rezult.doubleValue();
                arrDAdd[itry] = rezult.doubleValue();
                rezult = testCollection.delete(arrayList, arrDel[index]);
                rezult = rezult.divide(new BigDecimal(1000000));
                countDel += rezult.doubleValue();
                arrDDel1[itry] = rezult.doubleValue();
                arrayList.clear();
                itry++;
            }
            double del1 = countDel / maxTry;
            System.out.print(String.format("ArrayList  add %,10d items, time: %8.3f ms ", arrMaxSize[index], countAdd / maxTry));
            System.out.println(Arrays.toString(arrDAdd));
            itry = 0;
            countAdd = 0;
            countDel = 0;
            double[] arrDDel2 = new double[maxTry];
            while (itry < maxTry) {
                BigDecimal rezult = testCollection.add(linkedList, arrMaxSize[index]);
                rezult = rezult.divide(new BigDecimal(1000000));
                countAdd += rezult.doubleValue();
                arrDAdd[itry] = rezult.doubleValue();
                rezult = testCollection.delete(linkedList, arrDel[index]);
                rezult = rezult.divide(new BigDecimal(1000000));
                countDel += rezult.doubleValue();
                arrDDel2[itry] = rezult.doubleValue();
                linkedList.clear();
                itry++;
            }
            double del2 = countDel / maxTry;
            System.out.print(String.format("LinkedList add %,10d items, time: %8.3f ms ", arrMaxSize[index], countAdd / maxTry));
            System.out.println(Arrays.toString(arrDAdd));
            itry = 0;
            countAdd = 0;
            countDel = 0;
            double[] arrDDel3 = new double[maxTry];
            while (itry < maxTry) {
                BigDecimal rezult = testCollection.add(treeSet, arrMaxSize[index]);
                rezult = rezult.divide(new BigDecimal(1000000));
                countAdd += rezult.doubleValue();
                arrDAdd[itry] = rezult.doubleValue();
                rezult = testCollection.delete(treeSet, arrDel[index]);
                rezult = rezult.divide(new BigDecimal(1000000));
                countDel += rezult.doubleValue();
                arrDDel3[itry] = rezult.doubleValue();
                treeSet.clear();
                itry++;
            }
            System.out.print(String.format("TreeSet    add %,10d items, time: %8.3f ms ", arrMaxSize[index], countAdd / maxTry));
            System.out.println(Arrays.toString(arrDAdd));
            System.out.println();
            System.out.print(String.format("ArrayList  del %,10d items, time: %8.3f ms ", arrDel[index], del1));
            System.out.println(Arrays.toString(arrDDel1));
            System.out.print(String.format("LinkedList del %,10d items, time: %8.3f ms ", arrDel[index], del2));
            System.out.println(Arrays.toString(arrDDel2));
            System.out.print(String.format("TreeSet    del %,10d items, time: %8.3f ms ", arrDel[index], countDel / maxTry));
            System.out.println(Arrays.toString(arrDDel3));
            index++;
            System.out.println();
        }
    }
}
