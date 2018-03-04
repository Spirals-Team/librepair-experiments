package ru.job4j.orderbook;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class SAXPars extends DefaultHandler {
    private Map<String, Map<Integer, Order>> books;

    public SAXPars(Map<String, Map<Integer, Order>> books) {
        this.books = books;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("AddOrder")) {
            String book = attributes.getValue(0);
            String operation = (attributes.getValue(1));
            float price = Float.parseFloat(attributes.getValue(2));
            int volume = Integer.parseInt(attributes.getValue(3));
            int orderId = Integer.parseInt(attributes.getValue(4));

            Order order = new Order(book, operation, price, volume, orderId);
            Map<Integer, Order> orders = books.computeIfAbsent(order.getBook(), key -> new HashMap<>());
            orders.put(order.getId(), order);
        }
        if (qName.equals("DeleteOrder")) {
            String book = attributes.getValue(0);
            int orderId = Integer.parseInt(attributes.getValue(1));
            books.get(book).remove(orderId);
        }
    }

    public void parse() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            FileInputStream inputStream = new FileInputStream("orders.xml");
            parser.parse(inputStream, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
