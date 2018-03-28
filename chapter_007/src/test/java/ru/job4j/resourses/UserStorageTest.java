package ru.job4j.resourses;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserStorageTest {
    private UserStorage storage = new UserStorage();

    public UserStorageTest() throws SQLException {
    }

    @Test
    public void insertTest() {
        storage.insertUser("user1", "log1");
        storage.insertUser("user2", "log2");
        System.out.println(storage.getData());
    }

    @Test
    public void deleteTest() {
        storage.delete("user1");
        System.out.println(storage.getData());
    }

    @Test
    public void printTest() {
        System.out.println(storage.getData());
    }
}