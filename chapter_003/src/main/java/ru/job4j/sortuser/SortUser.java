package ru.job4j.sortuser;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * SortUser.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class SortUser {
    /**
     * Sort list.
     * @param users - list
     * @return - sorted list
     */
    public Set<User> sort(List<User> users) {
        Set<User> sorted =  new TreeSet<>(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.compareTo(o2);
                    }
                }
        );
        sorted.addAll(users);
        return sorted;
    }
    /**
     * Sort by name length.
     * @param list - list
     * @return - sorted list
     */
    public List<User> sortNameLength(List<User> list) {
         list.sort(
                 new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getName().compareTo(o2.getName());
                    }
                 }
         );
         return list;
    }
    /**
     * Sort by name length and age.
     * @param list - list
     * @return - sorted list
     */
    public List<User> sortByAllFields(List<User> list) {
        list.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return o1.getName().compareTo(o2.getName()) == 0
                                ? o1.getAge().compareTo(o2.getAge()) : o1.getName().compareTo(o2.getName());
                    }
                }
        );
        return list;
    }
}
