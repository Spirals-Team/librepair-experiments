package ru.job4j.departmentSort;

import java.util.*;

public class SortDepartments {
    private List<List<String>> parsedByArraysSymbols = new ArrayList<>();
    private List<List<String>> unsortedArray = new ArrayList<>();

    public SortDepartments(List<String> list) {
        parse(list);
    }

    public List<String> sort(int index) {
        Comparator<List<String>> comparator = Comparator(index);
        unsortedArray.sort(comparator);
        return bindArrays();
    }

    private void parse(List<String> list) {
        List<String> parsedList = new ArrayList<>();
        for (String elem : list) {
            parsedByArraysSymbols.add(Arrays.asList(elem.split("\\\\")));
        }

        for (List<String> elem : parsedByArraysSymbols) {
            List<String> array = new ArrayList<>();
            for (int i = 0; i < elem.size(); i++) {
                array.add(elem.get(i));
                addArray(array);
            }
        }
    }

    private void addArray(List<String> array) {
        ArrayList<String> list = new ArrayList<>();
        list.addAll(array);
        if (!unsortedArray.contains(array)) {
            unsortedArray.add(list);
        }
    }

    private List<String> bindArrays() {
        List<String> sortedArray = new ArrayList<>();
        for (List<String> element : unsortedArray) {
            StringBuilder builder = new StringBuilder();
            for (String elem : element) {
                builder.append(elem);
                if (element.indexOf(elem) < element.size() - 1) {
                    builder.append("\\");
                }
            }
            sortedArray.add(builder.toString());
        }
        return sortedArray;
    }

    private Comparator<List<String>> Comparator(int index) {
        Comparator<List<String>> comparator = new Comparator<List<String>>() {
            @Override
            public int compare(List<String> left, List<String> right) {
                int result = 0;

                Iterator<String> it1 = left.iterator();
                Iterator<String> it2 = right.iterator();
                while (it1.hasNext() && it2.hasNext() && result == 0) {
                    result = index * it1.next().compareTo(it2.next());
                }
                if (result == 0) {
                    result = Integer.compare(left.size(), right.size());
                }
                return result;
            }
        };
        return comparator;
    }
}