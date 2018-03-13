package ru.job4j.tree;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TreeExampleTest {

//    public static final class User implements Comparable<User> {
public static final class User { //implements Comparable<User> {
        public String name;

//        @Override
//        public int compareTo(User o) {
//            return this.name.compareTo(o.name);
//        }

        public User(String name) {
            this.name = name;
        }
    }

    @Test
    public void add() {
        TreeSet<User> users = new TreeSet<>(new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.name.compareTo(o2.name);
            }
        });
        users.add(new User("petr"));
        users.add(new User("arsentev"));

        TreeMap<User, String> map;



        for (User user : users) {
            System.out.println(user.name);
        }
    }





}
