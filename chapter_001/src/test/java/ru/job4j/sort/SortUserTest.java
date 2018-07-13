package ru.job4j.sort;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SortUserTest {

    /**
     * Тестирование конвертации списка в дерево с заданным порядком сортировки.
     * Проверяем что наименьший по возрасту элемент расположен в дереве первым
     */
    @Test
    public void getConverUsersListToTree() {
        List<User> users = new ArrayList<>();
        users.add( new User("igorek", 35));
        users.add( new User("alex", 15));
        users.add( new User("svetlana", 25));
        users.add( new User("anton", 33));
        TreeSet<User> result = SortUser.sort(users);
        assertThat(result.first().equals(new User ("alex",15)), is(true));
    }

    /**
     * Тестируем сортировку списка по длине имени
     */
    @Test
    public void getUsersListSortByNameLenght() {
        List<User> users = new ArrayList<>();
        users.add( new User("igorek", 35));
        users.add( new User("alex", 15));
        users.add( new User("svetlana", 25));
        users.add( new User("anton", 33));
        List<User> result = SortUser.sortNameLength(users);
        assertThat(result.get(2).equals(new User ("igorek",35)), is(true));
    }

    /**
     * Тестируем сортировку списка по имени а потом по возрасту
     */
    @Test
    public void getUsersListSortByAllFields() {
        List<User> users = new ArrayList<>();
        users.add( new User("igorek", 35));
        users.add( new User("alex", 15));
        users.add( new User("svetlana", 25));
        users.add( new User("anton", 33));
        users.add( new User("alex", 12));
        users.add( new User("svetlana", 27));
        users.add( new User("anton", 30));
        List<User> result = SortUser.sortByAllFields(users);
        assertThat(result.get(3).equals(new User ("anton",33)), is(true));
    }

}