package ru.job4j.jdbc.tracker;

import org.junit.Test;

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class ConnectorTest {

    @Test
    public void connectToDB() throws SQLException {
        Connector connector = new Connector();
        String url = connector.connect().getMetaData().getURL();
        assertThat(url, is("jdbc:sqlite::memory"));
    }
}