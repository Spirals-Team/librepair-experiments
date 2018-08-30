package ru.job4j.interview.orderbook;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class PrinterTest {

    private final Printer printer = new Printer();

    @Test
    public void printBook() {
        OrderBook book = new OrderBook("book-1");
        Order order2 = new Order(2, Operation.SELL, 46, 100.50d);
        Order order1 = new Order(1, Operation.SELL, 40, 100.50d);
        Order order3 = new Order(3, Operation.BUY, 80, 100.50d);

        book.add(order1);
        book.add(order2);
        book.add(order3);

        assertThat(printer.printBook(book),
            is("book-1\n\tBID \t\t\t ASK\n"
                + "Volume@Price  –  Volume@Price\n"
                + "-------- \t  –  \t 6@100.5\n"));
    }
}