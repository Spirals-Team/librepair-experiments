package ru.job4j.sort;

import java.util.*;

/**
 * Класс SortUser/
 */
public class SortUser {
    /**
     * Метод sort. Сортирует по алфавиту.
     * @param list принимаем List Users.
     * @return вернем сортированный Set Users.
     */
    public Set<User> sort(List<User> list) {
        Set<User> resault = new TreeSet<>();
        for (User user : list) {
            resault.add(user);
        }
        return resault;
    }

    /**
     * Метод sortNameLenght. Сортирует по длине имени.
     * @param users принимаем List Users.
     * @return вернем сортированный List Users.
     */
    public List<User> sortNameLenght(List<User> users) {
        users.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        final int rsl = Integer.compare(o1.getName().length(), o2.getName().length());
                        return rsl != 0 ? rsl : -1;
                    }
                });
        return users;
    }

    /**
     * Метод sortByAllFields. Сортирует по длинне имени. Если длинна одинакова сортирует по возросту.
     * @param users принимаем List Users.
     * @return вернем сортированный List Users.
     */
    public List<User> sortByAllFields(List<User> users) {
        users.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        final int rsl = Integer.compare(o1.getName().length(), o2.getName().length());
                        return rsl != 0 ? rsl : Integer.compare(o1.getAge(), o2.getAge());
                    }
                });
        return users;
    }
}
