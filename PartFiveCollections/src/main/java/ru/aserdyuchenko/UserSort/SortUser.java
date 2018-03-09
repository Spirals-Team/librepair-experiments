package ru.aserdyuchenko.UserSort;

import java.util.*;

/**
 * Created by Anton on 30.04.17.
 */
public class SortUser {
    /**
     * Sort by age.
     * @param list
     * @return sortSet
     */
    public Set<User> sort(List<User> list) {
        Set<User> sortSet = new TreeSet<>();
        sortSet.addAll(list);
        return sortSet;
    }

    /**
     * Sort by hashCode.
     * @param users - list, what need to sort.
     * @return users - sort by hashCode
     */
    public List<User> sortHash(List<User> users) {
        Collections.sort(users,
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.hashCode() - o2.hashCode();
                    }
                }
        );
        return users;
    }

    /**
     * Sort by name`s length.
     * @param users
     * @return users - sort by name`s length.
     */
    public List<User> sortLength(List<User> users) {
        Collections.sort(users,
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getName().length() - o2.getName().length();
                    }
                }
        );
        return users;
    }
}
