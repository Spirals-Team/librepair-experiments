package ru.aserdyuchenko.testTask;

import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class OrderBookTest {
    OrderBook book = new OrderBook();

    @Test
    public void whenAddNewOrder() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        book.saveToXML("book-1", new Order("BUY", 99.9, 49, 3));
    }

    @Test
    public void whenPrintOrderBook() throws ParserConfigurationException, SAXException, IOException {
        book.print();
    }

    @Test
    public void whenDeleteOrder() throws ParserConfigurationException, SAXException, IOException {
        book.delete();
    }
}