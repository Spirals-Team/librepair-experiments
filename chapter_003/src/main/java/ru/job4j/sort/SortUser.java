package ru.job4j.sort;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SortUser {
    public Set<User> sort(List<User> list) {
        Set<User> treeSet = new TreeSet<User>();
        for (User user : list) {
            treeSet.add(user);
        }
        return treeSet;
    }

    public void sortNameLength(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int rsl = Integer.compare(o1.getName().length(), o2.getName().length());
                return rsl != 0 ? rsl : o1.getName().compareTo(o2.getName());
            }
        });
    }

    public void sortByAllFields(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int rsl = o1.getName().compareTo(o2.getName());
                return rsl != 0 ? rsl : Integer.compare(o1.getAge(), o2.getAge());
            }
        });
    }
}
