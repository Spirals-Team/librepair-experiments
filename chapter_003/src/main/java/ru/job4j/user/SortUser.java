package ru.job4j.user;

import java.util.*;

public class SortUser {
    public Set<User> sort(List<User> users) {
        return new TreeSet<>(users);
    }

    public List<User> sortNameLength(List<User> list) {
        List<User> userList = new ArrayList<>();
        userList.addAll(list);
        userList.sort(new Comparator<User>() {
            @Override
            public int compare(User first, User second) {
                return Integer.compare(first.getName().length(), second.getName().length());
            }
        });
        return userList;
    }

    public List<User> sortByAllFields(List<User> users) {
        users.sort(new Comparator<User>() {
            @Override
            public int compare(User first, User second) {
                int rsl = first.getName().compareTo(second.getName());
                return rsl != 0 ? rsl : Integer.compare(first.getAge(), second.getAge());
            }
        });
        return users;
    }
}


