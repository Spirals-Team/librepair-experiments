package ru.aserdyuchenko.testTaskByQuestion;

import com.google.common.base.CharMatcher;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by anton on 14.05.17.
 */
public class Sort {
    /**
     * Метод сортировки коллекции, по количеству символов в строке.
     * @param list - коллекция, которую нужно отсортировать.
     * @param ch - символ, по которому будет выполняться сортировка.
     * @return list - отсортированная коллекция.
     */
    public List<String> sort(List<String> list, char ch) {
        Collections.sort(list,
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return CharMatcher.is(ch).countIn(o1) - CharMatcher.is(ch).countIn(o2);
                    }
                });
        return list;
    }

    /**
     * Сортировка по возрастанию.
     * @param list - коллекция для сортировки.
     * @return list - отсортированная коллекция.
     */
    public List<String> sortByHigh(List<String> list) {
        Collections.sort(list,
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return o1.compareTo(o2);
                    }
                });
        return list;
    }

    /**
     * Сортировка по убыванию.
     * @param list - коллекция для сортировки.
     * @return list - отсортированная коллекция.
     */
    public List<String> sortByLow(List<String> list) {
        Collections.sort(list,
                new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        StringBuilder firstStringBuilder = new StringBuilder();
                        StringBuilder secondStringBuilder = new StringBuilder();
                        Scanner firstScan = new Scanner(o1);
                        Scanner secondScan = new Scanner(o2);
                        firstScan.useDelimiter(Pattern.compile("/"));
                        secondScan.useDelimiter(Pattern.compile("/"));
                        firstStringBuilder.append(firstScan.next());
                        secondStringBuilder.append(secondScan.next());
                        int result = secondStringBuilder.toString().compareTo(firstStringBuilder.toString());
                        while (firstScan.hasNext() && secondScan.hasNext() && result == 0) {
                            firstStringBuilder.append("/" + firstScan.next());
                            secondStringBuilder.append("/" + secondScan.next());
                            result = secondStringBuilder.toString().compareTo(firstStringBuilder.toString());
                        }
                        if (secondScan.hasNext() && result == 0) {
                            result = -1;
                        }
                        return result;
                    }
                });
        return list;
    }

    /**
     * Добавление недостающих строк.
     * @param list - исходная коллекция.
     * @return newList - Коллекция содержащая все элементы.
     */
    public List<String> addStrings(List<String> list) {
        List<String> newList = new ArrayList<>();
        newList.addAll(list);
        for(String str : list) {
            StringBuilder stringBuilder = new StringBuilder();
            Scanner scanner = new Scanner(str);
            scanner.useDelimiter(Pattern.compile("/"));
            stringBuilder.append(scanner.next());
            if (!newList.contains(stringBuilder.toString())) {
                newList.add(stringBuilder.toString());
            }
            while (scanner.hasNext()) {
                stringBuilder.append("/" + scanner.next());
                if (!newList.contains(stringBuilder.toString())) {
                    newList.add(stringBuilder.toString());
                }
            }
        }
        return newList;
    }
}
