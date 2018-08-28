package ru.job4j.jdbc;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author Vladimir Lembikov (cympak2009@mail.ru) on 12.03.2018.
 * @version 1.0.
 * @since 0.1.
 */
public class SAXHandler extends DefaultHandler {
    /**
     * Арифметическая сумма.
     */
    private long amount;
    /**
     * Константа.
     */
    private final static String ENTRY = "entry";

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (ENTRY.equals(qName)) {
            this.amount = this.amount + Integer.parseInt(attributes.getValue("field"));
        }
    }

    /**
     * Геттер для получения суммы элементов.
     *
     * @return вернем сумму.
     */
    public long getAmount() {
        return amount;
    }
}
