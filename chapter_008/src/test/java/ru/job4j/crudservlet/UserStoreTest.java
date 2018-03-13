package ru.job4j.crudservlet;

import org.junit.Test;


import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserStoreTest {

    @Test
    public void whenAddUserThenThereIsThisUserInUsers() {

        UserStore userStore = UserStore.getInstance();

        userStore.workWithDataBase.deleleALLUsers();

        userStore.add("name1", "login1", "email1");
        userStore.add("name2", "login2", "email2");

        Integer numOfUsers = userStore.getQuontityOfUsers();
        Integer expectedNumOfUsers = 2;

        assertThat(numOfUsers, is(expectedNumOfUsers));
    }

    @Test
    public void whenAddTheSameLoginUserThenThenTheNumberOfUserNoChange() {

        UserStore userStore = UserStore.getInstance();

        userStore.workWithDataBase.deleleALLUsers();

        userStore.add("name1", "login1", "email1");
        userStore.add("name2", "login1", "email2");

        Integer numOfUsers = userStore.getQuontityOfUsers();
        Integer expectedNumOfUsers = 1;

        assertThat(numOfUsers, is(expectedNumOfUsers));
    }

    @Test
    public void whenDeleteUserThenThereIsNoThisUser() {

        UserStore userStore = UserStore.getInstance();

        userStore.workWithDataBase.deleleALLUsers();

        userStore.add("name1", "login1", "email1");
        userStore.add("name2", "login2", "email2");
        userStore.delete("login2");

        Integer numOfUsers = userStore.getQuontityOfUsers();
        Integer expectedNumOfUsers = 1;

        assertThat(numOfUsers, is(expectedNumOfUsers));
    }

    @Test
    public void whenEditUserThenThereIsThisUserIsEdited() {

        UserStore userStore = UserStore.getInstance();

        userStore.workWithDataBase.deleleALLUsers();

        User theSameUser = new User("nameEdited", "login2", "emailEdited");

        userStore.add("name1", "login1", "email1");
        userStore.add("name2", "login2", "email2");
        userStore.edit("nameEdited", "login2", "emailEdited");

        String expected = "\n" + theSameUser.toString();
        String result = userStore.get("login2");

        assertThat(result, is(expected));
    }
}