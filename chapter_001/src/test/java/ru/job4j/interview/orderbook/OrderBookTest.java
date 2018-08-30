package ru.job4j.interview.orderbook;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class OrderBookTest {

    private OrderBook book;
    private Order order1;
    private Order order2;
    private Order order3;

    @Test
    public void addOrdersToBidsCollection() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.BUY, 81, 100.50d);
        order2 = new Order(2, Operation.BUY, 86, 99.50d);

        book.add(order1);
        assertThat(book.getBids().size(), is(1));
        assertThat(book.getAsks().size(), is(0));
        book.add(order2);
        assertThat(book.getBids().size(), is(2));
        assertThat(book.getAsks().size(), is(0));
        assertThat(book.getBids().firstEntry()
            .getValue().get(order1.getOrderId()), is(order1));
        assertThat(book.getBids().lastEntry()
            .getValue().get(order2.getOrderId()), is(order2));
    }

    @Test
    public void addOrdersToBidsCollectionWithTheSamePrice() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.BUY, 81, 100.50d);
        order2 = new Order(2, Operation.BUY, 86, 100.50d);

        book.add(order1);
        assertThat(book.getBids().size(), is(1));
        assertThat(book.getBids().firstEntry().getValue().size(), is(1));
        assertThat(book.getAsks().size(), is(0));
        book.add(order2);
        assertThat(book.getBids().size(), is(1));
        assertThat(book.getBids().firstEntry().getValue().size(), is(2));
        assertThat(book.getAsks().size(), is(0));
        assertThat(book.getBids().firstEntry()
            .getValue().get(order1.getOrderId()), is(order1));
        assertThat(book.getBids().firstEntry()
            .getValue().get(order2.getOrderId()), is(order2));
    }

    @Test
    public void deleteOrderFromAsksCollection() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.SELL, 81, 100.50d);
        order2 = new Order(2, Operation.SELL, 86, 99.50d);

        book.add(order1);
        book.add(order2);
        book.delete(order1.getOrderId());
        assertThat(book.getAsks().size(), is(1));
        assertThat(book.getAsks().firstEntry()
            .getValue().get(order2.getOrderId()), is(order2));
        book.delete(order2.getOrderId());
        assertThat(book.getAsks().size(), is(0));
        assertFalse(book.delete(3));
    }

    @Test
    public void deleteOrderFromBidsCollection() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.BUY, 81, 100.50d);
        order2 = new Order(2, Operation.BUY, 86, 99.50d);

        book.add(order1);
        book.add(order2);
        book.delete(order1.getOrderId());
        assertThat(book.getBids().size(), is(1));
        assertThat(book.getBids().firstEntry()
            .getValue().get(order2.getOrderId()), is(order2));
        book.delete(order2.getOrderId());
        assertThat(book.getBids().size(), is(0));
        assertFalse(book.delete(3));
    }

    @Test
    public void addOrdersToAsksCollection() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.SELL, 81, 100.50d);
        order2 = new Order(2, Operation.SELL, 86, 99.50d);

        book.add(order1);
        assertThat(book.getAsks().size(), is(1));
        assertThat(book.getBids().size(), is(0));
        book.add(order2);
        assertThat(book.getAsks().size(), is(2));
        assertThat(book.getBids().size(), is(0));
        assertThat(book.getAsks().firstEntry()
            .getValue().get(order2.getOrderId()), is(order2));
        assertThat(book.getAsks().lastEntry()
            .getValue().get(order1.getOrderId()), is(order1));
    }

    @Test
    public void bidAskOrdersWithoutMatches() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.SELL, 81, 100.50d);
        order2 = new Order(2, Operation.BUY, 86, 99.50d);

        book.add(order1);
        book.add(order2);
        assertThat(book.getAsks().size(), is(1));
        assertThat(book.getBids().size(), is(1));
        assertThat(book.getAsks().firstEntry()
            .getValue().get(order1.getOrderId()), is(order1));
        assertThat(book.getBids().firstEntry()
            .getValue().get(order2.getOrderId()), is(order2));
    }

    @Test
    public void bidAskOrdersMatchesAskVolumeLessBidVolumeWithTheSamePrice() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.SELL, 81, 100.50d);
        order2 = new Order(2, Operation.BUY, 86, 100.50d);

        book.add(order1);
        book.add(order2);
        assertThat(book.getAsks().size(), is(0));
        assertThat(book.getBids().size(), is(1));
        assertThat(book.getBids().firstEntry()
            .getValue().get(order2.getOrderId()).getVolume(), is(5));
    }

    @Test
    public void bidAskOrdersMatchesSeveralAskVolumesEqualBidVolumesWithDifferentPrice() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.SELL, 40, 100.50d);
        order2 = new Order(2, Operation.SELL, 46, 100.00d);
        order3 = new Order(3, Operation.BUY, 86, 100.50d);

        book.add(order1);
        book.add(order2);
        book.add(order3);
        assertThat(book.getAsks().size(), is(0));
        assertThat(book.getBids().size(), is(0));
    }

    @Test
    public void bidAskOrdersMatchesSeveralAskVolumesLessBidVolumeWithDifferentPrice() {
        book = new OrderBook("book-1");
        order1 = new Order(1, Operation.SELL, 40, 100.50d);
        order2 = new Order(2, Operation.SELL, 46, 100.00d);
        order3 = new Order(3, Operation.BUY, 89, 100.50d);

        book.add(order1);
        book.add(order2);
        book.add(order3);
        assertThat(book.getAsks().size(), is(0));
        assertThat(book.getBids().firstEntry().getValue().size(), is(1));
        assertThat(book.getBids().firstEntry()
            .getValue().get(order3.getOrderId()).getVolume(), is(3));
    }

    @Test
    public void testSequenceOfProcessingOrders() {
        book = new OrderBook("book-1");
        order2 = new Order(2, Operation.SELL, 46, 100.50d);
        order1 = new Order(1, Operation.SELL, 40, 100.50d);
        order3 = new Order(3, Operation.BUY, 80, 100.50d);

        book.add(order1);
        book.add(order2);
        book.add(order3);
        assertThat(book.getBids().size(), is(0));
        assertThat(book.getAsks().firstEntry().getValue().size(), is(1));
        assertThat(book.getAsks().firstEntry()
            .getValue().get(order2.getOrderId()).getVolume(), is(6));
    }

//    @Test
//    public void aggregateAndPrintBook() {
//    }
}