package ru.job4j.jdbc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Класс по обработке элементов xml.
 * @author Hincu Andrei (andreih1981@gmail.com)on 07.12.2017.
 * @version $Id$.
 * @since 0.1.
 */
public class Handler extends DefaultHandler {
    /**
     * Cчетчик элементов.
     */
    private long count;
    /**
     *Константа для парсинга.
     */
    private final static String ENTRY = "entry";
    public Handler() {
        super();
    }

    @Override
    public void startDocument() throws SAXException {
        System.out.println("Start parsing.");
    }

    @Override
    public void endDocument() throws SAXException {
        System.out.println("End parsing.");
    }

    /**
     * При нахождении элемента получаем требуемую информацию.
     * @param uri url.
     * @param localName name.
     * @param qName name.
     * @param attributes attribute.
     * @throws SAXException error.
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (ENTRY.equals(qName)) {
            this.count += Integer.parseInt(attributes.getValue("field"));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

    }

    public long getCount() {
        return count;
    }
}
