package ru.job4j.department;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Department {
    private TreeSet<String> listDepatment = new TreeSet<String>();

    public Department(List<String> listIn) {
        add(listIn);
    }

    public void add(String st) {
        String[] array = splitInArray(st);
        for (int i = 0; i < array.length; i++) {
            listDepatment.add(array[i]);
        }
    }

    public void add(List<String> list) {
        for (String st: list) {
            add(st);
        }
    }

    private String[] splitInArray(String st) {
        String[] array = st.split("\\\\");
        if (array.length > 1) {
            for (int i = 1; i < array.length; i++) {
                array[i] = array[i - 1].concat("\\").concat(array[i]);
            }
        }
        return array;
    }

    public void del(String st) {
        listDepatment.remove(st);
    }

    public void print() {
        for (String item: listDepatment) {
            System.out.println(item);
        }
    }

    public void printByDec() {
        TreeSet<String> newlist = new TreeSet<String>(new SortingInDecreasingOrder());
        newlist.addAll(listDepatment);
        for (String item: newlist) {
            System.out.println(item);
        }
    }
}
