package ru.job4j.jdbc;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class SQLStorageTest {

    @Test
    public void createAndPopulateDB() {
        SQLStorage storage = new SQLStorage();
        storage.setUrl("jdbc:sqlite::memory");
        storage.setNumber(1000000);
        storage.initDB();

        XMLDataPersistor persistor = new XMLDataPersistor();
        persistor.setStyleFilename("./ext_src/transform.xsl");
        persistor.createXml(storage.getDataSet(), "./ext_src/1.xml");
        persistor.transformXml("./ext_src/1.xml", "./ext_src/2.xml");

        EntryCalculator calc = new EntryCalculator();
        long sum = calc.aggregateSum(persistor.parseEntries("./ext_src/2.xml"));
        assertThat(sum, is(500000500000L));
    }
}