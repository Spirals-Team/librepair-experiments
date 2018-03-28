package ru.job4j.resourses;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private Connection connection = DbConnection.getConnection();
    private List<String> users = new ArrayList<>();

    public UserStorage() throws SQLException {
    }

    public List<String> getUsers() {
        return users;
    }

    public void insertUser(String name, String login) {
        try {
            String query = "INSERT INTO users(name, login) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(String name) {
        try {
            String query = "DELETE FROM users WHERE name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getData() {
        StringBuilder builder = new StringBuilder();
        try {
            String query = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                builder.append("User: [");
                builder.append(resultSet.getString(1));
                builder.append("] Login: [");
                builder.append(resultSet.getString(2));
                builder.append("]\n");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
