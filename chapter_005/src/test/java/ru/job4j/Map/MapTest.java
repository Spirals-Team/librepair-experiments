package ru.job4j.map;

import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class MapTest {

    private static final class User {

        public String name;

        public User(String name) {
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
    }

    @Test
    public void map() {
        User first = new User("Petr");
        User second = new User("Petr");

//        User test = new User("test");

        //LinkedHashMap<>(); - то же самое что и HashMap толко не меняется порядок элементов
        //TreeMap<>(); быстрый поиск и быстрая вставка т.к. это упорядоченная структура
        Map<User, String> map = new HashMap<>();
        map.put(first, "first");
        map.put(second, "second");
//        map.put(first, "asddfsdf");
        System.out.println(map.get(first));


//        что значет??
//        return Objects.hashCode(key) ^ Objects.hashCode(value)
//        System.out.println(4 ^ 3);
//        System.out.println(map);
//        System.out.println(first.equals(second));
//        assertThat(first, is(second));
    }
}

