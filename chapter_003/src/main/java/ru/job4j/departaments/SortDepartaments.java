package ru.job4j.departaments;

import java.util.*;

/**
 * Класс сортировки депортаментов.
 */
public class SortDepartaments {

    /**
     * Метод сортировки депортаментов.
     * В методе используется коллекция TreeSet, позволяющаю сортировать множество.
     * @param values Исходный массив строк департаментов.
     * @return Отсортированные депортаменты.
     */
    public Set<String> sortDepartments(String[] values) {
        TreeSet<String> temp = new TreeSet<>();
        for (String value: values) {
            StringBuilder builder = new StringBuilder();
            for (String s : value.split("\\\\")) {
                builder.append(s);
                temp.add(builder.toString());
                builder.append("\\");
            }
        }
        return temp;
    }

    /**
     * Метод реверсивной сортировки.
     * В методе используется метод из класса Comparator reverseOrder
     * @return Отсортированный реверсивно, массив.
     */
    public TreeSet<String> reverse(String[] departments) {
        TreeSet<String>  sortDepartments = new TreeSet<>(Comparator.reverseOrder());
          sortDepartments.addAll(sortDepartments(departments));
                return sortDepartments;
            }



}
