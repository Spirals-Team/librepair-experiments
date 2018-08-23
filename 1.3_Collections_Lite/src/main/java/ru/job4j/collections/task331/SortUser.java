package ru.job4j.collections.task331;


import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *Class SortUser.
 *@author ifedorenko
 *@since 08.09.2017
 *@version 1
 */
public class SortUser {
    /**
     * Method sort.
     * @param list list of users
     * @return TreeSet
     */
    public Set<User> sort(List<User> list) {
        Set<User> result = new TreeSet<>();
        for (User user : list) {
            result.add(user);
        }
        return result;
    }

    /**
     * Method sort by length.
     * @param list list of users
     * @return sorted list
     */
    public List<User> sortNameLength(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                Integer length1 = Integer.valueOf(o1.getName().length());
                Integer length2 = Integer.valueOf(o2.getName().length());
                return length1 == length2 ? 0 : length1 > length2 ? 1 : -1;
            }
        });
        return list;
    }

    /**
     * Method sort by name and age.
     * @param list list of users
     * @return sorted list
     */
    public List<User> sortByAllFields(List<User> list) {
        list.sort(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                int result = 0;
                String name1 = o1.getName();
                String name2 = o2.getName();
                int nameComp = name1.compareTo(name2);
                if (nameComp != 0) {
                    result = nameComp;
                } else {
                    Integer age1 = Integer.valueOf(o1.getAge());
                    Integer age2 = Integer.valueOf(o2.getAge());
                    result = age1.compareTo(age2);
                }
                return result;
            }
        });
        return list;
    }
}
