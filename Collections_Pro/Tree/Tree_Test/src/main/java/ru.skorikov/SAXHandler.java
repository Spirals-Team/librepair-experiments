package ru.skorikov;

import org.xml.sax.helpers.DefaultHandler;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 10.12.17
 * @ version: java_kurs_standart
 */
public class SAXHandler extends DefaultHandler {
    /**
     * Книга ордеров.
     */
    private String book;
    /**
     * Номер ордера.
     */
    private Integer orderId;
    /**
     * Операция.
     */
    private String operation;
    /**
     * Цена.
     */
    private Double price;
    /**
     * Объем.
     */
    private Integer volume;

    /**
     * Хранилище карты книг из книг.
     */
    private Map<String, Map<Integer, Order>> books;

    /**
     * Получить книгу.
     *
     * @return книга.
     */
    public Map<String, Map<Integer, Order>> getBooks() {
        return books;
    }

    @Override
    public void startDocument() throws org.xml.sax.SAXException {
        super.startDocument();
        books = new TreeMap<>();
    }

    @Override
    public void endDocument() throws org.xml.sax.SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes attributes)
            throws org.xml.sax.SAXException {
        if (qName.equals("AddOrder")) {
            book = attributes.getValue("book");
            operation = attributes.getValue("operation");
            price = Double.parseDouble(attributes.getValue("price"));
            volume = Integer.parseInt(attributes.getValue("volume"));
            orderId = Integer.parseInt(attributes.getValue("orderId"));

            if (books.containsKey(book)) {
                books.get(book).put(orderId, new Order(operation, price, volume));
            } else {
                Map<Integer, Order> map = new TreeMap<>();
                map.put(orderId, new Order(operation, price, volume));
                books.put(book, map);
            }
        }
        if (qName.equals("DeleteOrder")) {
            book = attributes.getValue("book");
            orderId = Integer.parseInt(attributes.getValue("orderId"));

            books.get(book).remove(orderId);
        }
    }

    @Override
    public void endElement(String s, String s1, String s2) throws org.xml.sax.SAXException {
        super.endElement(s, s1, s2);
    }
}
