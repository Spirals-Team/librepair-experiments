package ru.job4j.map;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

//Создать два объекта User, которые имеют одинаковые поля.
//        Создать карту Map<User, Object>
//        Добавить два объекта. Вывести карту на печать. Описать полученный результат словами.
public class UserTest {

    @Test
    public void map() {

        Calendar calendar = Calendar.getInstance();

        User first = new User("Serg", 1, calendar);


        User second = new User("Serg", 1, calendar);

        Map<User, Object> map = new HashMap<>();

        map.put(first, "first");
        map.put(second, "second");

        System.out.println(map);
        System.out.println(map.get(first));

    }

    @Test
    public void hashcodeForDifferentTypes() {

        Integer k = 55;
        System.out.println(k.hashCode());
        String str = "l";
        System.out.println(str.hashCode());

        Calendar calendar = Calendar.getInstance();

        User first = new User("Serg", 1, calendar);

        System.out.println(first.hashCode());
        System.out.println(Objects.hash(first.name, first.children, first.birthday));

    }
}