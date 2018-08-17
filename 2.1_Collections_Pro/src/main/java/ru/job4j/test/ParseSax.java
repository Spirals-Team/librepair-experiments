package ru.job4j.test;

import java.io.IOException;
import java.util.*;


import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


/**
 * Class ParseSax.
 */
public class ParseSax extends DefaultHandler {

    /**
     * @param orders map for all orders.
     */
    private Map<String, HashMap<Integer, Order>> orders = new HashMap<String, HashMap<Integer, Order>>();

    /**
     * @param ADD addorder
     */
    private static final String ADD = "AddOrder";

    /**
     * @param DELETE dleteorder
     */
    private static final String DELETE = "DeleteOrder";

    /**
     *@param BOOK book
     */
    private static final String BOOK = "book";

    /**
     *@param OPERATION operation
     */
    private static final String OPERATION = "operation";

    /**
     *@param PRICE price
     */
    private static final String PRICE = "price";

    /**
     *@param VOLUME volume
     */
    private static final String VOLUME = "volume";

    /**
     *@param ORDERID orderId
     */
    private static final String ORDERID = "orderId";

    /**
     * Method load.
     * @throws ParserConfigurationException exception
     * @throws SAXException exception
     * @throws IOException exception
     */
    public void load() throws ParserConfigurationException, SAXException, IOException {
        ParseSax handler = new ParseSax();
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(handler);
        parser.parse("orders.xml");
        this.orders = handler.getOrders();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        int lenght = attributes.getLength();
        HashMap<String, String> map = new HashMap<>();
        if (ADD.equals(qName)) {
            for (int i = 0; i < lenght; i++) {
                map.put(attributes.getQName(i), attributes.getValue(i));
            }
            Order order = new Order(map.get(BOOK),
                    "SELL".equals(map.get(OPERATION)) ? Order.Type.SELL : Order.Type.BUY,
                    Float.valueOf(map.get(PRICE)),
                    Integer.valueOf(map.get(VOLUME)),
                    Integer.valueOf(map.get(ORDERID))
            );
            this.add(order);
        } else if (DELETE.equals(qName)) {
            for (int i = 0; i < lenght; i++) {
                map.put(attributes.getQName(i), attributes.getValue(i));
            }
            orders.get(map.get(BOOK)).remove(Integer.parseInt(map.get(ORDERID)));
        }

    }

    /**
     * Method for printing all orders.
     */
    public void print() {
        for (HashMap<Integer, Order> map : orders.values()) {
            Book book = new Book(map.values());
            book.calculate();
        }
    }

    /**
     * Method for adding order.
     * @param order order for adding
     */
    public void add(Order order) {
        orders.putIfAbsent(order.book, new HashMap<Integer, Order>());
        orders.get(order.book).put(order.id, order);
    }

    /**
     * Method init.
     * @return work time
     * @throws IOException exception
     * @throws SAXException exception
     * @throws ParserConfigurationException exception
     */
    public long init() throws IOException, SAXException, ParserConfigurationException {
        long start = System.currentTimeMillis();
        this.load();
        this.print();
        return System.currentTimeMillis() - start;
    }

    /**
     * Getter.
     * @return map
     */
    public Map<String, HashMap<Integer, Order>> getOrders() {
        return orders;
    }

    /**
     * method main.
     * @param args args
     * @throws ParserConfigurationException exception
     * @throws SAXException exception
     * @throws IOException exception
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        ParseSax ps = new ParseSax();
        ps.init();
    }
}
