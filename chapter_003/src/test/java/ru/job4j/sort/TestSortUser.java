package ru.job4j.sort;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Класс теста SortUser and User compareTo.
 */
public class TestSortUser {
    /**
     * compareTo
     */
    @Test
    public void sortTreeSetTheFirstLessOfTheSecond() {
        assertThat(new User("Vova", 28).compareTo(new User("Roma", 30)), is(-1));
    }

    /**
     * compareTo
     */
    @Test
    public void sortTreeSetTheFirstMoreThanTheSecond() {
        assertThat(new User("Vova", 30).compareTo(new User("Roma", 28)), is(1));
    }

    /**
     * compareTo
     */
    @Test
    public void sortTreeSetTheFirstIsEqualToSecond() {
        assertThat(new User("Vova", 30).compareTo(new User("Roma", 30)), is(-1));
    }

    /**
     * Тест сортировки по длинне символов в имени.
     */
    @Test
    public void sortNameLenght() {
        SortUser sortUser = new SortUser();
        List<User> users = new ArrayList<>();
        users.add(new User("Vyacheslav", 30));
        users.add(new User("Roma", 30));
        List<User> resault = sortUser.sortNameLenght(users);
        assertEquals(4, resault.get(0).getName().length());
        assertEquals(10, resault.get(1).getName().length());
    }

    /**
     * Тест сортировки если имена равны сравниваем по возрасту.
     */
    @Test
    public void sortByAllFields() {
        SortUser sortUser = new SortUser();
        List<User> users = new ArrayList<>();
        users.add(new User("Vyacheslav", 35));
        users.add(new User("Vyacheslav", 30));
        List<User> resault = sortUser.sortByAllFields(users);
        assertEquals(30, resault.get(0).getAge());
        assertEquals(35, resault.get(1).getAge());
    }

}
