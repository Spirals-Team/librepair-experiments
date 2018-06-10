package ru.job4j.search;

import org.junit.Test;
import java.util.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortUserTest {
    @Test
    public void whenSortListToTreeSetByAge() {
        UserNew user1 = new UserNew();
        UserNew user2 = new UserNew();
        UserNew user3 = new UserNew();
        user1.setName("Alex");
        user2.setName("Mike");
        user3.setName("Tom");
        user1.setAge(20);
        user2.setAge(22);
        user3.setAge(19);
        List<UserNew> users = new ArrayList<UserNew>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        TreeSet<UserNew> treeUser = new TreeSet<>();
        treeUser.add(user3);
        treeUser.add(user2);
        treeUser.add(user1);
        Set<UserNew> result = new SortUser().sort(users);
        assertThat(result, is(treeUser));
    }

    @Test
    public void whenSortListByNameLength() {
        UserNew user1 = new UserNew();
        UserNew user2 = new UserNew();
        UserNew user3 = new UserNew();
        user1.setName("Alexei");
        user2.setName("Mike");
        user3.setName("Tom");
        user1.setAge(20);
        user2.setAge(22);
        user3.setAge(19);
        List<UserNew> users = new ArrayList<UserNew>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        List<UserNew> result = new SortUser().sortNameLength(users);
        List<UserNew> listUser = new ArrayList<UserNew>();
        listUser.add(user3);
        listUser.add(user2);
        listUser.add(user1);
        assertThat(result, is(listUser));
    }

    @Test
    public void whenSortListByAllFields() {
        UserNew user1 = new UserNew();
        UserNew user2 = new UserNew();
        UserNew user3 = new UserNew();
        UserNew user4 = new UserNew();
        user1.setName("Sergei");
        user2.setName("Ivan");
        user3.setName("Sergei");
        user4.setName("Ivan");
        user1.setAge(25);
        user2.setAge(30);
        user3.setAge(20);
        user4.setAge(25);
        List<UserNew> users = new ArrayList<UserNew>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        List<UserNew> result = new SortUser().sortByAllFields(users);
        List<UserNew> listUser = new ArrayList<UserNew>();
        listUser.add(user4);
        listUser.add(user2);
        listUser.add(user3);
        listUser.add(user1);
        assertThat(result, is(listUser));
    }
}