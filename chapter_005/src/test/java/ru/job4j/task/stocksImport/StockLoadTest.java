package ru.job4j.task.stocksImport;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class StockLoadTest {

    @Ignore
    @org.junit.Test
    public void testStockLoad() throws Exception {
        StockLoad stockLoad = new StockLoad();
        boolean result = stockLoad.parse();
        boolean expected = true;

        assertThat(result, is(expected));


    }

}
