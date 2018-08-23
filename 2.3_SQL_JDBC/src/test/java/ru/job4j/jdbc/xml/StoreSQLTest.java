package ru.job4j.jdbc.xml;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class StoreSQLTest
 */
public class StoreSQLTest {
    /**
     * @param LOGGER logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Program.class);
    /**
     * @param properties properties
     */
    private final Properties properties = new Properties();

    /**
     * Method initProperties.
     */
    private void initProperties() {
        File file = new File(this.getClass().getClassLoader().getResource("xml.properties").getFile());
        try (FileInputStream fis = new FileInputStream(file)) {
            properties.load(fis);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Test №1.
     */
    @Test
    public void ifAdd10ThenCountOfValuesInDb10() {
        this.initProperties();
        try (StoreSQL db = new StoreSQL(properties)) {
            db.generate(10);
            List<Field> list = db.getAll();
            int result = 10;
            int expect = list.size();
            assertThat(result, is(expect));
        }
    }

    /**
     * Test N2.
     */
    @Test
    public void ifAdd10000ThenCountOfValuesInDb10000() {
        this.initProperties();
        try (StoreSQL db = new StoreSQL(properties)) {
            db.generate(10000);
            List<Field> list = db.getAll();
            int result = 10000;
            int expect = list.size();
            assertThat(result, is(expect));
        }
    }

    /**
     * Test N3.
     */
    @Test
    public void ifAddSomeValuesAndAfterDeleteThenCountOfValuesInDbIs0() {
        this.initProperties();
        try (StoreSQL db = new StoreSQL(properties)) {
            db.generate(10000);
            db.delete();
            List<Field> list = db.getAll();
            int result = 0;
            int expect = list.size();
            assertThat(result, is(expect));
        }
    }
}