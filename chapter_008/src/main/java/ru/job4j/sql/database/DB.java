package ru.job4j.sql.database;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import ru.job4j.sql.Sql;
import ru.job4j.sql.items.Advert;
import ru.job4j.sql.items.Author;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Класс бд подключения и запросов.
 * @author Hincu Andrei (andreih1981@gmail.com)on 23.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class DB {

    private Connection connection;
    private String url;
    private String user;
    private  String password;
    private static final Logger LOG = LogManager.getLogger(Sql.class);

    public DB(String settingsFile) {
        loadSettings(settingsFile);
        try {
            this.connection = DriverManager.getConnection(url, user, password);
            System.out.println("Соединение с бд установлено.");
            createTables();
        } catch (SQLException e) {
            LOG.error("Connection ERROR", e);
        }
    }

    /**
     * Метод загружает параметры подключения к бд из файла.
     * @param settingsFile имя файла настроек подключения.
     */
    private void loadSettings(String settingsFile) {
        try (InputStream in = getClass().getClassLoader().getResourceAsStream(settingsFile)) {
            Properties pr = new Properties();
            pr.load(in);
            this.url = pr.getProperty("db.url");
            this.user = pr.getProperty("db.user");
            this.password = pr.getProperty("db.password");
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Метод создает таблицы и функции в бд если их еще нет.
     */
    public void createTables() {
        try (final Statement statement = connection.createStatement()) {
            statement.executeUpdate(SqlQuery.CREATE_AUTHOR_TABLE);
            statement.executeUpdate(SqlQuery.CREATE_TABLE_ADVERTS);
            statement.executeUpdate(SqlQuery.CREATE_FUNCTION_ADD_AUTHOR);
            statement.executeUpdate(SqlQuery.CREATE_FUNCTION_ADD_ADVERT);
        } catch (SQLException e) {
            e.printStackTrace();
            LOG.error("Error when was create table and functions", e);
        }
    }

    /**
     * Метод возвращает дату последнего обновления в милисекундах.
     * @return 0 если бд пустая или дата последнего обновления.
     */
    public long getLastTimeOfUpdate() {
        long dataMax = 0;
        try (final Statement st = this.connection.createStatement()) {
            try (final ResultSet rs = st.executeQuery(SqlQuery.SELECT_MAX_DATE)) {
                if (rs.next()) {
                    if (rs.getTimestamp("max_date") != null) {
                        dataMax = rs.getTimestamp("max_date").getTime();
                    }
                }
            }
        } catch (SQLException e) {
            LOG.error("Query error", e);
        }
        return dataMax;
    }

    /**
     * Метод сохраняет обьявление в бд и записывает в лог id и само объявление.
     * @param advert объявление.
     */
    public void addNewAdvert(Advert advert) {
        try (final PreparedStatement ps = this.connection.prepareStatement(SqlQuery.ADD_ADVERT)) {
            Author author = advert.getAuthor();
            ps.setString(1, author.getName());
            ps.setString(2, author.getUrl());
            ps.setString(3, advert.getTitle());
            ps.setString(4, advert.getUrl());
            ps.setString(5, advert.getText());
            ps.setTimestamp(6, new Timestamp(advert.getPublicationDate().getTimeInMillis()));
            ps.setTimestamp(7, new Timestamp(advert.getDate().getTimeInMillis()));
            try (final ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("add_advert");
                    advert.setId(id);
                    LOG.debug(advert + "was saved with id " + id);
                }
            }
        } catch (SQLException e) {
            LOG.error("Error when advert was add in db", e);
        }
    }
}
