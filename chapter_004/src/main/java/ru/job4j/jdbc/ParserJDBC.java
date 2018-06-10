package ru.job4j.jdbc;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class ParserJDBC implements AutoCloseable {
    private String dbUrl;
    private Properties prop;
    private HashMap<Integer, String> map = new HashMap();
    final static Logger LOGGER = Logger.getLogger(ParserJDBC.class.getName());

    /**
     * Конструктор забирает ссылку на базу из проперти, осуществляет соединение с бд,
     * создает таблицу с вакансиями, если ее нет, выгребает записи из таблицы, если они там есть в мапу.
     */
    public ParserJDBC() {
        PropertyConfigurator.configure("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\log4j.properties");
        prop = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("C:\\Users\\rig0\\projects\\azuryanov\\azuryanov\\chapter_004\\src\\main\\java\\ru\\job4j\\jdbc\\parser.properties")) {
            prop.load(fileInputStream);
            this.dbUrl = prop.getProperty("bdurl");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Connection con = DriverManager.getConnection(dbUrl);
             Statement statement = con.createStatement()) {
            LOGGER.info("Connecting to db");
            statement.addBatch(prop.getProperty("createtable"));
            statement.executeBatch();
            ResultSet rs = statement.executeQuery(prop.getProperty("select"));
            while (rs.next()) {
                map.put(rs.getInt(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод добавляет записи по вакансиям в бд.
     * Осуществляется открытие соединения с бд, из проперти берем запрос для записи в бд данных
     * если запись уже есть в мапе(проверка по номеру айди), то добавления в бд не происходит, т.к. такая запись уже в бд есть
     * @param id - id номер вакансии, распарсен с сайта.
     * @param conteiner - запись о вакансии, состоит из айди, урлаи  текста.
     */
    public void add(int id, Conteiner conteiner) {
        try (Connection con = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = con.prepareStatement(prop.getProperty("insert"))) {
                 //Проверка что элемент уже есть в бд
                 if (!map.containsKey(id)) {
                     statement.setInt(1, conteiner.getId());
                     statement.setString(2, conteiner.getName());
                     statement.setString(3, conteiner.getUrl());
                     statement.execute();
                     LOGGER.info("New vacancy " + conteiner.getName() + " add to Database");
                 }
        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
    @Override
    public void close() throws Exception {

    }
}


