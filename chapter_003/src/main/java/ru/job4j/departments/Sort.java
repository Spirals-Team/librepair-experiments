package ru.job4j.departments;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class Sort
 *
 * @author Aleksandr Vysotskiy.
 * @version 1.5
 * @since 22.05.18
 */
public class Sort {

    /**
     * This is an auxiliary method, if adds the missing parent.
     *
     * @param input array of departments.
     * @return array with add missing parents.
     */
    private ArrayList<String> addParents(ArrayList<String> input) {
        for (int index = 0; index < input.size(); index++) {
            if (input.get(index).contains("\\")) {
                String path = input.get(index).substring(0, input.get(index).lastIndexOf("\\"));
                if (!input.contains(path)) {
                    input.add(path);
                }
            }
        }
        return input;
    }

    /**
     * This method sort array of departments by increase.
     *
     * @param input array of departments.
     * @return sorted array in ascending order.
     */
    public ArrayList<String> sortAscending(ArrayList<String> input) {
        input = addParents(input);
        input.sort(new Comparator<String>() {
                       @Override
                       public int compare(String o1, String o2) {
                           return o1.compareTo(o2);
                       }
                   }
        );
        return input;
    }

    /**
     * This method sort array of departments by diminution.
     *
     * @param input array of departments.
     * @return sorted array in diminution order.
     */
    public ArrayList<String> diminution(ArrayList<String> input) {
        input = addParents(input);
        input.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result;
                if (o1.length() < o2.length() && o2.startsWith(o1)) {
                    result = -1;
                } else {
                    if (o1.length() > o2.length() && o1.startsWith(o2)) {
                        return 1;
                    } else {
                        result = o2.compareTo(o1);
                    }
                }
                return result;
            }
        });
        return input;
    }
}