package ru.job4j.sort;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SortUserTest {

    @Test
    public void whenAddNotSorListInTree() {
        SortUser sortUser = new SortUser();
        List<User> list = new ArrayList<User>();
        list.addAll(
                Arrays.asList(
                        new User("Petr", 48),
                        new User("Mike", 22),
                        new User("Anton", 34),
                        new User("Vlad", 34),
                        new User("Alex", 22)
                )
        );
        Set<User> expect = new TreeSet<User>();
        expect.add(new User("Alex", 22));
        expect.add(new User("Mike", 22));
        expect.add(new User("Anton", 34));
        expect.add(new User("Vlad", 34));
        expect.add(new User("Petr", 48));
        assertThat(sortUser.sort(list), is(expect));
    }

    @Test
    public void whenSortListByLengthName() {
        SortUser sortUser = new SortUser();
        List<User> testList = new ArrayList<User>();
        testList.addAll(
                Arrays.asList(
                        new User("Petr", 48),
                        new User("Vadim", 22),
                        new User("Anton", 34),
                        new User("Vlad", 34),
                        new User("An", 22)
                )
        );
        List<User> expect = new ArrayList<User>();
        expect.addAll(
                Arrays.asList(
                        new User("An", 22),
                        new User("Petr", 48),
                        new User("Vlad", 34),
                        new User("Anton", 34),
                        new User("Vadim", 22)

                )
        );
        sortUser.sortNameLength(testList);
        assertThat(testList, is(expect));
    }

    @Test
    public void whenSortListByAllFields() {
        SortUser sortUser = new SortUser();
        List<User> testList = new ArrayList<User>();
        testList.addAll(
                Arrays.asList(
                        new User("Petr", 48),
                        new User("Mike", 23),
                        new User("Petr", 34),
                        new User("Vlad", 34),
                        new User("Vlad", 14),
                        new User("Mike", 22)
                )
        );
        List<User> expect = new ArrayList<User>();
        expect.addAll(
                Arrays.asList(
                        new User("Mike", 22),
                        new User("Mike", 23),
                        new User("Petr", 34),
                        new User("Petr", 48),
                        new User("Vlad", 14),
                        new User("Vlad", 34)
                )
        );
        sortUser.sortByAllFields(testList);
        assertThat(testList, is(expect));
    }
}