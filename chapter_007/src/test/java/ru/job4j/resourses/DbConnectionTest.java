package ru.job4j.resourses;

import org.junit.Test;
import ru.job4j.models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;

public class DbConnectionTest {
    private UserStorage storage = UserStorage.getInstance();

    @Test
    public void connectionTest() throws SQLException {
        List<User> list = storage.getAllUsers();
        System.out.println(list.get(0).getName());
        System.out.println(list.get(0).getLogin());
    }
}