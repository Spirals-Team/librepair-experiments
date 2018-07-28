package ru.job4j.user;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SortUser {
    private User[] insertionSort(List<User> user) {
        User[] array = new User[user.size()];
        int i = 0;
        for (User item : user) {
            array[i++] = item;
        }
        for (int out = 1; out < array.length; out++) {
            User temp = array[out];
            int in = out;
            while (in > 0 && array[in - 1].compareTo(temp) >= 0) {
                array[in] = array[in - 1];
                in--;
            }
            array[in] = temp;
        }
        return array;
    }

    public Set<User> sort(List<User> user) {
        Set<User> result = new TreeSet<>();
        for (User item : insertionSort(user)) {
            result.add(item);
        }
        return result;
    }

    public List<User> sortNameLength(List<User> user) {
        user.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User user, User t1) {
                        return user.getName().length() < t1.getName().length() ? -1 : 1;
                    }
                }
        );
        return user;
    }


    public List<User> sortByAllFields(List<User> user) {
        user.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User user, User t1) {
                        int cur = user.getName().compareTo(t1.getName());
                        return cur != 0 ? cur : Integer.compare(user.getAge(), t1.getAge());
                    }
                }
        );
        return user;
    }
}
