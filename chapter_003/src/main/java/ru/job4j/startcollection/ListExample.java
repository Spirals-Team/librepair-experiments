package ru.job4j.startcollection;
/**
 * тестирование дженериков
 */

import java.util.*;
import java.util.function.Consumer;

public class ListExample {
    static class Users implements Comparable<Users> {
        private final String name;

        Users(String name) {
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
            Users users = (Users) o;
            return Objects.equals(name, users.name);
        }

        @Override
        public int hashCode() {

            return Objects.hash(name);
        }

        @Override
        public String toString() {
            return "Users{" + "name='" + name + '\'' + '}';
        }


        @Override
        public int compareTo(Users o) {
            return this.name.compareTo(o.name);
        }
    }

    public static void main(String[] args) {
        List<Users> users = new ArrayList<Users>();
        users.addAll(Arrays.asList(new Users("zuma"), new Users("peter"), new Users("Alex")));
        Set<Users> users2 = new TreeSet<Users>();
        users2.addAll(Arrays.asList(new Users("zuma"), new Users("peter"), new Users("Alex")));

        boolean result = users.contains(new Users("Alex"));
        System.out.println(result);

        Iterator<Users> iterator = users.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        users.sort(
                new Comparator<Users>() {
                    @Override
                    public int compare(Users o1, Users o2) {
                        return o1.name.compareTo(o2.name);
                    }
                }
        );
        users.forEach(user -> System.out.println(user));
    }
}
