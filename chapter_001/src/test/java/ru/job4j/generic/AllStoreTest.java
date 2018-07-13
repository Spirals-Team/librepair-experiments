package ru.job4j.generic;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Тесты для хранилищ
 */
public class AllStoreTest {

    @Test
    /**
     * Тесты для UserStore
     */
    public void getAllTestUserStory() {
        UserStore test = new UserStore();
        test.add(new User("First"));
        test.add(new User("Second"));
        test.add(new User("Three"));
        test.add(new User("Four"));
        test.add(new User("Five"));
        assertThat(test.findByIndex(1).getId(), is("Second"));
        test.replace("Four", new User("Six"));
        assertThat(test.findByIndex(3).getId(), is("Six"));
        test.delete("First");
        assertThat(test.findByIndex(0).getId(), is("Second"));
        assertThat(test.findById("Three").getId(), is("Three"));
    }

    @Test
    /**
     * Тесты для RoleStore
     */
    public void getAllTestRoleStory() {
        RoleStore test = new RoleStore();
        test.add(new Role("First"));
        test.add(new Role("Second"));
        test.add(new Role("Three"));
        test.add(new Role("Four"));
        test.add(new Role("Five"));
        assertThat(test.findByIndex(1).getId(), is("Second"));
        test.replace("Four", new Role("Six"));
        assertThat(test.findByIndex(3).getId(), is("Six"));
        test.delete("Six");
        assertThat(test.findByIndex(3).getId(), is("Five"));
        assertThat(test.findById("Three").getId(), is("Three"));
    }

}