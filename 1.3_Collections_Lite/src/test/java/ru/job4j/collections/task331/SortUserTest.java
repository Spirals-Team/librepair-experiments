package ru.job4j.collections.task331;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class SortUserTest.
 * @author Igor Fedorenko (mailto:if.zommy@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SortUserTest {
    /**
     * Test sort by age.
     */
    @Test
    public void whenAddListOfUserThenReturnSortedSetByAge() {
        SortUser su = new SortUser();
        User user1 = new User("Andrey", 23);
        User user2 = new User("Masha", 25);
        User user3 = new User("Igor", 29);
        User user4 = new User("Dasha", 21);

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        Set<User> expect = new TreeSet<>();
        expect.add(user1);
        expect.add(user2);
        expect.add(user3);
        expect.add(user4);

        Set<User> result = su.sort(list);
        assertThat(result, is(expect));
    }

    /**
     * Test sort by length by name.
     */
    @Test
    public void whenAddListOfUserThenReturnSortedSetByLengthByName() {
        SortUser su = new SortUser();
        User user1 = new User("Andrey", 23);
        User user2 = new User("Masha", 25);
        User user3 = new User("Igor", 29);
        User user4 = new User("Dasha", 21);

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        List<User> expect = new ArrayList<>();
        expect.add(user3); //Igor
        expect.add(user2); //Masha
        expect.add(user4); //Dasha
        expect.add(user1); //Andrey

        List<User> result = su.sortNameLength(list);
        assertThat(result, is(expect));
    }

    /**
     * Test sort by name and age.
     */
    @Test
    public void whenAddListOfUserThenReturnSortedSetByNameAndAge() {
        SortUser su = new SortUser();
        User user1 = new User("Andrey", 23);
        User user2 = new User("Igor", 27);
        User user3 = new User("Igor", 21);
        User user4 = new User("Dasha", 21);
        User user5 = new User("Andrey", 20);

        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
        list.add(user5);

        List<User> expect = new ArrayList<>();
        expect.add(user5); //Andrey 20
        expect.add(user1); //Andrey 23
        expect.add(user4); //Dasha 21
        expect.add(user3); //Igor 21
        expect.add(user2); //Igor 27

        List<User> result = su.sortByAllFields(list);
        assertThat(result, is(expect));
    }

}
