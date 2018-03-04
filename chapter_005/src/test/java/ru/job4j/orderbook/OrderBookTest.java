package ru.job4j.orderbook;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class OrderBookTest {

    @Test
    public void whenParsedThenResultMapHasBuyAndSellOrders() {
        OrderBook ob = new OrderBook();
        ob.execute();

        assertThat(ob.getResult().get("book-1").get("BUY").get(0).getVolume(), is(1038053));
        assertThat(ob.getResult().get("book-1").get("SELL").get(0).getVolume(), is(14516));
    }

}