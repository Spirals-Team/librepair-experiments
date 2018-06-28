package ru.job4j.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class TrackerJDBC implements AutoCloseable {
    private String dbUrl;
    private Properties prop;

    /**
     * Конструктор считывает ссылку на бд из проперти, используя полученную ссылку создает соединение с бд.
     * Используя скрипт из проперти создает таблицу Items в базе данных
     */
    public TrackerJDBC() {
        prop = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\info.properties")) {
            prop.load(fileInputStream);
            this.dbUrl = prop.getProperty("bdurl");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection(dbUrl);
             Statement statement = con.createStatement()) {
            statement.addBatch(prop.getProperty("createtable"));
            statement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод реализующий добавление заявки в бд.
     * @param item новая заявка.
     */
    public Item add(Item item) {
        try (Connection con = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = con.prepareStatement(prop.getProperty("insert"), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            statement.setString(2, item.getDesc());
            statement.setLong(3, item.getCreated());
            statement.setString(4, item.getComments());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

        /**
     * Метод реализаущий поиск заявки в бд по заданному id.
     * @param id искомой заявки.
     */
    public Item findById(int id) {
        Item result = new Item();
        try (Connection con = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = con.prepareStatement(prop.getProperty("findid"))) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
            result.setName(rs.getString(2));
            result.setDesc(rs.getString(3));
            result.setCreated(rs.getLong(4));
            result.setComments(rs.getString(5));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод реализаущий поиск всех непустых ячеек в бд.
     * @return возвращает список заявок, находящихся в бд.
     */
    public ArrayList<Item> findAll() {
        ArrayList<Item> result = new ArrayList<>();
        try (Connection con = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = con.prepareStatement(prop.getProperty("findall"));
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Item item = new Item();
                item.setId(rs.getString(1));
                item.setName(rs.getString(2));
                item.setDesc(rs.getString(3));
                item.setCreated(rs.getLong(4));
                item.setComments(rs.getString(5));
                result.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Метод, реализующий замену заявки с заданным id в бд на новую заявку itemrpl.
     * @param idrpl - id заявки, которую заменяем на новую заявку itemrpl.
     */
    public void replace(String idrpl, Item itemrpl) {
        try (Connection con = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = con.prepareStatement(prop.getProperty("update"))) {
            statement.setString(5, idrpl);
            statement.setString(1, itemrpl.getName());
            statement.setString(2, itemrpl.getDesc());
            statement.setLong(3, itemrpl.getCreated());
            statement.setString(4, itemrpl.getComments());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод реализуtn удаление заявки с заданным id .
     * @param id удаляемой заявки.
     */
    public void delete(String id) {
        try (Connection con = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = con.prepareStatement(prop.getProperty("delete"))) {
            statement.setString(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод реализует  поиск в хранилище заявки с заданным именем.
     * @param key имя искомой заявки.
     */
    public Item findByName(String key) {
        Item result = new Item();
        try (Connection con = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = con.prepareStatement(prop.getProperty("findname"))) {
            statement.setString(1, key);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    result.setId(rs.getString(1));
                    result.setName(rs.getString(2));
                    result.setDesc(rs.getString(3));
                    result.setCreated(rs.getLong(4));
                    result.setComments(rs.getString(5));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {

    }
}
