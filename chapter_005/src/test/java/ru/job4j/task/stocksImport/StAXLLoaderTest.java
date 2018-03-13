package ru.job4j.task.stocksImport;

import org.junit.Ignore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StAXLLoaderTest {

    @Ignore
    @org.junit.Test
    public void testStockLoad() throws Exception {

        StAXLLoader stAXLLoader = new StAXLLoader();
        boolean result = stAXLLoader.parse();
        boolean expected = true;

        assertThat(result, is(expected));


    }

}
