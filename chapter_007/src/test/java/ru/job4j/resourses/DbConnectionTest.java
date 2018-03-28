package ru.job4j.resourses;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.*;

public class DbConnectionTest {
    @Test
    public void connectionTest() throws SQLException {
        Connection connection = DbConnection.getConnection();
        try {
            String query = "Select * from users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                System.out.print(resultSet.getString(1));
                System.out.print(resultSet.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}