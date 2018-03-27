package ru.job4j.litle.sortdivision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Класс для сортировки подразделений предприятия.
 * @author Hincu Andrei (andreih1981@gmail.com) by 28.09.17;
 * @version $Id$
 * @since 0.1
 */
public class SortSubDivision {
    /**
     * Мктод для добовления недастающих элементов в список.
     * @param list исходный лист.
     * @return список со всеми подразделениями.
     */
    public List<String> refactorSubdivision(List<String> list) {
        List<String> copy = new ArrayList<>(list);
        int maxLenght = 0;
        /**
         * Нахожу максимально длинную иерархию.
         */
        for (String s : list) {
            String[]array = s.split("/");
            if (array.length > maxLenght) {
                maxLenght = array.length;
            }
        }
        /**
         * удаляю во временном списке все коненые подразделения у которых
         * одинаковые вышестоящие подразделения, остается только 1 .
         */
        for (int i = 0; i < list.size(); i++) {
            String line = list.get(i);
            String[]array = line.split("/");
            if (array.length == maxLenght) {
                String ar = "";
                for (int j = i + 1; j < list.size(); j++) {
                    ar = list.get(j);
                    String[]arr2 = ar.split("/");
                    if (array[0].equals(arr2[0]) && array[maxLenght - 2].equals(arr2[maxLenght - 2])) {
                        copy.remove(ar);
                    }
                }
            }
        }
        /**
         *Добавляю недостающие вышестоящие подразделения.
         */
        for (String s : copy) {
            String[]array = s.split("/");
            if (array.length == maxLenght) {
                String subdivision = array[0];
                if (!list.contains(subdivision)) {
                    list.add(subdivision);
                }
                for (int i = 1; i < maxLenght - 1; i++) {
                    String line = array[i];
                    subdivision += "/" + line;
                    if (!list.contains(subdivision)) {
                        list.add(subdivision);
                    }
                }
            }
        }

        return list;
    }

    /**
     * Сортировка по возрастанию.
     * @param subdivision исходный лист.
     * @return отсортированный лист.
     */
    public List<String> sortSubdivisionByIncrease(List<String> subdivision) {
        subdivision.sort(new Comparator<String>() {
            @Override
            public int compare(String s, String t) {
                String[]s1 = s.split("/");
                String[]t1 = t.split("/");
                int temp = s1[0].compareTo(t1[0]);
                if (temp == 0) {
                    temp = s1.length - t1.length;
                    if (temp == 0) {
                        for (int i = 0; i < s1.length;) {
                            temp = s1[i].compareTo(t1[i]);
                            if (temp == 0) {
                                i++;
                            } else {
                                return temp;
                            }
                        }
                    } else if (temp < 0) {
                        for (int i = 1; i < s1.length; i++) {
                            if (s1.length > i && t1.length > i) {
                                temp = s1[i].compareTo(t1[i]);
                                if (temp == 0) {
                                    temp = s1.length - t1.length;
                                }
                            }
                        }
                    }
                }
                return temp;
            }
        });
        return subdivision;

    }

    /**
     * Сортировка по убыванию.
     * @param list исходный лист.
     * @return отсортированный лист.
     */
    public List<String> sortSubdivisionByWaning(List<String> list) {
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String s, String t) {
                String[] s1 = s.split("/");
                String[] t1 = t.split("/");
                int temp = t1[0].compareTo(s1[0]);
                if (temp == 0) {
                    temp = s1.length - t1.length;
                    if (temp == 0) {
                        for (int i = 0; i < s1.length;) {
                            temp = t1[i].compareTo(s1[i]);
                            if (temp == 0) {
                                i++;
                            } else {
                                return temp;
                            }
                        }
                    } else if (temp < 0) {
                        for (int i = 1; i < s1.length; i++) {
                            if (s1.length > i && t1.length > i) {
                                temp = t1[i].compareTo(s1[i]);
                                if (temp == 0) {
                                    temp = s1.length - t1.length;
                                }
                            }
                        }
                    }
                }
                return temp;
            }
        });
        return list;
    }
}
