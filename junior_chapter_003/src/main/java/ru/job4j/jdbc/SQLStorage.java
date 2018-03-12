package ru.job4j.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 03.03.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class SQLStorage {
    private static final Logger LOG = LoggerFactory.getLogger(SQLStorage.class);

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/java_a_from_z";
        String username = "postgres";
        String password = "postgres";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
//            PreparedStatement ps = conn.prepareStatement("INSERT INTO type (name) VALUES (?)");
//            ps.setString(1, "Крахмал");
//            int rowsDelete = ps.executeUpdate();
//            ps.close();
//            PreparedStatement ps = conn.prepareStatement("SELECT * FROM type AS t WHERE t.id IN (?, ?)");
//            ps.setInt(1, 1);
//            ps.setInt(2, 2);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM type");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println(String.format("%s %s", rs.getInt("id"), rs.getString("name")));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
    }
}
