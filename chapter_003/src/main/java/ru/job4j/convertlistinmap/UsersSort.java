package ru.job4j.convertlistinmap;
/**
 * класс содержит метод который будет сортировать  людей по возрасту в порядке возростания
 */


import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UsersSort {

    /**
     * будем принимать лист ,  добавляем в трисет  коллекцию и возвращаем
     *
     * @param list
     * @return
     */
    public Set<User> sort(List<User> list) {
        TreeSet<User> returnUsers = new TreeSet<>();
        returnUsers.addAll(list);
        return returnUsers;
    }

    /**
     * сортировка по длинне имени
     * @param userList
     * @return
     */
    public List<User> sortNameLength(List<User> userList) {
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.compare(o1.getName().length(), o2.getName().length());
            }
        });
        return userList;
    }

    /**
     * сортировка лексикографическая, и по возрасту
     * @param userList
     * @return
     */
    public List<User> sortByAllFields(List<User> userList) {
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                if (o1.getName().compareTo(o2.getName()) == 0) {
                    return o1.getAge().compareTo(o2.getAge());
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        return userList;
    }
}
