package ru.job4j.controlvopros;

import java.util.*;

public class Convert {

    public Convert() { //отступ перед фигурной скобки отсутствовал и не понятно зачем нам вообще конструктор этого класса

    }

    /**
     * Converts array to list
     * @param array
     * @return
     */
    List<Integer> makeList(int[][] array) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                list.add(array[i][j]);
            }
        }
        return list;
    }


    /**
     * Converts list to array
     * @param list
     * @param rws
     * @return
     */
    public int[][] makeArray(List<Integer> list, int rws) {
        Iterator<Integer> iterator = list.iterator();
        int cls = list.size() / rws + (list.size() % rws == 0 ? 0 : 1);
        int[][] array = new int[rws][cls];
        for (int i = 0; i < rws; i++) {
            for (int j = 0; j < cls; j++) { // здесь вроде всё нормально не хватало только фигурных скобок в локе if  и else
                if (iterator.hasNext()) {
                    array[i][j] = iterator.next();
                } else {
                    array[i][j] = 0;
                }
            }
        }
        return array;
    }
}