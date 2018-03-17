package ru.job4j.collections.orderbook;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 23.10.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Handler extends DefaultHandler {
    /**
     * Ордер.
     */
    private Order order;
    /**
     * Ссылка на хранилище.
     */
    private OrderBook orderBook;
    /**
     * Константа для добавления ордера.
     */
    private static final String ADD_ORDER = "AddOrder";
    /**
     * Константа для удаления ордера.
     */
    private static final String DELETE_ORDER = "DeleteOrder";
    /**
     * Конструктор.
     * @param orderBook хранилище.
     */
    public Handler(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    /**
     * Метод выводит сообщение о начале старта парсинга файла.
     * @throws SAXException ех.
     */
    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parsing.");
    }

    /**
     * Метод выводит сообщение о окончании парсинга файла.
     * @throws SAXException ех.
     */
    @Override
    public void endDocument() throws SAXException {
        System.out.println("End parsing.");
    }

    /**
     * Парсинг элемента с извлечением соответствующих атрибутов.
     * @param uri урл.
     * @param localName имя.
     * @param qName имя элемента.
     * @param att атрибут.
     * @throws SAXException ех.
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes att) throws SAXException {
         order = new Order();
        if (ADD_ORDER.equals(qName)) {
            order.setBook(att.getValue("book"));
            order.setOperation(att.getValue("operation"));
            order.setPrice(Float.valueOf(att.getValue("price")));
            order.setVolume(Integer.parseInt(att.getValue("volume")));
            order.setId(Integer.valueOf(att.getValue("orderId")));
            orderBook.addOrRemove(order, true);
        } else if (DELETE_ORDER.equals(qName)) {
            String book = att.getValue("book");
            int id = Integer.parseInt(att.getValue("orderId"));
            order.setBook(book);
            order.setId(id);
            orderBook.addOrRemove(order, false);
        }
    }

    /**
     * Обнуляем ордер после очередного элемента.
     * @param uri урл.
     * @param localName наме.
     * @param qName имя элемента.
     * @throws SAXException ех.
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        order = null;
    }
}
