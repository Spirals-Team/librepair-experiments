package ru.job4j.sort;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SortUser {

    /**
     * Метод преобразует список пользователей в отсортированное множество
     * @param users список пользователей
     * @return отсортированное множество
     */
    public static TreeSet<User> sort (final List<User> users) {
        return new TreeSet<User>(users);
    }

    /**
     * Метод сортирует список пользователей по длине имени
     * @param users список пользователей
     * @return отсортированный по длине имени список
     */
    public static List<User> sortNameLength (final List<User> users) {
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                Integer len = o1.getName().length();
                return len.compareTo(o2.getName().length());
            }
        });
        return users;
    }

    /**
     * Метод сортирует список пользователей по имени и потом по возрасту
     * @param users список пользователей
     * @return отсортированный список
     */
    public static List<User> sortByAllFields (final List<User> users) {
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int result;
                // если имена одинаковые - то сравниваем по возрасту
                if (o1.getName().equals(o2.getName())) {
                    result = o1.getAge().compareTo(o2.getAge());
                } else {
                    result = o1.getName().compareTo(o2.getName());
                }
                return result;
            }
        });
        return users;
    }
}
