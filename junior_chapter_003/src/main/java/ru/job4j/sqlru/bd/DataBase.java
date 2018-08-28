package ru.job4j.sqlru.bd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.sqlru.items.Vacancy;

import java.sql.*;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 18.04.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class DataBase {
    private static final Logger LOG = LoggerFactory.getLogger(DataBase.class);
    private Connection connection = null;

    public DataBase(Connection connection) {
        this.connection = connection;
        createTables();
    }

    public void createTables() {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(Query.CREATE_TABLE_VACANCY);
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    public long getMaxDate() {
        long maxDate = 0;
        try {
            Statement stMaxDate = connection.createStatement();
            ResultSet rs = stMaxDate.executeQuery(Query.SELECT_MAX_DATE);
            if (rs.next()) {
                if (rs.getTimestamp("max_date") != null) {
                    maxDate = rs.getTimestamp("max_date").getTime();
                }
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
        return maxDate;
    }

    public void addData(Vacancy vacancy) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO vacancy(title, url, text, autor, date) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, vacancy.getTitle());
            ps.setString(2, vacancy.getUrl());
            ps.setString(3, vacancy.getText());
            ps.setString(4, vacancy.getAuthor());
            ps.setTimestamp(5, new Timestamp(vacancy.getDate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
