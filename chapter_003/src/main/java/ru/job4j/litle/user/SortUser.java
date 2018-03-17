package ru.job4j.litle.user;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * SortUser.
 * @author Hincu Andrei (andreih1981@gmail.com) by 18.09.17;
 * @version $Id$
 * @since 0.1
 */
public class SortUser {
    /**
     * Метод возвращает отсортированный сет в порядке увеличения возраста.
     * @param list неотсортированный лист.
     * @return отсортированный сет.
     */
    public Set<User> sort(List<User> list) {
        return new TreeSet<User>(list);
    }

    /**
     * Метод сортирует по длинне имени лист.
     * @param list лист.
     * @return отсортированный лист.
     */
    public List<User> sortNameLength(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                return user.getName().length() - t1.getName().length();
            }
        });
        return list;
    }

    /**
     * Метод сортирует по длинне имени лист и по возрасту.
     * @param list list.
     * @return sorted list.
     */
    public List<User> sortByAllFields(List<User> list) {
     list.sort(new Comparator<User>() {
         @Override
         public int compare(User user, User t1) {
             int nameTemp = user.getName().length() - t1.getName().length();
             if (nameTemp == 0) {
                 return user.getAge() - t1.getAge();
             }
             return nameTemp;
         }
     });
        return list;
    }
}
