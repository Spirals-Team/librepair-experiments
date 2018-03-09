package ru.aserdyuchenko.JDBC;

import org.junit.Test;
import java.sql.SQLException;
import java.util.Arrays;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class JdbcStorageTest {
    private JdbcStorage storage = new JdbcStorage();

//    @Test
//    public void whenAddNumberInTableAndPrintTable() throws SQLException {
//        storage.add(3);
//        assertThat(storage.get(), is(Arrays.asList(1, 2, 3)));
//    }
}
