package ru.job4j.doptask;

import java.util.*;

/**
 * Класс для редактирования и сортировки справочника подразделений.
 */
public class StructureDepartment {
    /**
     * Метод для добавления недостающих элементов справочника.
     *
     * @param inArray принимаем реализацию List.
     * @return вернем реализацию List.
     */
    private List<String> theEditArray(List<String> inArray) {
        List<String> outArray = new ArrayList<>();
        for (String x : inArray) {
            String[] arrStr = x.split("\\\\");
            String temp = "";
            for (String in : arrStr) {
                temp = temp + in;
                if (!outArray.contains(temp)) {
                    outArray.add(temp);
                }
                temp = temp + "\\";
            }
        }
        return outArray;
    }

    /**
     * Метод для сортировки по возрастанию.
     *
     * @param inArray принимаем реализацию List.
     * @return вернем реализацию List.
     */
    public List<String> sortingTop(List<String> inArray) {
        inArray = theEditArray(inArray);
        inArray.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = 0;
                if (o1.compareTo(o2) != 0) {
                    int size = o1.length() < o2.length() ? o1.length() : o2.length();
                    for (int i = 0; i < size; i++) {
                        if (o1.charAt(i) != o2.charAt(i)) {
                            result = o1.charAt(i) > o2.charAt(i) ? 1 : -1;
                            break;
                        }
                    }
                }
                return result != 0 ? result : 1;
            }
        });
        return inArray;
    }

    /**
     * Метод для сортировки по убыванию.
     *
     * @param inArray принимаем реализацию List.
     * @return вернем реализацию List.
     */
    public List<String> sortingDown(List<String> inArray) {
        inArray = theEditArray(inArray);
        inArray.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                int result = 0;
                if (o1.compareTo(o2) != 0) {
                    int size = o1.length() < o2.length() ? o1.length() : o2.length();
                    for (int i = 0; i < size; i++) {
                        if (o1.charAt(i) != o2.charAt(i)) {
                            result = o1.charAt(i) < o2.charAt(i) ? 1 : -1;
                            break;
                        }
                    }
                }
                return result != 0 ? result : 1;
            }
        });
        return inArray;
    }
}
