package ru.aserdyuchenko.servlet;

import org.junit.Test;
import ru.aserdyuchenko.storage.DataSource;

import java.sql.SQLException;
// TODO: Добавить тесты для проверки работы БД.
public class DataSourceTest {
    DataSource dataSource;

    @Test
    public void whenGetMapFromDataSource() {
        try {
            dataSource = new DataSource();
            // TODO: Добавить ожидаемый результат.
            System.out.println(dataSource.getMap().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}