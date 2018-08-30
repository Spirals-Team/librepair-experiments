package ru.job4j.interview.orderbook;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.TreeMap;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static ru.job4j.interview.orderbook.Action.ADD;
import static ru.job4j.interview.orderbook.Action.DELETE;

/**
 * Parser.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class OrderParser {

    private XMLStreamReader parser;

    /**
     * Init parser.
     *
     * @param in input stream
     */
    public void init(final InputStream in) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        parser = factory.createXMLStreamReader(in);
    }

    /**
     * Parse xml.
     *
     * @param orderBooks store for parsed objects
     * @throws XMLStreamException
     */
    public void parse(TreeMap<String, OrderBook> orderBooks) throws XMLStreamException {
        while (parser.hasNext()) {
            if (isChildNode(parser, parser.next())) {
                process(orderBooks);
            }
        }
    }

    private void process(TreeMap<String, OrderBook> orderBooks) {
        String bookName = parser.getAttributeValue(null, "book");
        int id = parseInt(parser.getAttributeValue(null, "orderId"));
        String actionName = parser.getLocalName();
        Action action = Action.actionFromName(actionName);
        switch (action) {
            case ADD:
                addOrderBookIfAbsent(orderBooks, bookName);
                orderBooks.get(bookName).add(createOrder(parser, id));
                break;
            case DELETE:
                if (orderBooks.get(bookName) != null) {
                    orderBooks.get(bookName).delete(id);
                }
                if (orderBooks.get(bookName).getBids().isEmpty()
                    && orderBooks.get(bookName).getAsks().isEmpty()) {
                    orderBooks.remove(bookName);
                }
                break;
            default:
                throw new IllegalArgumentException(format(
                    "Node with action: {%s}, not found", actionName));
        }
    }

    private void addOrderBookIfAbsent(TreeMap<String, OrderBook> orderBooks, final String bookName) {
        orderBooks.putIfAbsent(bookName, new OrderBook(bookName));
    }

    private boolean isChildNode(final XMLStreamReader parser, final int event) {
        return event == XMLStreamConstants.START_ELEMENT
            && checkAction(parser.getLocalName());
    }

    private boolean checkAction(String localName) {
        return localName.equalsIgnoreCase(ADD.getType())
            || localName.equalsIgnoreCase(DELETE.getType());
    }

    private Order createOrder(final XMLStreamReader parser, final int id) {
        Operation operation = Operation.operationFromTitle(
            parser.getAttributeValue(null, "operation"));
        int volume = parseInt(
            parser.getAttributeValue(null, "volume"));
        double price = Double.parseDouble(
            parser.getAttributeValue(null, "price"));
        return new Order(id, operation, volume, price);
    }
}
