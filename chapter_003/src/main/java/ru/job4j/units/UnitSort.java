package ru.job4j.units;

import java.util.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
class UserSort {
    /**
     * Метод возвращает массив с добавленными строками названия
     * департаментов, если они отсутствовали  в исходном массиве.
     */
    public String[] addUnit(String[] arg) {
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(arg));
        Set<String> listSet = new TreeSet<>();
        for (String out : list) {
            boolean result = false;
            String sub = out.substring(0, 2);
            for (String in : list) {
                if (sub.equals(in)) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                listSet.add(sub);
            }
        }
        list.addAll(listSet);
        return list.toArray(new String[list.size()]);
    }
}

class AscendingSort implements Comparator<String> {
    @Override
    public int compare(String s, String t1) {
        return s.compareTo(t1);
    }
}

class DescendingSort implements Comparator<String> {
    @Override
    public int compare(String s, String t1) {
       String[] split1 = s.split("\\D+");
       String[] split2 = t1.split("\\D+");
       int length1 = split1.length;
       int length2 = split2.length;
       int curent = 0;
       for (int i = 1; i < Math.min(length1, length2); i++) {
           int number1 = Integer.parseInt(split1[i]);
           int number2 = Integer.parseInt(split2[i]);
           if (number1 > number2 | number1 == number2 & length1 < length2) {
               curent = -1;
           }
       }
       return curent;
    }
}
