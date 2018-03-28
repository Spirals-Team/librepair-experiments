package ru.job4j.collections.iterator;

import java.util.*;

public class SortUser {
    public Set<User> sort(List<User> list) {
        list.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        return Integer.compare(o1.getAge(), o2.getAge());
                    }
                }
        );
        Set<User> result = new TreeSet<>();
        result.addAll(list);
        return result;
    }

    public List<User> sortNameLength(List<User> list) {
        list.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        int result = Integer.compare(o1.getName().length(), o2.getName().length());
                        int age = Integer.compare(o1.getAge(), o2.getAge());
                        return o1.getName().length() != o2.getName().length() ? result : age;
                    }
                }
        );
        return list;
    }

    public List<User> sortByAllFields(List<User> list) {
        list.sort(
                new Comparator<User>() {
                    @Override
                    public int compare(User o1, User o2) {
                        int result = o1.getName().compareTo(o2.getName());
                        int age = Integer.compare(o1.getAge(), o2.getAge());
                        return result != 0 ? result : age;
                    }
                }
        );
        return list;
    }
}
