package ru.job4j.jdbc;

import java.sql.SQLException;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SQLStorageTest {
    /**
     *  URL to the database test.db
     */
    private String url = "jdbc:sqlite:";
    private String DBName = "test.db";
    private int N = 5;

    @Test
    public void firstTest() throws SQLException {
        SQLStorage storage = new SQLStorage();
        storage.setURL(url + DBName);
        storage.setN(N);


        storage.setConnection();
        storage.parse();
        storage.xsl("1.xml", "2.xml", "template.xsl");


        int average = storage.parseAndFindAverage("2.xml");
        int expected = 0;
        for (int i = 1; i <= this.N; i++) {
            expected += i;
        }
        expected /= this.N;
        assertThat(average, is(expected));
    }
}