package ru.job4j.lite;

import java.util.*;
import java.util.function.Consumer;

import static java.lang.String.*;

public class ListExamle {

//    static class Node {
//        Node next;
//    }

    static class User implements Comparable<User> {
//        static class User {
        private final String name;

        User(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            User user = (User) o;

            return name != null ? name.equals(user.name) : user.name == null;
        }

        @Override
        public int hashCode() {
            return name != null ? name.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "User{" + "name='" + name + '\'' + '}';
        }

       // @Override
        public int compareTo(User o) {
            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {

//        List<User> users = new ArrayList<User>();
        Set<User> users = new TreeSet<User>();
        users.addAll(
                Arrays.asList(
                        new  User("serg"),
                        new User("ivan"),
                        new User("zuma")
                )
        );

        System.out.println(users);



//        users.sort(
//                new Comparator<User>() {
//                    @Override
//                    public int compare(User o1, User o2) {
//                        return o1.name.compareTo(o2.name);
//                    }
//                }
//        );

//        System.out.println(users);

//        Iterator<User> it = users.iterator();

//        System.out.println(it.next());
//        System.out.println(it.next());
//        System.out.println(it.next());

//        while (it.hasNext()) {
//            System.out.println(it.next());
//        }

//        users.forEach(new Consumer<User>() {
//            @Override
//            public void accept(User user) {
//                System.out.println(user);
//            }
//        });

//        users.forEach(user -> System.out.println(user));
//        users.forEach(System.out::println);

//        for (User user : users) {
//            System.out.println(user);
//        }

        // User user = new User("serg");

//        users.add((new User("serg")));
//        users.add(user);

//        boolean result = users.contains(new User("serg"));
//        boolean result = users.contains(user);

//        System.out.println(result);

//        Node first = new Node();
//        Node second = new Node();
//        Node third = new Node();
//
//        first.next = second;
//        second.next = third;

//        List<Integer> list = new ArrayList<Integer>();
//        list.add(425);

//        list.add(425);
//
//        for (Integer value : list) {
//            System.out.println(value);
//        }
//
//        Integer value = list.get(0);
//        System.out.println(value);
//
//        list.add(1, 32);
//
//        System.out.println(list.get(1));
//
//        List<Integer> flats = new LinkedList<>(); //ArrayList<>();////
//        flats.add(4); // index = 2
//        flats.add(3); // index = 3
//
//        list.addAll(flats);
//
//        System.out.println(format("find 4 index: %s", list.indexOf(4)));
//
//        System.out.println(format("find 4 index: %s", list.lastIndexOf(4)));
//
//        list.remove(2);
//
//        System.out.println(format("find 4 index: %s", list.lastIndexOf(4)));
//
//        System.out.println(format("find 3 index: %s", list.lastIndexOf(3)));
    }
}
