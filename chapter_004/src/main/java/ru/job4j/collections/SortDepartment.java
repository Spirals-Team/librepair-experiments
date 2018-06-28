package ru.job4j.collections;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SortDepartment {
    private HashSet<String>  set = new HashSet<>();

    /**
     * Метод добавляет строку в коллекцию. Строка разделяется на подстроки по сплитеру заданному в
     * переменной делиметр. Далее в цикле записываем в коллекцию подстроки(1 элемент - 1 подстрока, 2 элемент -
     * сумма 1 и 2 подстроки и т.д.)
     * @param code - добавляемая строка.
     */
    public void add(String code) {
        String delimeter = "\\\\";
        String[] subStr = code.split(delimeter);
        String[] newS = subStr;
        for (int i = 1; i < subStr.length; i++) {

            newS[i] = newS[i - 1] + "\\" + subStr[i];
            set.add(newS[0]);
            set.add(newS[i]);
        }
    }

    /**
     * Вспомогательный метод для теста.
     */
    public int size() {
        return set.size();
    }

    public Set<String> sortSet() {
        Set<String> sortedSet = new TreeSet<String>(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        sortedSet.addAll(set);
        return sortedSet;
    }

    public Set<String> sortSetByInc() {
        Set<String> sortedSet = new TreeSet<String>(new Comparator<String>() {
            @Override
            public int compare(String left, String right) {
                int len1 = left.length();
                int len2 = right.length();
                int lim = Math.min(len1, len2);
                char[] v1 = left.toCharArray();
                char[] v2 = right.toCharArray();
                int k = 0;
                while (k < lim) {
                char c1 = v1[k];
                char c2 = v2[k];
                if (c1 != c2) {
                    return c2 - c1;
                }
                k++;
                }
                return len1 - len2;
            }
        });
        sortedSet.addAll(set);
        return sortedSet;
    }
}
